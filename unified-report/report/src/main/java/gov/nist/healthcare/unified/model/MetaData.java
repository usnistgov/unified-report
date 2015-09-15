package gov.nist.healthcare.unified.model;

import org.json.JSONObject;

public class MetaData {
	private JSONObject metadata = new JSONObject();
	
	public JSONObject raw(){
		return metadata;
	}
	
	public Section getSection(String name){
		if(metadata.has(name)){
			Section s = new Section(name);
			s.setRaw(metadata.getJSONObject(name));
			return s;
		}
		return null;
	}
	
	public Collection getCollection(String name){
		if(metadata.has(name)){
			Collection s = new Collection(name);
			s.setRaw(metadata.getJSONArray(name));
			return s;
		}
		return null;
	}
	
	public void put(String k,Collection v){
		if(!v.isEmpty())
			metadata.put(k, v.raw());
	}
	
	public void put(String k,Section v){
		if(!v.isEmpty())
			metadata.put(k, v.raw());
	}
	
}
