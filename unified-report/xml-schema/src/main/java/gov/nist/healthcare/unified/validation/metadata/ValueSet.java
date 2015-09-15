package gov.nist.healthcare.unified.validation.metadata;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ValueSet")
public class ValueSet {
	private String bindingStrength;
	private String id;
	private String bindingLocation;
	private String extensibility;
	private String stability;
	
	@XmlElement(name="BindingStrength")
	public String getBindingStrength() {
		return bindingStrength;
	}
	public void setBindingStrength(String bindingStrength) {
		this.bindingStrength = bindingStrength;
	}
	
	@XmlElement(name="Id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement(name="BindingLocation")
	public String getBindingLocation() {
		return bindingLocation;
	}
	public void setBindingLocation(String bindingLocation) {
		this.bindingLocation = bindingLocation;
	}
	
	@XmlElement(name="Extensibility")
	public String getExtensibility() {
		return extensibility;
	}
	public void setExtensibility(String extensibility) {
		this.extensibility = extensibility;
	}
	
	@XmlElement(name="Stability")
	public String getStability() {
		return stability;
	}
	public void setStability(String stability) {
		this.stability = stability;
	}
	
	
}
