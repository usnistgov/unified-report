package gov.nist.healthcare.unified.validation.message.hl7.v2.report;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="StackTrace")
public class StackTrace {
	private List<TraceElement> traces;

	@XmlElement(name = "Trace")
	public List<TraceElement> getTraces() {
		if(traces == null)
			traces = new ArrayList<TraceElement>();
		return traces;
	}

	public void setTraces(List<TraceElement> traces) {
		this.traces = traces;
	}
	
}
