package gov.nist.healthcare.unified.proxy;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import validator.Util;
import validator.Validation;
import gov.nist.healthcare.unified.enums.Context;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.model.Section;
import gov.nist.validation.report.Report;
import hl7.v2.profile.Profile;
import hl7.v2.profile.XMLDeserializer;
import hl7.v2.validation.content.ConformanceContext;
import hl7.v2.validation.vs.ValueSetLibrary;

public class ValidationProxy {

	private Section service;
	public ValidationProxy(String serName,String serProvider,String serVersion){
		service = new Section("service");
		service.put("name", serName);
		service.put("provider", serProvider);
		service.put("version", serVersion);
	}
	
	public EnhancedReport validate(String msg,String profile,String constraints,String vs,String id,Context context){
		try {
		
			String content = Util.streamAsString(msg);
			String pr = Util.streamAsString(profile);
			Report r = Validation.validate(profile, constraints, vs, content,id);
			String ctx = "";
			if(context == Context.Free) ctx = "Context-Free"; else ctx = "Context-Based";
			return EnhancedReport.fromValidation(r, content, pr, id, service,ctx);
		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public EnhancedReport validateContent(String content,String profile,String constraints,String vs,String id,Context context){
		try {
		
			Report r = Validation.validate(profile, constraints, vs, content,id);
			String pr = Util.streamAsString(profile);
			String ctx = "";
			if(context == Context.Free) ctx = "Context-Free"; else ctx = "Context-Based";
			return EnhancedReport.fromValidation(r, content, pr, id, service,ctx);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * @param content Message content
	 * @param profile Profile content
	 * @param constraints Constraints Object
	 * @param vs ValueSetsLibrary Object
	 * @param id Message id
	 * @param context Context Free or Context Based 
	 */
	public EnhancedReport validate(String content,String profile,ConformanceContext constraints,ValueSetLibrary vs,String id,Context context){
		try {
		
			InputStream stream = new ByteArrayInputStream(profile.getBytes(StandardCharsets.UTF_8));
			Profile profileX = XMLDeserializer.deserialize(stream).get();

			Report r = Validation.validate(profileX, constraints, vs, content,id);
			String pr = profile;
			String ctx = "";
			if(context == Context.Free) ctx = "Context-Free"; else ctx = "Context-Based";
			return EnhancedReport.fromValidation(r, content, pr, id, service,ctx);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
