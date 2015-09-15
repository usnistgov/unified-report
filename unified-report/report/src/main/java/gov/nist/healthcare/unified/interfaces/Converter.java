package gov.nist.healthcare.unified.interfaces;

import gov.nist.healthcare.unified.exceptions.ConversionException;
import gov.nist.healthcare.unified.model.EnhancedReport;

public interface Converter {
	Object convert(EnhancedReport s)  throws ConversionException;
	EnhancedReport unserialize(String s)  throws ConversionException;
	String getFormat();
}
