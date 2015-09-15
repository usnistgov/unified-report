package gov.nist.healthcare.unified.model;

import gov.nist.healthcare.unified.exceptions.ConversionException;
import gov.nist.healthcare.unified.exceptions.NotFoundException;

public interface AccessibleArray {
	
	//Getters
	public String getName();
	public String getString(int i) throws NotFoundException, ConversionException;
	public Integer getInt(int i) throws NotFoundException, ConversionException;
	public AccessibleObject getObject(int i) throws NotFoundException, ConversionException;
	public AccessibleArray getArray(int i) throws NotFoundException, ConversionException;
	
	//Helpers
	public int size();
	public boolean isEmpty();
}	
