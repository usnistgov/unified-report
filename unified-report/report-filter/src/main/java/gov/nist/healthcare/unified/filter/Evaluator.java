package gov.nist.healthcare.unified.filter;

import gov.nist.healthcare.unified.expressions.Exp;

public class Evaluator {
	public static boolean evaluate(String val1, Exp e, String val2) {
		if (e == Exp.Contains) {
			return val1.contains(val2);
		} else if (e == Exp.Endswith) {
			return val1.endsWith(val2);
		} else if (e == Exp.Equals) {
			return val1.equals(val2);
		} else if (e == Exp.Format) {
			return val1.matches(val2);
		} else if (e == Exp.Greater) {
			try {
				int a = Integer.parseInt(val1);
				int b = Integer.parseInt(val2);
				return a > b;
			} catch (Exception ep) {
				return val1.compareTo(val2) > 0;
			}
		} else if (e == Exp.Lower) {
			try {
				int a = Integer.parseInt(val1);
				int b = Integer.parseInt(val2);
				return a > b;
			} catch (Exception ep) {
				return val1.compareTo(val2) < 0;
			}
		} else if (e == Exp.StartsWith) {
			return val1.startsWith(val2);
		}
		return false;
	}
	
	public static boolean evaluate(Condition r,String val){
		return Evaluator.evaluate(val, r.e, r.value);
	}
	
}
