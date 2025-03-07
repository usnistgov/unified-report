package validator;


import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import javax.xml.validation.Schema;

import gov.nist.validation.report.Report;
import hl7.v2.profile.Profile;
import hl7.v2.profile.XMLDeserializer;
import hl7.v2.validation.SyncHL7Validator;
import hl7.v2.validation.ValidationContext;
import hl7.v2.validation.content.ConformanceContext;
import hl7.v2.validation.content.DefaultConformanceContext;
import hl7.v2.validation.vs.ValueSetLibrary;
import hl7.v2.validation.vs.ValueSetLibraryImpl;

public class Validation {

//	public static ValueSetFactory getDefaultValueSetFactoryWithoutHTTP(ValueSetLibrary library) {
//		return new DefaultValueSetFactory(
//				library,
//				new DefaultValueSetFactoryConfiguration(
//						true,
//						false
//				)
//		);
//	}
//
//	public static ValueSetFactory getDefaultValueSetFactoryWithHTTP(CloseableHttpClient client, ValueSetLibrary library) {
//		return new DefaultValueSetFactory(
//				client,
//				library,
//				new DefaultValueSetFactoryConfiguration(
//						true,
//						true
//				)
//		);
//	}
	
//	public static Report validate(String profile,String constraint,String vs,String message,String id) throws Exception{
//		Profile is_pro = getProfile(profile);
//		ConformanceContext is_cons = getConformanceContext(constraint);
//		ValueSetLibrary is_vs = getValueSetLibrary(vs);		
//		return new SyncHL7Validator(is_pro, getDefaultValueSetFactoryWithoutHTTP(is_vs), is_cons).check(message, id);
//	}
//	
//	public static Report validate(Profile profile,ConformanceContext constraint,ValueSetLibrary vs,String message,String id,Reader configuration) throws Exception{			
//		return new SyncHL7Validator(profile, getDefaultValueSetFactoryWithoutHTTP(vs), constraint).checkUsingConfiguration(message, id,configuration);
//	}
//	
//	public static Report validate(Profile profile,ConformanceContext constraint,ValueSetLibrary vs,String message,String id) throws Exception{	
//		return new SyncHL7Validator(profile, getDefaultValueSetFactoryWithoutHTTP(vs), constraint).check(message, id);
//	}
	
	
	public static Report validateNew(ValidationContext context,String message,String id) throws Exception{	
		return new SyncHL7Validator(context).check(message, id);
	}
	
	public static Report validateNewWithConfig(ValidationContext context,String message,String id,Reader configuration) throws Exception{	
		return new SyncHL7Validator(context).checkUsingConfiguration(message, id, configuration);
	}
	
	public static Report validateXML(String xmlFile, List<Schema> schemas, List<String> schematrons, String phase){
		return gov.nist.hit.xml.Validator.validate(xmlFile,schemas,schematrons,phase);
	}
	
	public static Report validateWCTP(String wctpFile){
		return gov.nist.hit.wctp.WCTPValidation.generic(wctpFile);
	}
	
	
	
//	public static Report validate(){
//		try {
//			
//			SyncHL7Validator validator = createValidator("/izp/Profile.xml", "/izp/Constraints.xml", "/izp/ValueSets_Validation.xml");
//			// The message instance (ER7)
//			String message = Util.streamAsString("/izp/message.er7");
//
//			// The check method will throw an exception if something goes wrong
//			Report report = validator.check(message, "bfb1c703-c96e-4f2b-8950-3f5b1c9cd2d8");
//			
//			// Print the report
//			//report.prettyPrint();
//			
//			return report;
//
//
//
//		} catch(Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	private static SyncHL7Validator createValidator(
//			String profileFileName,
//			String constraintsFileName,
//			String valueSetLibFileName) throws Exception {
//		
//		// The profile in XML
//		InputStream profileXML = Validation.class.getResourceAsStream(profileFileName);
//
//		// ### [Update]
//		// The conformance context file XML
//		InputStream confContext = Validation.class.getResourceAsStream(constraintsFileName);
//
//		// The get() at the end will throw an exception if something goes wrong
//		Profile profile = XMLDeserializer.deserialize(profileXML).get();
//
//		// ### [Update]
//		// The get() at the end will throw an exception if something goes wrong
//		ConformanceContext c = DefaultConformanceContext.apply(confContext).get();
//
//		// The plugin map. This should be empty if no plugin is used
//		Map<String, Function3<Plugin, Element, Separators, EvalResult>> pluginMap = Map$.MODULE$.empty();
//		
//		
//		InputStream vsLibXML = Validation.class.getResourceAsStream(valueSetLibFileName);
//		ValueSetLibrary valueSetLibrary = ValueSetLibrary.apply(vsLibXML).get();
//
//		// A java friendly version of an HL7 validator
//		// This should be a singleton for a specific tool. We create it once and reuse it across validations
//		return new SyncHL7Validator(profile, valueSetLibrary, c);
//	}
	
	static Profile getProfile(String name) {
		// The profile in XML
		InputStream profileXML = Util.class.getResourceAsStream(name);
					
		// The get() at the end will throw an exception if something goes wrong
		Profile profile = XMLDeserializer.deserialize(profileXML).get();
		
		return profile;
	}
	
	/**
	 * @return An instance of the conformance context
	 */
	static ConformanceContext getConformanceContext(String name) {
		// The first conformance context XML file
		InputStream contextXML1 = Util.class.getResourceAsStream(name);

		// The second conformance context XML file
		//InputStream contextXML2 = ValidationApp.class.getResourceAsStream("/Predicates.xml");

		List<InputStream> confContexts = Arrays.asList( contextXML1 );

		// The get() at the end will throw an exception if something goes wrong
		ConformanceContext c = DefaultConformanceContext.apply(confContexts).get();
		return c;
	}
	
	/**
	 * @return An instance of the value set library
	 */
	static ValueSetLibrary getValueSetLibrary(String name) {
		InputStream vsLibXML = Util.class.getResourceAsStream(name);
		
		ValueSetLibrary valueSetLibrary = ValueSetLibraryImpl.apply(vsLibXML).get();
		
		return valueSetLibrary;
	}
}
