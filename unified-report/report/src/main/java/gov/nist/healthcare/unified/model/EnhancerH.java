package gov.nist.healthcare.unified.model;

import gov.nist.healthcare.unified.exceptions.ConversionException;
import gov.nist.healthcare.unified.exceptions.NotFoundException;
import gov.nist.healthcare.unified.helper.ConfigHandler;
import gov.nist.healthcare.unified.message.EncodingConstants;
import gov.nist.healthcare.unified.validation.message.StandardTypeType;
import gov.nist.healthcare.unified.validation.message.ValidationType;
import gov.nist.validation.report.Entry;
import gov.nist.validation.report.Report;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.json.JSONObject;
import org.w3c.dom.Document;

public class EnhancerH {

	public static void enhanceHeader(EnhancedReport er,Section service, String msg){
			er.getMetadata().put(service);
			EnhancerH.enhanceHeader(er,msg);
	}
	
	public static void enhanceHeader(EnhancedReport er, String msg){
		er.getMetadata().put("standardType", StandardTypeType.HL_7_V_2.value());
		er.getMetadata().put("validationType", ValidationType.AUTOMATED.value());
		
		er.getMetadata().put("message.content", msg);
		er.getMetadata().put("message.encoding", EncodingConstants.ER_7.value());
		Collection cls = new Collection("failuresInterpretation");
		cls.setRaw(ConfigHandler.getFailuresInterpretation());
		er.getMetadata().put(cls);
		
		GregorianCalendar date = new GregorianCalendar();

		try {
			XMLGregorianCalendar calendar = DatatypeFactory.newInstance()
			        .newXMLGregorianCalendar(
			            date);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			er.getMetadata().put("date", formatter.format(calendar.toGregorianCalendar().getTime()));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void enhanceWithReport(EnhancedReport er,Report r){
		int error = 0;
		int warning = 0;
		int alert = 0;
		int informational = 0;
		int affirmative = 0;
		ArrayList<Section> entries = new ArrayList<Section>();
		for(String k : r.getEntries().keySet()){
			for(Entry e : r.getEntries().get(k)){
				entries.add(format(e));
				if(e.getClassification().toUpperCase().equals("AFFIRMATIVE")){
					affirmative++;
				}
				else if(e.getClassification().toUpperCase().equals("ALERT")){
					alert++;
				}
				else if(e.getClassification().toUpperCase().equals("ERROR")){
					error++;
				}
				else if(e.getClassification().toUpperCase().equals("INFO") || e.getClassification().toUpperCase().equals("INFORMATIONAL")){
					informational++;
				}
				else if(e.getClassification().toUpperCase().equals("WARNING")){
					warning++;
				}
			}
		}
		
		er.getMetadata().put("counts.error", error);
		er.getMetadata().put("counts.warning", warning);
		er.getMetadata().put("counts.alert", alert);
		er.getMetadata().put("counts.informational", informational);
		er.getMetadata().put("counts.affirmative", affirmative);

		if(error == 0)
			er.getMetadata().put("validationStatus", "true");
		else
			er.getMetadata().put("validationStatus", "false");
		
		try {
			er.getDetections().put(entries);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConversionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void enhanceWithProfile(EnhancedReport er,String s,String id){
		ByteArrayInputStream profile = new ByteArrayInputStream(s.getBytes());
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        String root = "/ConformanceProfile/MetaData/";
        JSONObject o = new JSONObject();
        DocumentBuilder builder;
		try {
			
			builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(profile);
			XPath xPath =  XPathFactory.newInstance().newXPath();
			
			//--- OrgName
			String OrgName = xPath.compile(root+"@OrgName").evaluate(xmlDocument);
			if (OrgName != null && !OrgName.equals(""))  er.getMetadata().put("profile.orgName", OrgName);
			//--- Name
			String Name = xPath.compile(root+"@Name").evaluate(xmlDocument);
			if (Name != null && !Name.equals(""))  er.getMetadata().put("profile.name", Name);
			//--- Version
			String Version = xPath.compile(root+"@Version").evaluate(xmlDocument);
			if (Version != null && !Version.equals(""))  er.getMetadata().put("profile.version", Version);
			//--- Standard
			String Strd = xPath.compile("/ConformanceProfile/@HL7Version").evaluate(xmlDocument);
			if (Strd != null && !Strd.equals(""))  er.getMetadata().put("profile.standard", Strd);
			//--- Type
			String Type = xPath.compile("/ConformanceProfile/Messages/Message[@ID = '"+id+"']/@Type").evaluate(xmlDocument);
			if (Type != null && !Type.equals(""))  er.getMetadata().put("profile.type", Type);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Section format(Entry e){
		Section s = new Section("");
		
		try {
			s.setRaw(filterMD(new JSONObject(e.toJson())));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return s;
	}
	
	public static JSONObject filterMD(JSONObject entry){
		if(!entry.has("metaData"))
			return entry;
		else {
			if(entry.isNull("metaData") || entry.getJSONObject("metaData").keySet().size() == 0) {
				entry.remove("metaData");
				return entry;
			}
			
			JSONObject md = entry.getJSONObject("metaData");
			if(md.has("valueSet")){
				Set<Object> keys = new HashSet<Object>();
				keys.addAll(md.getJSONObject("valueSet").keySet());
				for(Object k : keys){
					if(md.getJSONObject("valueSet").isNull(k.toString()) || md.getJSONObject("valueSet").getString(k.toString()).equals("")){
						entry.getJSONObject("metaData").getJSONObject("valueSet").remove(k.toString());
					}
				}
				if(entry.getJSONObject("metaData").getJSONObject("valueSet").keySet().size() == 0)
					entry.remove("metaData");
				return entry;
			}
			else if(md.has("reference")){
				Set<Object> keys = new HashSet<Object>();
				keys.addAll(md.getJSONObject("reference").keySet());
				for(Object k : keys){
					if(md.getJSONObject("reference").isNull(k.toString()) || md.getJSONObject("reference").getString(k.toString()).equals("")){
						entry.getJSONObject("metaData").getJSONObject("reference").remove(k.toString());
					}
				}
				if(entry.getJSONObject("metaData").getJSONObject("reference").keySet().size() == 0)
					entry.remove("metaData");
				return entry;
			}
			
			entry.remove("metaData");
			return entry;
		}
	}
}
