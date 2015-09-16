package gov.nist.healthcare.unified.model.impl;

import java.util.ArrayList;

public class CategoryGroup {
	private String category;
	private ArrayList<Entry> entries;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public ArrayList<Entry> getEntries() {
		if(entries == null)
			entries = new ArrayList<Entry>();
		return entries;
	}
	public void setEntries(ArrayList<Entry> entries) {
		this.entries = entries;
	}
	
}
