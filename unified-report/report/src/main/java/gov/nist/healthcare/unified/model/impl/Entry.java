package gov.nist.healthcare.unified.model.impl;

import java.util.ArrayList;

public class Entry {
	private int line;
	private int column;
	private String path;
	private String description;
	private String category;
	private String classification;
	private ArrayList<Trace> stacktrace;
	private AssertionMetaData metadata;
	
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public ArrayList<Trace> getStacktrace() {
		if(stacktrace == null)
			stacktrace = new ArrayList<Trace>();
		return stacktrace;
	}
	public void setStacktrace(ArrayList<Trace> stacktrace) {
		this.stacktrace = stacktrace;
	}
	public AssertionMetaData getMetadata() {
		return metadata;
	}
	public void setMetadata(AssertionMetaData metadata) {
		this.metadata = metadata;
	}
	
	@Override
	public String toString() {
		return "Entry [line=" + line + ", column=" + column + ", path=" + path
				+ ", description=" + description + ", category=" + category
				+ ", classification=" + classification + ", stacktrace="
				+ stacktrace + ", metadata=" + metadata + "]";
	}
	
	
	
	
}
