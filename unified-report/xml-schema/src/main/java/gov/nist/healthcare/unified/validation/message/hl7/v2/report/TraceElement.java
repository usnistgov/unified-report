package gov.nist.healthcare.unified.validation.message.hl7.v2.report;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Trace")
public class TraceElement {
	private String assertion;
	private Reasons reasons;
	
	@XmlElement(name = "Assertion")
	public String getAssertion() {
		return assertion;
	}
	public void setAssertion(String assertion) {
		this.assertion = assertion;
	}
	
	@XmlElement(name = "FailureReasons")
	public Reasons getReasons() {
		return reasons;
	}
	
	public void setReasons(Reasons reasons) {
		this.reasons = reasons;
	}
	
	
}
