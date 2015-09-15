package gov.nist.healthcare.unified.model.impl;

import java.util.ArrayList;

public class ClassificationGroup {
	private String classification;
	private ArrayList<CategoryGroup> categories;
	
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public ArrayList<CategoryGroup> getCategories() {
		if(categories == null)
			categories = new ArrayList<CategoryGroup>();
		return categories;
	}
	public void setCategories(ArrayList<CategoryGroup> categories) {
		this.categories = categories;
	}
	
	
}
