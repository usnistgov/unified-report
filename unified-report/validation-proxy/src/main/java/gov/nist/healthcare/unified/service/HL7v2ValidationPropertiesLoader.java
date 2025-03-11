package gov.nist.healthcare.unified.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

@Service
public class HL7v2ValidationPropertiesLoader {

	private Map<String, String> validationUrlsMap; // Store the map as a field

	public HL7v2ValidationPropertiesLoader() {
		loadValidationUrls(); // Load the map in the constructor
	}

	private void loadValidationUrls() {
		try {
			validationUrlsMap = new HashMap<>(); // Initialize the map
			ClassPathResource resource = new ClassPathResource("validation-service-config.properties");
			Properties properties = PropertiesLoaderUtils.loadProperties(resource);
			properties.stringPropertyNames().forEach(key -> validationUrlsMap.put(key, properties.getProperty(key)));

		} catch (IOException e) {
		}
	}

	public Map<String, String> getValidationUrlsMap() {
		return validationUrlsMap;
	}
}