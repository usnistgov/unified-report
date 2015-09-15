package gov.nist.healthcare.unified.validation.metadata;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MetaData")
public class MetaData {
	private Reference reference;
	private ValueSet vs;

	@XmlElement(name="ValueSet")
	public ValueSet getVs() {
		return vs;
	}

	public void setVs(ValueSet vs) {
		this.vs = vs;
	}

	@XmlElement(name="Reference")
	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}
}
