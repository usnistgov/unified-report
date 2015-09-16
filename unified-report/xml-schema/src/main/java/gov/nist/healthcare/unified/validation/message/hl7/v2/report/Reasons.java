package gov.nist.healthcare.unified.validation.message.hl7.v2.report;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="FailureReasons")
public class Reasons {
	private List<String> reasons;

	@XmlElement(name = "Reason")
	public List<String> getReasons() {
		if(reasons == null)
			reasons = new ArrayList<String>();
		return reasons;
	}

	public void setReasons(List<String> reasons) {
		this.reasons = reasons;
	}
	
	
}
