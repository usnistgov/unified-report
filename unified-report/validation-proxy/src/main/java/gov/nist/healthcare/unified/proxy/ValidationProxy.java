package gov.nist.healthcare.unified.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import gov.nist.healthcare.unified.enums.Context;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.model.Section;
import gov.nist.healthcare.unified.model.ValidationBody;
import gov.nist.validation.report.Report;
import hl7.v2.validation.ValidationContext;
import hl7.v2.validation.ValidationContextBuilder;
import hl7.v2.validation.vs.external.client.ExternalValueSetClient;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import scala.collection.JavaConverters;
import validator.Validation;


@Component
public class ValidationProxy {

	private Section service;

	@Value("#{${validation.urls:{null}}}")
	private Map<String, String> urls;

	public ValidationProxy() {
	}

	public ValidationProxy(String serName, String serProvider) {
		service = new Section("service");
		service.put("name", serName);
		service.put("provider", serProvider);
		service.put("validationVersion", buildinfo.Info.version());
	}

	public void setInfo(String serName, String serProvider) {
		service = new Section("service");
		service.put("name", serName);
		service.put("provider", serProvider);
		service.put("validationVersion", buildinfo.Info.version());
	}

	public EnhancedReport validate(String content, String profile, String valueSetLibrary, List<String> constraintsList, String vsBinding,
			String coConstraintsContext, String slicingContext, String conformanceProfileId, Context context, String configuration,
			HashMap<String, String> apikeys, String externalValidationVersion) throws Exception {
		if (externalValidationVersion == null || externalValidationVersion.equalsIgnoreCase(buildinfo.Info.version()) || urls == null || urls.get(externalValidationVersion) == null) {
			return validateLocally(content, profile, valueSetLibrary, constraintsList, vsBinding, coConstraintsContext, slicingContext, conformanceProfileId,
					context, configuration, apikeys);
		} else {
			return validateExternally(content, profile, valueSetLibrary, constraintsList, vsBinding, coConstraintsContext, slicingContext, conformanceProfileId,
					context, configuration, apikeys, externalValidationVersion);
		}

	}

	

	// WIP external call to validation to get report
	public EnhancedReport validateExternally(String content, String profile, String valueSetLibrary, List<String> ccontexts, String vsBinding,
			String coConstraintsContext, String slicingContext, String id, Context context, String configuration, HashMap<String, String> apikeys,
			String externalValidationVersion) throws Exception {

		OkHttpClient client = new OkHttpClient();
		String contextString = "";
		if (context == Context.Free)
			contextString = "Context-Free";
		else
			contextString = "Context-Based";

		ValidationBody vb = new ValidationBody();
		vb.setProfile(profile);
		vb.setValueSetLibrary(valueSetLibrary);
		vb.setConstraints(ccontexts);
		vb.setVsBindings(vsBinding);
		vb.setCoConstraints(coConstraintsContext);
		vb.setSlicings(slicingContext);
		vb.setContent(content);
		vb.setId(id);
		vb.setConfig(configuration);
		vb.setServiceName(service.getString("name"));
		vb.setServiceProvider(service.getString("provider"));
		vb.setContext(contextString);
		vb.setApiKeys(apikeys);

		Gson gson = new Gson();
		String json = gson.toJson(vb);
		RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
		Request request = new Request.Builder().url(urls.get(externalValidationVersion)).post(body).build();

		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			EnhancedReport r = EnhancedReport.from("json", response.body().string());
			return r;
		}

