package gov.nist.healthcare.unified.proxy;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import gov.nist.healthcare.unified.enums.Context;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.model.Section;
import gov.nist.validation.report.Report;
import hl7.v2.profile.Profile;
import hl7.v2.profile.XMLDeserializer;
import hl7.v2.validation.content.ConformanceContext;
import hl7.v2.validation.content.DefaultConformanceContext;
import hl7.v2.validation.vs.ValueSetLibrary;
import hl7.v2.validation.vs.ValueSetLibraryImpl;
import validator.Util;
import validator.Validation;

public class ValidationProxy {

	private Section service;
	
	
	public ValidationProxy(String serName,String serProvider){
		service = new Section("service");
		service.put("name", serName);
		service.put("provider", serProvider);
		service.put("validationVersion", buildinfo.Info.version());
	}
	
	
	public EnhancedReport validate(String msg,String profile,String constraints,String vs,String id,Context context) throws Exception{
	
			String content = Util.streamAsString(msg);
			String pr = Util.streamAsString(profile);
			Report r = Validation.validate(profile, constraints, vs, content,id);
			String ctx = "";
			ArrayList<Section> mds = new ArrayList<Section>();
			mds.add(service);
			if(context == Context.Free) ctx = "Context-Free"; else ctx = "Context-Based";
			return EnhancedReport.fromValidation(r, content, pr, id,mds,ctx);
	}
	
	public EnhancedReport validate(String msg,String profile,ArrayList<String> constraints,String vs,String id,Context context) throws Exception{

		// Extract Profile
		String pr = Util.streamAsString(profile);
		
		// Conf
		List<InputStream> l = new ArrayList<InputStream>();
		for(String s : constraints){
			InputStream contextXML = ValidationProxy.class.getResourceAsStream(s);
			l.add(contextXML);
		}
		ConformanceContext c = DefaultConformanceContext.apply(l).get();
		
		//VS
		InputStream vsXML = ValidationProxy.class.getResourceAsStream(vs);
		ValueSetLibrary valueSetLibrary = ValueSetLibraryImpl.apply(vsXML).get();
		
		return this.validate(msg,pr,c,valueSetLibrary,id,context);
	}
	
	public EnhancedReport validateContent(String content,String profile,String constraints,String vs,String id,Context context) throws Exception{

			Report r = Validation.validate(profile, constraints, vs, content,id);
			String pr = Util.streamAsString(profile);
			String ctx = "";
			if(context == Context.Free) ctx = "Context-Free"; else ctx = "Context-Based";
			ArrayList<Section> mds = new ArrayList<Section>();
			mds.add(service);
			return EnhancedReport.fromValidation(r, content, pr, id, mds,ctx);

	}
	/**
	 * @param content Message content
	 * @param profile Profile content
	 * @param constraints Constraints Object
	 * @param vs ValueSetsLibrary Object
	 * @param id Message id
	 * @param context Context Free or Context Based 
	 * @throws Exception 
	 */
	public EnhancedReport validate(String content,String profile,ConformanceContext constraints,ValueSetLibrary vs,String id,Context context) throws Exception{

			InputStream stream = new ByteArrayInputStream(profile.getBytes(StandardCharsets.UTF_8));
			Profile profileX = XMLDeserializer.deserialize(stream).get();

			Report r = Validation.validate(profileX, constraints, vs, content,id);
			String pr = profile;
			String ctx = "";
			if(context == Context.Free) ctx = "Context-Free"; else ctx = "Context-Based";
			ArrayList<Section> mds = new ArrayList<Section>();
			mds.add(service);
			return EnhancedReport.fromValidation(r, content, pr, id, mds,ctx);

	}
	
	public EnhancedReport validate(String content,String profile,ConformanceContext constraints,ValueSetLibrary vs,String id,Context context,Reader configuration) throws Exception{

		InputStream stream = new ByteArrayInputStream(profile.getBytes(StandardCharsets.UTF_8));
		Profile profileX = XMLDeserializer.deserialize(stream).get();

		Report r = Validation.validate(profileX, constraints, vs, content,id,configuration);
		String pr = profile;
		String ctx = "";
		if(context == Context.Free) ctx = "Context-Free"; else ctx = "Context-Based";
		ArrayList<Section> mds = new ArrayList<Section>();
		mds.add(service);
		return EnhancedReport.fromValidation(r, content, pr, id, mds,ctx);

}
	



	
}
