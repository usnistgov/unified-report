package gov.nist.healthcare.unified.model;

import gov.nist.healthcare.unified.exceptions.ConversionException;
import gov.nist.healthcare.unified.exceptions.NotFoundException;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
//			StringRef tmp = new StringRef();
//			tmp.value = "b";
//			if(TST(tmp))
//				
//			String str = new Scanner(new File("test.xml")).useDelimiter("\\Z").next();
//			EnhancedReport er = EnhancedReport.from("xml", str);
//			
			
			 String str = new Scanner(new File("test.json")).useDelimiter("\\Z").next();
			 EnhancedReport err = EnhancedReport.from("json", str);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		EnhancedReport er = new EnhancedReport();
//		Detections dts = new Detections();
//		Section md = new Section("metaData");
//		
//		md.put("type","automated");
//		md.put("profile.name","prname");
//		md.put("profile.version","v2");
//		md.put("message.content","msg");
//		
//		
//		Section dt1 = new Section("dt1");
//		dt1.put("classification", "a");
//		dt1.put("category", "ca");
//		Section dt2 = new Section("dt2");
//		dt2.put("classification", "a");
//		dt2.put("category", "caa");
//		Section dt3 = new Section("dt3");
//		dt3.put("classification", "b");
//		dt3.put("category", "cb");
//		Section dt4 = new Section("dt4");
//		dt4.put("classification", "b");
//		dt4.put("category", "cbb");
//		
//		ArrayList<Section> dtsl = new ArrayList<Section>();
//		dtsl.add(dt1);
//		dtsl.add(dt2);
//		dtsl.add(dt3);
//		dtsl.add(dt4);
//		
//		
//		
//		try {
//			dts.put(dtsl);
//			
//			er.setDetections(dts);
//			er.setMetadata(md);
//			
//			
//		} catch (NotFoundException | ConversionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	public static boolean TST(StringRef b){
		b.value = "a";
		return true;
	}
}
