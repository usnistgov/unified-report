package gov.nist.healthcare.unified.validation.message.hl7.v2.report;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="TestCaseReference")
public class TestCaseReference {
	private String testPlan;
	private String testGroup;
	private String testCase;
	private String testStep;
	
	@XmlElement(name="TestPlan")
	public String getTestPlan() {
		return testPlan;
	}
	
	public void setTestPlan(String testPlan) {
		this.testPlan = testPlan;
	}
	
	@XmlElement(name="TestGroup")
	public String getTestGroup() {
		return testGroup;
	}
	
	public void setTestGroup(String testGroup) {
		this.testGroup = testGroup;
	}
	
	@XmlElement(name="TestCase")
	public String getTestCase() {
		return testCase;
	}
	
	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}
	
	@XmlElement(name="TestStep")
	public String getTestStep() {
		return testStep;
	}
	
	public void setTestStep(String testStep) {
		this.testStep = testStep;
	}
	
}
