package gov.nist.healthcare.unified.model.impl;

import java.util.ArrayList;

public class Trace {
	private String assertion;
	private ArrayList<String> reasons;
	
	public String getAssertion() {
		return assertion;
	}
	public void setAssertion(String assertion) {
		this.assertion = assertion;
	}
	public ArrayList<String> getReasons() {
		if(reasons == null)
			reasons = new ArrayList<String>();
		return reasons;
	}
	public void setReasons(ArrayList<String> reasons) {
		this.reasons = reasons;
	}
	@Override
	public String toString() {
		return "Trace [assertion=" + assertion + ", reasons=" + reasons + "]";
	}
	
	
	
	
}
