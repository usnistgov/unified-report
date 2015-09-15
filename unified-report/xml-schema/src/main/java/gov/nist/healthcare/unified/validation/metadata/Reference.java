package gov.nist.healthcare.unified.validation.metadata;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Reference")
public class Reference {
	private String chapter;
	private String section;
	private String page;
	private String url;
	private String source;
	private String generatedBy;
	private String referencePath;
	private String testDataCategorization;
	
	@XmlElement(name="Chapter")
	public String getChapter() {
		return chapter;
	}
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}
	@XmlElement(name="Section")
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	@XmlElement(name="Page")
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	@XmlElement(name="Url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@XmlElement(name="Source")
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@XmlElement(name="GeneratedBy")
	public String getGeneratedBy() {
		return generatedBy;
	}
	public void setGeneratedBy(String generatedBy) {
		this.generatedBy = generatedBy;
	}
	@XmlElement(name="ReferencePath")
	public String getReferencePath() {
		return referencePath;
	}
	public void setReferencePath(String referencePath) {
		this.referencePath = referencePath;
	}
	@XmlElement(name="TestDataCategorization")
	public String getTestDataCategorization() {
		return testDataCategorization;
	}
	public void setTestDataCategorization(String testDataCategorization) {
		this.testDataCategorization = testDataCategorization;
	}
	
}
