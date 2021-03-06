package gov.nist.healthcare.unified.model.impl;

public class Metadata {
	private String standardType;
	private String validationType;
	private String serviceName;
	private String serviceVersion;
	private String serviceProvider;
	private String profileOrgName;
	private String profileIdentifier;
	private String profileVersion;
	private String profileName;
	private String profileType;
	private String profileHL7Version;
	private String profileSpecification;
	private String profileMessageType;
	private String profileDate;
	private int affirmativesCount;
	private int alertsCount;
	private int errorsCount;
	private int warningCount;
	private int infoCount;
	private int specErrorCount;
	
	private String messageContent;
	
	public String getProfileIdentifier() {
		return profileIdentifier;
	}

	public void setProfileIdentifier(String profileIdentifier) {
		this.profileIdentifier = profileIdentifier;
	}

	public String getProfileHL7Version() {
		return profileHL7Version;
	}

	public void setProfileHL7Version(String profileHL7Version) {
		this.profileHL7Version = profileHL7Version;
	}

	public String getProfileSpecification() {
		return profileSpecification;
	}

	public void setProfileSpecification(String profileSpecification) {
		this.profileSpecification = profileSpecification;
	}

	private String messageEncoding;

	public String getStandardType() {
		return standardType;
	}

	public void setStandardType(String standardType) {
		this.standardType = standardType;
	}

	public String getValidationType() {
		return validationType;
	}

	public void setValidationType(String validationType) {
		this.validationType = validationType;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public String getProfileOrgName() {
		return profileOrgName;
	}

	public void setProfileOrgName(String profileOrgName) {
		this.profileOrgName = profileOrgName;
	}

	public String getProfileVersion() {
		return profileVersion;
	}

	public void setProfileVersion(String profileVersion) {
		this.profileVersion = profileVersion;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getProfileType() {
		return profileType;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}


	public int getAffirmativesCount() {
		return affirmativesCount;
	}

	public void setAffirmativesCount(int affirmativesCount) {
		this.affirmativesCount = affirmativesCount;
	}

	public int getAlertsCount() {
		return alertsCount;
	}

	public void setAlertsCount(int alertsCount) {
		this.alertsCount = alertsCount;
	}

	public int getErrorsCount() {
		return errorsCount;
	}

	public void setErrorsCount(int errorsCount) {
		this.errorsCount = errorsCount;
	}

	public int getWarningCount() {
		return warningCount;
	}

	public void setWarningCount(int warningCount) {
		this.warningCount = warningCount;
	}

	public int getInfoCount() {
		return infoCount;
	}

	public void setInfoCount(int infoCount) {
		this.infoCount = infoCount;
	}
	
	public int getSpecErrorCount() {
		return specErrorCount;
	}

	public void setSpecErrorCount(int specErrorCount) {
		this.specErrorCount = specErrorCount;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getMessageEncoding() {
		return messageEncoding;
	}

	public void setMessageEncoding(String messageEncoding) {
		this.messageEncoding = messageEncoding;
	}

	@Override
	public String toString() {
		return "Metadata [standardType=" + standardType + ", validationType="
				+ validationType + ", serviceName=" + serviceName
				+ ", serviceVersion=" + serviceVersion + ", serviceProvider="
				+ serviceProvider + ", profileOrgName=" + profileOrgName
				+ ", profileVersion=" + profileVersion + ", profileName="
				+ profileName + ", profileType=" + profileType
				+ ", profileMessageType=" + profileMessageType
				+ ", profileDate=" + profileDate + ", affirmativesCount="
				+ affirmativesCount + ", alertsCount=" + alertsCount
				+ ", errorsCount=" + errorsCount + ", warningCount="
				+ warningCount + ", infoCount=" + infoCount + ", specErrorCount=" + specErrorCount
				+ ", messageContent=" + messageContent + ", messageEncoding="
				+ messageEncoding + "]";
	}

	public String getProfileDate() {
		return profileDate;
	}

	public void setProfileDate(String profileDate) {
		this.profileDate = profileDate;
	}

	public String getProfileMessageType() {
		return profileMessageType;
	}

	public void setProfileMessageType(String profileMessageType) {
		this.profileMessageType = profileMessageType;
	}

}
