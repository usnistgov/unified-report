package gov.nist.healthcare.unified.model;

import gov.nist.healthcare.unified.exceptions.ConversionException;
import gov.nist.healthcare.unified.exceptions.NotFoundException;

import org.json.JSONArray;
import org.json.JSONException;

public class Collection implements AccessibleArray {
	private JSONArray sections = new JSONArray();
	public String name;
	
	
	public Collection(String n){
		name = n;
	}
	
	public JSONArray raw(){
		return sections;
	}
	
	public void setRaw(JSONArray r){
		sections = r;
	}
	
	public boolean isEmpty(){
		return sections.length() == 0;
	}
	
	public int size(){
		return sections.length();
	}

	@Override
	public Integer getInt(int i) throws NotFoundException, ConversionException {
		try {
			if(i < this.raw().length() && i >= 0){
				return this.raw().getInt(i);
			}
			else
				throw new NotFoundException();
		}
		catch(JSONException ex){
			throw new ConversionException();
		}
	}

	@Override
	public Section getObject(int i) throws NotFoundException,
			ConversionException {
		try {
			if(i < this.raw().length() && i >= 0){
				Section cl = new Section("");
				cl.setRaw(this.raw().getJSONObject(i));
				return cl;
			}
			else
				throw new NotFoundException();
		}
		catch(JSONException ex){
			throw new ConversionException();
		}
	}

	@Override
	public Collection getArray(int i) throws NotFoundException,
			ConversionException {
		try {
			if(i < this.raw().length() && i >= 0){
				Collection cl = new Collection("");
				cl.setRaw(this.raw().getJSONArray(i));
				return cl;
			}
			else
				throw new NotFoundException();
		}
		catch(JSONException ex){
			throw new ConversionException();
		}
	}

	@Override
	public String getName() {
		return name;
	}

	public void put(String value) {
		if(value == null || value.equals(""))
			return;
		
		if(!value.isEmpty())
			this.raw().put(value);
		
	}

	public void put(int value) {
		this.raw().put(value);
	}

	public void put(AccessibleObject ao) {
		Section s = (Section) ao;
		this.raw().put(s.raw());
	}

	public void put(AccessibleArray aa) {
		Collection s = (Collection) aa;
		this.raw().put(s.raw());
	}

	@Override
	public String getString(int i) throws NotFoundException,
			ConversionException {
		try {
			if(i < this.raw().length() && i >= 0){
				return this.raw().getString(i);
			}
			else
				throw new NotFoundException();
		}
		catch(JSONException ex){
			throw new ConversionException();
		}
	}
}
