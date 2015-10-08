package gov.nist.healthcare.unified.filter;

import gov.nist.healthcare.unified.expressions.Action;

import java.util.ArrayList;

public class Restriction {
	public ArrayList<Condition> conditions = new ArrayList<Condition>();
	public Action action;
	
	public void add(Condition c){
		this.conditions.add(c);
	}
	
	public void setAction(Action a){
		this.action = a;
	}
}
