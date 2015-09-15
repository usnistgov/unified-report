package gov.nist.healthcare.unified.helper;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;

public class ConfigHandler {
	
		public static JSONArray getFailuresInterpretation(){
			
			JSONArray failInter = new JSONArray();
			ArrayList<String> detections = new ArrayList<String>();
			Hashtable<String,ArrayList<String>> ar = new Hashtable<String,ArrayList<String>>();
			Config conf = ConfigFactory.load();
			Iterator<java.util.Map.Entry<String,ConfigValue>> it = conf.entrySet().iterator();
			while(it.hasNext()){
				java.util.Map.Entry<String,ConfigValue> e = it.next();
				String k = e.getKey();
				String det = getDetection(k);
				if(det != null && det != "")
					detections.add(det);
			}
			
			for(String d : detections){
				String categ = conf.getString("report."+d+".category").toUpperCase().replace(" ", "_").replace("-", "_");
				String cl	 = conf.getString("report."+d+".classification").toUpperCase().replace(" ", "_").replace("-", "_");
				if(ar.containsKey(categ) && !ar.get(categ).contains(cl))
					ar.get(categ).add(cl);
				else if(!ar.containsKey(categ))
				{
					ArrayList<String> na = new ArrayList<String>();
					na.add(cl);
					ar.put(categ, na);
				}
			}
			
			for(String categ : ar.keySet()){
				for(String cl : ar.get(categ)){
					JSONObject mf = new JSONObject();
					mf.put(categ, cl);
					failInter.put(mf);
				}
			}
		    return failInter;
		
		}
		
		private static String getDetection(String k){
			if(!k.startsWith("report."))
				return null;
			else if(k.startsWith("report.classification"))
					return null;
			else if(k.startsWith("report.category"))
					return null;

			//
			String detection = k.substring(k.indexOf(".")+1);
			if(detection.contains("."))
				return detection.substring(0, detection.indexOf("."));
			return detection;
			
		}
}
