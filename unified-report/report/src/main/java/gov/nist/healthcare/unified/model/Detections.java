package gov.nist.healthcare.unified.model;

import gov.nist.healthcare.unified.exceptions.ConversionException;
import gov.nist.healthcare.unified.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Set;

import org.json.JSONObject;

public class Detections extends Section {
	public ArrayList<Classification> cl = new ArrayList<Classification>();
	public Detections() {
		super("detections");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Classification getObject(String k) throws NotFoundException, ConversionException{
		Section s;
		String search = k;
		String cla = k;
		if(k.contains(".")){
			search = k.substring(k.indexOf(".")+1);
			cla = k.substring(0,k.indexOf("."));
		}
		
		for(Classification c : cl){
			if(c.getName().equals(cla)){
				if(!k.contains("."))
					return c;
				else
					return (Classification) c.getObject(search);
			}
				
		}
		throw new NotFoundException();
	}
	
	@Override
	public Collection getArray(String k) throws NotFoundException, ConversionException{
		Section s;
		String search = k;
		String cla = k;
		if(k.contains(".")){
			search = k.substring(k.indexOf(".")+1);
			cla = k.substring(0,k.indexOf("."));
		}
		
		for(Classification c : cl){
			if(c.getName().equals(cla))
				return (Collection) c.getArray(search);
		}
		throw new NotFoundException();
	}
	
	@Override
	public String getString(String k) throws NotFoundException, ConversionException{
		Section s;
		String search = k;
		String cla = k;
		if(k.contains(".")){
			search = k.substring(k.indexOf(".")+1);
			cla = k.substring(0,k.indexOf("."));
		}
		
		for(Classification c : cl){
			if(c.getName().equals(cla))
				return c.getString(search);
		}
		
		throw new NotFoundException();
	}
	
	@Override
	public Integer getInt(String k) throws NotFoundException, ConversionException{
		Section s;
		String search = k;
		String cla = k;
		if(k.contains(".")){
			search = k.substring(k.indexOf(".")+1);
			cla = k.substring(0,k.indexOf("."));
		}
		
		for(Classification c : cl){
			if(c.getName().equals(cla))
				return c.getInt(search);
		}
		
		throw new NotFoundException();
	}
	
	private int get(String classf){
		for(int i = 0; i < cl.size(); i++){
			//
			if(cl.get(i).getName().equals(classf))
				return i;
		}
		return -1;
	}
	public void put(ArrayList<Section> detections) throws NotFoundException, ConversionException{
		for(Section s : detections){
			int x = this.get(s.getString("classification"));
			//
			if(x == -1){
				Classification c = new Classification(s.getString("classification"));
				c.putCateg(s,s.getString("category"));
				cl.add(c);
			}
			else {
				Classification c = cl.get(x);
				c.putCateg(s,s.getString("category"));
			}
		}
	}
	
	@Override
	public JSONObject raw(){
		JSONObject o = new JSONObject();
		//
		for(Classification c : cl){
			//
			o.put(c.getName(), c.raw());
		}
		return o;
	}
	
	public ArrayList<Classification> classes(){
		return cl;
	}

	public Section asSection() {
		Section s = new Section("detections");
		s.setRaw(this.raw());
		return s;
	}
	
	public void fromSection(Section s) {
		this.cl = new ArrayList<Classification>();
		for(Object o : s.keys()){
			Classification c = new Classification(o.toString());
			try {
				c.setRaw(((Section) s.getObject(o.toString())).raw());
			} catch (NotFoundException | ConversionException e) {
				
			}
			cl.add(c);
		}
	}
}
