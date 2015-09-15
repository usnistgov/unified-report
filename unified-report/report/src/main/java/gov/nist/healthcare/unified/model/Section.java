package gov.nist.healthcare.unified.model;

import gov.nist.healthcare.unified.exceptions.ConversionException;
import gov.nist.healthcare.unified.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Section implements AccessibleObject {
	public String name;
	public JSONObject o;

	public Section(String n) {
		this.name = n;
		o = new JSONObject();
	}

	public String getName() {
		return name;
	}

	public JSONObject raw() {
		return o;
	}

	public void setRaw(JSONObject r) {
		o = r;
	}

	public String getString(String k) throws NotFoundException, ConversionException {
		try {
			if(k.contains(".")){
				String parent = k.substring(0, k.lastIndexOf("."));
				String child  = k.substring(k.lastIndexOf(".")+1);
				Section a = (Section) this.getObject(parent);
				if(a.raw().has(child))
					return a.raw().getString(child);
				else
					throw new NotFoundException();
			}
			else {
				
				if(o.has(k))
					return o.getString(k);
				else
					throw new NotFoundException();
			}
		}
		catch(JSONException e){
			throw new ConversionException();
		}
	}

	public Integer getInt(String k) throws NotFoundException, ConversionException {
		try {
			if(k.contains(".")){
				String parent = k.substring(0, k.lastIndexOf("."));
				String child  = k.substring(k.lastIndexOf(".")+1);
				Section a = (Section) this.getObject(parent);
				if(a.raw().has(child))
					return a.raw().getInt(child);
				else
					throw new NotFoundException();
			}
			else {
				
				if(o.has(k))
					return o.getInt(k);
				else
					throw new NotFoundException();
			}
		}
		catch(JSONException e){
			throw new ConversionException();
		}
		
	}

	public boolean isEmpty() {
		return o.keySet().size() == 0;
	}

	private String[] steps(String k) {
		String[] stps = k.split("\\.");
		return stps;
	}

	@Override
	public Section getObject(String key) throws NotFoundException,
			ConversionException {
		JSONObject tmp = o;
		String last = "";
		for (String k : steps(key)) {
			if (tmp.has(k)) {
				try {
					tmp = tmp.getJSONObject(k);
					last = k;
				} catch (JSONException e) {
					throw new ConversionException();
				}
			} else {
				//
				throw new NotFoundException();
			}
		}
		Section s = new Section(last);
		s.setRaw(tmp);
		return s;
	}

	@Override
	public Collection getArray(String key) throws NotFoundException,
			ConversionException {
		JSONObject tmp = o;
		JSONArray res;
		String[] keys = steps(key);
		int i = 0;
		for (String k : keys) {
			if (tmp.has(k)) {
				try {
					if (i < keys.length - 1) {
						tmp = tmp.getJSONObject(k);
					} else {
						res = tmp.getJSONArray(k);
						Collection s = new Collection(k);
						s.setRaw(res);
						return s;
					}
					i++;
				} catch (JSONException e) {
					throw new ConversionException();
				}
			} else
				throw new NotFoundException();
		}
		return null;
	}

	@Override
	public List<String> keys() {
		ArrayList<String> ks = new ArrayList<String>();
		ks.addAll(o.keySet());
		return ks;
	}

	@Override
	public boolean accessPrimitive(String key, StringRef container) {
		try {
			container.value = this.getString(key);
			return true;
		} catch (ConversionException e) {
			try {
				container.value = this.getInt(key)+"";
				return true;
			} catch (NotFoundException | ConversionException e1) {
				return false;
			}
		} catch (NotFoundException e) {
			return false;
		}
	}

	@Override
	public boolean accessComplex(String key, AccessibleObject container) {
		try {
			Section s = this.getObject(key);
			((Section) container).setRaw(s.raw());
			return true;
		} catch (NotFoundException | ConversionException e) {
			return false;
		}
	}

	@Override
	public boolean accessComplex(String key, AccessibleArray container) {
		try {
			Collection c = this.getArray(key);
			((Collection)container).setRaw(c.raw()); 
			return true;
		} catch (NotFoundException | ConversionException e) {
			return false;
		}
	}


	public Section put(String key, String value) {
		if(value == null || value.equals(""))
			return this;
		
		if(key.contains(".")){
			String parent = key.substring(0, key.lastIndexOf("."));
			String child  = key.substring(key.lastIndexOf(".")+1);
			
			Section container = new Section("");
			container.setRaw(this.raw());
			
			String[] keys = steps(parent);
			for(String k : keys){
					if(!container.accessComplex(k, container)){
						Section s = new Section(k);
						container.put(s);
						container = s;
					}
			}
			container.raw().put(child, value);
		}
		else
			this.raw().put(key, value);
		return this;
	}


	public Section put(String key, int value) {
		if(key.contains(".")){
			String parent = key.substring(0, key.lastIndexOf("."));
			String child  = key.substring(key.lastIndexOf(".")+1);
			
			Section container = new Section("");
			container.setRaw(this.raw());
			
			String[] keys = steps(parent);
			for(String k : keys){
					if(!container.accessComplex(k, container)){
						Section s = new Section(k);
						container.put(s);
						container = s;
					}
			}
			container.raw().put(child, value);
		}
		else
			this.raw().put(key, value);
		
		return this;
	}


	public Section put(AccessibleObject ao) {
		Section s = (Section) ao;
		this.raw().put(ao.getName(), s.raw());
		return this;
	}


	public Section put(AccessibleArray aa) {
		Collection s = (Collection) aa;
		this.raw().put(aa.getName(), s.raw());
		return this;
	}

}
