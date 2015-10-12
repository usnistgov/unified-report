package gov.nist.healthcare.unified.filter;

import gov.nist.healthcare.unified.expressions.Exp;

public class Condition {
	public String entry_field;
	public Exp e;
	public String value;
	
	public Condition(String e, Exp ex, String val){
		this.entry_field = e;
		this.e = ex;
		this.value = val;
	}
}
