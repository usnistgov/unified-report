package gov.nist.healthcare.unified.filter;

import gov.nist.healthcare.unified.expressions.Action;
import gov.nist.healthcare.unified.model.Section;

import java.util.HashMap;

public class Group {
	public HashMap<String,Section> map = new HashMap<String,Section>();
	public Action action;
}
