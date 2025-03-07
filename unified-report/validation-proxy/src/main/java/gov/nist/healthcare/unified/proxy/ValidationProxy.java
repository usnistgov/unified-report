package gov.nist.healthcare.unified.proxy;


import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.impl.client.CloseableHttpClient;

import com.google.gson.Gson;

import gov.nist.healthcare.unified.enums.Context;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.model.Section;
import gov.nist.healthcare.unified.model.ValidationBody;
import gov.nist.validation.report.Report;
import hl7.v2.validation.ValidationContext;
import hl7.v2.validation.ValidationContextBuilder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import scala.collection.JavaConverters;
import validator.Validation;


public class ValidationProxy {

	private Section service;
	
	
	public ValidationProxy(String serName,String serProvider){
		service = new Section("service");
		service.put("name", serName);
		service.put("provider", serProvider);
		service.put("validationVersion", buildinfo.Info.version());
	}
	
	
	
	public EnhancedReport validateNew(String content, String profile, InputStream valueSetLibrary, List<InputStream> cStreams, InputStream vsBinding , InputStream coConstraintsContext, InputStream slicingContext, String id, Context context, Reader configuration, CloseableHttpClient httpClient) throws Exception{
	
		InputStream profileIS =IOUtils.toInputStream(profile, StandardCharsets.UTF_8);
		ValidationContextBuilder builder = new ValidationContextBuilder(profileIS);
				
		
		ValidationContext validationContext;
		if (valueSetLibrary != null) {
			builder.useDefaultValueSetFactory(
					valueSetLibrary,
					httpClient,
					true
			);
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
			 r = Validation.validateNew(validationContext,content,id);
		}else {
			r = Validation.validateNewWithConfig(validationContext,content,id,configuration);
		}
		
		
		String pr = profile;//new String(profile.readAllBytes());
		String ctx = "";
		if(context == Context.Free) ctx = "Context-Free"; else ctx = "Context-Based";
		ArrayList<Section> mds = new ArrayList<Section>();
		mds.add(service);
		return EnhancedReport.fromValidation(r, content, profile, id, mds,ctx);
	}
	
	//WIP external call to validation to get report
	public EnhancedReport validateExternal(String content,String profile,  String valueSetLibrary, List<String> ccontexts,String vsBinding , String coConstraintsContext, String slicingContext,  String id,Context context,Reader configuration, CloseableHttpClient httpClient) throws Exception{		
		OkHttpClient client = new OkHttpClient();
 
		
		ValidationBody vb = new ValidationBody();
//       vb.setValidationContext(validationContext);
       vb.setProfile(profile);
       vb.setValueSetLibrary(valueSetLibrary);
       vb.setConstraints(ccontexts);
       vb.setVsBindings(vsBinding);
       vb.setCoConstraints(coConstraintsContext);
       vb.setSlicings(slicingContext);
       vb.setContent(content);
       vb.setId(id);
       vb.setConfig(null);
       
//       String json = mapper.writeValueAsString(vb);
       
       Gson gson = new Gson();
       String json = gson.toJson(vb);
       
       RequestBody body = RequestBody.create(MediaType.get("application/json"), json);
       Request request = new Request.Builder()
               .url("http://localhost:10168/validate")
               .post(body)
               .build();

       Response response = client.newCall(request).execute();
       System.out.println("Response Code: " + response.code());
       System.out.println(response.body());
       if(response.isSuccessful()) {
    	   EnhancedReport    r = EnhancedReport.from("json",response.body().string());
    	   return r;
       }
       
       return null;
		
	}
	
	// convert parameters
	public EnhancedReport validateNew(String content,String profile,  String valueSetLibrary, List<String> ccontexts,String vsBinding , String coConstraintsContext, String slicingContext,  String id,Context context,Reader configuration, CloseableHttpClient httpClient) throws Exception{		
		InputStream valueSetLibraryIS =IOUtils.toInputStream(valueSetLibrary, StandardCharsets.UTF_8);
		List<InputStream> cStreams = new ArrayList<InputStream>();	
		for(String c : ccontexts) {
			if (c != null) {
				cStreams.add(IOUtils.toInputStream(c, StandardCharsets.UTF_8));
			}
		}	
		InputStream vsBindingIS = null;
		if (vsBinding != null) {
			vsBindingIS =IOUtils.toInputStream(vsBinding, StandardCharsets.UTF_8);
		}
		InputStream coConstraintsContextIS = null;
		if (coConstraintsContext != null) {
			coConstraintsContextIS =IOUtils.toInputStream(coConstraintsContext, StandardCharsets.UTF_8);
		}
		InputStream slicingContextIS = null;
		if (slicingContext != null) {
			slicingContextIS =IOUtils.toInputStream(slicingContext, StandardCharsets.UTF_8);
		}
		
		return this.validateNew(content, profile, valueSetLibraryIS, cStreams, vsBindingIS, coConstraintsContextIS, slicingContextIS, id, context, configuration, httpClient);
		
	}



	

	



	
}
