package gov.nist.healthcare.unified.model;

import gov.nist.healthcare.unified.exceptions.ConversionException;
import gov.nist.healthcare.unified.exceptions.NotFoundException;

import java.util.List;

public interface AccessibleObject {
	
	//Getters
	public String getName();
	public String getString(String key) throws NotFoundException, ConversionException;
	public Integer getInt(String key) throws NotFoundException, ConversionException;
	public AccessibleObject getObject(String key) throws NotFoundException, ConversionException;
	public AccessibleArray getArray(String key) throws NotFoundException, ConversionException;
	public List<String> keys();
	
	//Helpers
	public boolean accessPrimitive(String key,StringRef container);
	public boolean accessComplex(String key,AccessibleObject container);
	public boolean accessComplex(String key,AccessibleArray container);
	
	//bool
	public boolean isEmpty();
}
