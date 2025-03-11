package gov.nist.healthcare.unified.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ValidationBody {

	
	private String content;
	private String id;
	private String config;
	private Section service;
	
	private String profile;
	private String valueSetLibrary;
	private String vsBindings;
	private List<String> constraints = new ArrayList<String>();
	private String coConstraints;
	private String slicings;
	
	private String serviceName;
	private String serviceProvider;
	private String context;
	
	private HashMap<String,String> apiKeys = new HashMap<String, String>();
	
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
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getValueSetLibrary() {
		return valueSetLibrary;
	}
	public void setValueSetLibrary(String valueSetLibrary) {
		this.valueSetLibrary = valueSetLibrary;
	}
	public String getVsBindings() {
		return vsBindings;
	}
	public void setVsBindings(String vsBindings) {
		this.vsBindings = vsBindings;
	}
	public List<String> getConstraints() {
		return constraints;
	}
	public void setConstraints(List<String> constraints) {
		this.constraints = constraints;
	}
	public String getCoConstraints() {
		return coConstraints;
	}
	public void setCoConstraints(String coConstraints) {
		this.coConstraints = coConstraints;
	}
	public String getSlicings() {
		return slicings;
	}
	public void setSlicings(String slicings) {
		this.slicings = slicings;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public HashMap<String, String> getApiKeys() {
		return apiKeys;
	}
	public void setApiKeys(HashMap<String, String> apiKeys) {
		this.apiKeys = apiKeys;
	}
	
	
	

    
	
}
