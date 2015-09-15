package gov.nist.healthcare.unified.model.impl;

public class ValueSet extends AssertionMetaData {
	private String id;
	private String stability;
	private String extensibility;
	private String bindingStrength;
	private String bindingLocation;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStability() {
		return stability;
	}
	public void setStability(String stability) {
		this.stability = stability;
	}
	public String getExtensibility() {
		return extensibility;
	}
	public void setExtensibility(String extensibility) {
		this.extensibility = extensibility;
	}
	public String getBindingStrength() {
		return bindingStrength;
	}
	public void setBindingStrength(String bindingStrength) {
		this.bindingStrength = bindingStrength;
	}
	public String getBindingLocation() {
		return bindingLocation;
	}
	public void setBindingLocation(String bindingLocation) {
		this.bindingLocation = bindingLocation;
	}
	
	
}
