package gov.nist.healthcare.unified.model.impl;

public class Reference extends AssertionMetaData {
	private String chapter;
	private String section;
	private String page;
	private String url;
	private String source;
	private String generatedBy;
	private String referencePath;
	private String testDataCategorization;
	
	public String getChapter() {
		return chapter;
	}
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getGeneratedBy() {
		return generatedBy;
	}
	public void setGeneratedBy(String generatedBy) {
		this.generatedBy = generatedBy;
	}
	public String getReferencePath() {
		return referencePath;
	}
	public void setReferencePath(String referencePath) {
		this.referencePath = referencePath;
	}
	public String getTestDataCategorization() {
		return testDataCategorization;
	}
	public void setTestDataCategorization(String testDataCategorization) {
		this.testDataCategorization = testDataCategorization;
	}
	
	
}
