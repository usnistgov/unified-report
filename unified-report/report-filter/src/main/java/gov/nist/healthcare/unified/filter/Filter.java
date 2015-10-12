package gov.nist.healthcare.unified.filter;

import gov.nist.healthcare.unified.exceptions.ConversionException;
import gov.nist.healthcare.unified.exceptions.NotFoundException;
import gov.nist.healthcare.unified.expressions.Action;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.model.Section;

import java.util.ArrayList;
import java.util.HashMap;

public class Filter {
	private ArrayList<Restriction> restrictions = new ArrayList<Restriction>();

	public void restrain(Restriction r) {
		restrictions.add(r);
	}

	public EnhancedReport filter(EnhancedReport r) {
		EnhancedReport result = new EnhancedReport();
		result.setMetadata(r.getMetadata());
		result.initCounts();
		
		ArrayList<Section> entries = r.getDetections().entries();
		ArrayList<Section> Rentries = new ArrayList<Section>();
		ArrayList<Integer> kept = new ArrayList<Integer>();
		boolean match = false;
		for (Section s : entries) {
			match = false;
			for (int i = 0; i < restrictions.size(); i++) {
				Restriction restriction = restrictions.get(i);
				if(test(s,restriction.conditions)){
					match = true;
					if(restriction.action == Action.KeepOne && !kept.contains(i)){
						Rentries.add(s);
						kept.add(i);
						break;
					}
					else if(restriction.action == Action.Remove)
						break;
				}
			}
			if(!match)
				Rentries.add(s);
		}
		result.put(Rentries);
		return result;
	}
	
	public static EnhancedReport removeDuplicate(EnhancedReport r) {
		EnhancedReport result = new EnhancedReport();
		result.setMetadata(r.getMetadata());
		result.initCounts();
		
		ArrayList<Section> entries = r.getDetections().entries();
		ArrayList<Section> Rentries = new ArrayList<Section>();
		for (Section s : entries) {
			if(!search(Rentries,s))
				Rentries.add(s);
		}
		result.put(Rentries);
		return result;
	}
	
	public static boolean same(Section entry1,Section entry2){
		try {
			return path(entry1.getString("path")).equals(path(entry2.getString("path"))) && entry1.getString("description").equals(entry2.getString("description"));
		} catch (NotFoundException | ConversionException e) {
			return false;
		}
	}
	
	public static boolean search(ArrayList<Section> list, Section s){
		for (Section se : list) {
			if(same(se,s))
				return true;
		}
		return false;
	}
	public boolean test(Section s, ArrayList<Condition> r) {
		for (Condition rest : r) {
			try {
				if (!Evaluator.evaluate(rest, s.getString(rest.entry_field))) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}
	
	public static String path(String p){
		String SEG = p.substring(0, 3);
		if(p.contains("-")){
			return SEG + "-" + p.split("-")[1];
		}
		else
			return SEG;
	}
}
