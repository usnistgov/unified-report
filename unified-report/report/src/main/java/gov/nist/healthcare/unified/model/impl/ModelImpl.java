package gov.nist.healthcare.unified.model.impl;

import java.util.ArrayList;

public class ModelImpl {
	private Metadata metadata;
	private ArrayList<ClassificationGroup> classifications;
	
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	public ArrayList<ClassificationGroup> getClassifications() {
		if(classifications == null)
			classifications = new ArrayList<ClassificationGroup>();
		return classifications;
	}
	public void setClassifications(ArrayList<ClassificationGroup> classifications) {
		this.classifications = classifications;
	}
	
	
	
	
}
