package gov.nist.healthcare.unified.model;

import hl7.v2.validation.ValidationContext;

public class ValidationBody {

	
	private ValidationContext validationContext;
	private String content;
	private String id;
	private String config;
	private Section service;
	
	public ValidationContext getValidationContext() {
		return validationContext;
	}
	public void setValidationContext(ValidationContext validationContext) {
		this.validationContext = validationContext;
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
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public Section getService() {
		return service;
	}
	public void setService(Section service) {
		this.service = service;
	}
	
	

    
	
}