		return null;

	}

	//validate locally for better performance with the latest validation engine.
	public EnhancedReport validateLocally(String content, String profile, String valueSetLibrary, List<String> ccontexts, String vsBinding,
			String coConstraintsContext, String slicingContext, String id, Context context, String configuration, HashMap<String, String> apikeys)
			throws Exception {
		Report r;
		// configure external value set validation/fetching
		SSLContextBuilder sslBuilder = new SSLContextBuilder();
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(2 * 1000).setConnectTimeout(2 * 1000).setSocketTimeout(2 * 1000)
				.build();
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslBuilder.build(), NoopHostnameVerifier.INSTANCE);

		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).disableCookieManagement()
				.setSSLSocketFactory(socketFactory).addInterceptorFirst(new HttpRequestInterceptor() {
					@Override
					public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
						context.getAttribute(ExternalValueSetClient.HTTP_CONTEXT_VS_BINDING_IDENTIFIER).toString();
						request.addHeader("X-API-KEY",
								apikeys.get(context.getAttribute(ExternalValueSetClient.HTTP_CONTEXT_VS_BINDING_IDENTIFIER).toString()));
					}
				}).build();

		InputStream profileIS = IOUtils.toInputStream(profile, StandardCharsets.UTF_8);
		ValidationContextBuilder builder = new ValidationContextBuilder(profileIS);

		InputStream valueSetLibraryIS = IOUtils.toInputStream(valueSetLibrary, StandardCharsets.UTF_8);
		List<InputStream> cStreams = new ArrayList<InputStream>();
		for (String c : ccontexts) {
			if (c != null) {
				cStreams.add(IOUtils.toInputStream(c, StandardCharsets.UTF_8));
			}
		}
		InputStream vsBindingIS = null;
		if (vsBinding != null) {
			vsBindingIS = IOUtils.toInputStream(vsBinding, StandardCharsets.UTF_8);
		}
		InputStream coConstraintsContextIS = null;
		if (coConstraintsContext != null) {
			coConstraintsContextIS = IOUtils.toInputStream(coConstraintsContext, StandardCharsets.UTF_8);
		}
		InputStream slicingContextIS = null;
		if (slicingContext != null) {
			slicingContextIS = IOUtils.toInputStream(slicingContext, StandardCharsets.UTF_8);
		}

		ValidationContext validationContext;
		if (valueSetLibrary != null) {
			builder.useDefaultValueSetFactory(valueSetLibraryIS, httpClient, true);
		}

		scala.collection.immutable.List<InputStream> conformanceContexts = JavaConverters.collectionAsScalaIterable(cStreams).toList();
		if (conformanceContexts != null) {
			builder.useConformanceContext(conformanceContexts);
		}
		if (vsBinding != null) {
			builder.useVsBindings(vsBindingIS);
		}
		if (coConstraintsContext != null) {
			builder.useCoConstraintsContext(coConstraintsContextIS);
		}
		if (slicingContext != null) {
			builder.useSlicingContext(slicingContextIS);
		}
		builder.setFFLegacy0396(true);
		validationContext = builder.getValidationContext();

		if (configuration == null) {
			r = Validation.validateHL7v2(validationContext, content, id);
		} else {
			r = Validation.validateHL7v2WithConfig(validationContext, content, id,new StringReader(configuration));
		}

		String contextString = "";
		if (context == Context.Free)
			contextString = "Context-Free";
		else
			contextString = "Context-Based";
		ArrayList<Section> mds = new ArrayList<Section>();
		mds.add(service);
		return EnhancedReport.fromValidation(r, content, profile, id, mds, contextString);

	}
	
	
	//not used
	public EnhancedReport validateLocalOld(String content, String profile, String valueSetLibrary, List<String> ccontexts, String vsBinding,
			String coConstraintsContext, String slicingContext, String id, Context context, Reader configuration, CloseableHttpClient httpClient)
			throws Exception {
		InputStream valueSetLibraryIS = IOUtils.toInputStream(valueSetLibrary, StandardCharsets.UTF_8);
		List<InputStream> cStreams = new ArrayList<InputStream>();
		for (String c : ccontexts) {
			if (c != null) {
				cStreams.add(IOUtils.toInputStream(c, StandardCharsets.UTF_8));
			}
		}
		InputStream vsBindingIS = null;
		if (vsBinding != null) {
			vsBindingIS = IOUtils.toInputStream(vsBinding, StandardCharsets.UTF_8);
		}
		InputStream coConstraintsContextIS = null;
		if (coConstraintsContext != null) {
			coConstraintsContextIS = IOUtils.toInputStream(coConstraintsContext, StandardCharsets.UTF_8);
		}
		InputStream slicingContextIS = null;
		if (slicingContext != null) {
			slicingContextIS = IOUtils.toInputStream(slicingContext, StandardCharsets.UTF_8);
		}

		return this.validateLocalOld(content, profile, valueSetLibraryIS, cStreams, vsBindingIS, coConstraintsContextIS, slicingContextIS, id, context,
				configuration, httpClient);
	}
	
	
//	not used
	public EnhancedReport validateLocalOld(String content, String profile, InputStream valueSetLibrary, List<InputStream> cStreams, InputStream vsBinding,
			InputStream coConstraintsContext, InputStream slicingContext, String id, Context context, Reader configuration, CloseableHttpClient httpClient)
			throws Exception {

		InputStream profileIS = IOUtils.toInputStream(profile, StandardCharsets.UTF_8);
		ValidationContextBuilder builder = new ValidationContextBuilder(profileIS);

		ValidationContext validationContext;
		if (valueSetLibrary != null) {
			builder.useDefaultValueSetFactory(valueSetLibrary, httpClient, true);
		}

		scala.collection.immutable.List<InputStream> conformanceContexts = JavaConverters.collectionAsScalaIterable(cStreams).toList();
		if (conformanceContexts != null) {
			builder.useConformanceContext(conformanceContexts);
		}
		if (vsBinding != null) {
			builder.useVsBindings(vsBinding);
		}
		if (coConstraintsContext != null) {
			builder.useCoConstraintsContext(coConstraintsContext);
		}
		if (slicingContext != null) {
			builder.useSlicingContext(slicingContext);
		}
		builder.setFFLegacy0396(true);
		validationContext = builder.getValidationContext();

		Report r;
		if (configuration == null) {
			r = Validation.validateHL7v2(validationContext, content, id);
		} else {
			r = Validation.validateHL7v2WithConfig(validationContext, content, id, configuration);
		}

		String ctx = "";
		if (context == Context.Free)
			ctx = "Context-Free";
		else
			ctx = "Context-Based";
		ArrayList<Section> mds = new ArrayList<Section>();
		mds.add(service);
		return EnhancedReport.fromValidation(r, content, profile, id, mds, ctx);
	}

}
