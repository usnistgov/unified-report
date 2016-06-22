package gov.nist.healthcare.unified.converters;

import org.json.JSONObject;

import gov.nist.healthcare.unified.interfaces.Converter;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.model.Section;

public class JSONConverter implements Converter {

	@Override
	public String convert(EnhancedReport s) {
		Section sect = new Section("report");
		sect.put(s.getMetadata());
		sect.put(s.getDetectionSection());
		return sect.raw().toString();
	}

	@Override
	public EnhancedReport unserialize(String s) {
		JSONObject j = new JSONObject(s);
		EnhancedReport er = new EnhancedReport();
		if(j.has("metaData")){
			Section sect = new Section("metaData");
			sect.setRaw(j.getJSONObject("metaData"));
			
			
			JSONObject md = j.getJSONObject("metaData");
			if(md.has("message")){
				JSONObject msg = md.getJSONObject("message");
				if(msg.has("content")){
					er.setOriginalMsg(msg.getString("content"));
					msg.put("content", EnhancedReport.messageF(msg.getString("content"), false));
				} else {
					er.setOriginalMsg("");
				}
			}		
			er.setMetadata(sect);	
		}
		
		if(j.has("detections")){
			Section sect = new Section("detections");
			sect.setRaw(j.getJSONObject("detections"));
			er.setDetectionSection(sect);
		}
		return er;
	}

	@Override
	public String getFormat() {
		// TODO Auto-generated method stub
		return "json";
	}

	
}
