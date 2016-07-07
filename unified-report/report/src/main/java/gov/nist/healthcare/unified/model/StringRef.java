package gov.nist.healthcare.unified.model;

public class StringRef {
	
	public StringRef(String s){
		this.value = s;
	}
	
	public StringRef(){
		this.value = "";
	}
	
	public String value = "";
	public String toString(){
		return value;
	}
}
