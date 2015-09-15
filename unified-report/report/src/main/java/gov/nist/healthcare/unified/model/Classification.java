package gov.nist.healthcare.unified.model;

import gov.nist.healthcare.unified.exceptions.ConversionException;
import gov.nist.healthcare.unified.exceptions.NotFoundException;

import java.util.Set;

public class Classification extends Section {
	
	public Classification(String n) {
		super(n);
		// TODO Auto-generated constructor stub
	}
	
	public void putCateg(Section detection, String cat) throws ConversionException{
		Collection categ;
		try {
			categ = (Collection) this.getArray(cat);
			categ.put(detection);
			
		} catch (NotFoundException e) {
			Collection c = new Collection(cat);
			c.put(detection);
			this.put(c);
		}
	}
	
	public Set categs(){
		return this.raw().keySet();
	}
}
