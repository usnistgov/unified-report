package gov.nist.healthcare.unified.model;

import java.io.Reader;

import gov.nist.healthcare.unified.model.Section;
import hl7.v2.validation.ValidationContext;

public class ValidationBody {
	
	private ValidationContext validationContext;
	private Reader config;
	private String content;
	private String id;
	private String profile;
	private Section section;
	
	



	public ValidationBody(ValidationContext validationContext, Reader config, String content, String id, String profile, Section section) {
		super();
		this.validationContext = validationContext;
		this.config = config;
		this.content = content;
		this.id = id;
		this.profile = profile;
		this.section = section;
	}

	public ValidationBody() {
		super();
	}
	
	public ValidationContext getValidationContext() {
		return validationContext;
	}
	public void setValidationContext(ValidationContext validationContext) {
		this.validationContext = validationContext;
	}
	public Reader getConfig() {
		return config;
	}
	public void setConfig(Reader config) {
		this.config = config;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}
	
	
}
