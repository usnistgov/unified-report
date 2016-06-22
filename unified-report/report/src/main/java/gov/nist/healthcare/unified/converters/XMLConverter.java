package gov.nist.healthcare.unified.converters;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import gov.nist.healthcare.unified.exceptions.ConversionException;
import gov.nist.healthcare.unified.exceptions.NotFoundException;
import gov.nist.healthcare.unified.interfaces.Converter;
import gov.nist.healthcare.unified.message.EncodingConstants;
import gov.nist.healthcare.unified.model.Classification;
import gov.nist.healthcare.unified.model.Collection;
import gov.nist.healthcare.unified.model.Detections;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.model.Section;
import gov.nist.healthcare.unified.model.StringRef;
import gov.nist.healthcare.unified.validation.message.ReportHeader;
import gov.nist.healthcare.unified.validation.message.StandardTypeType;
import gov.nist.healthcare.unified.validation.message.ValidationType;
import gov.nist.healthcare.unified.validation.message.hl7.v2.context.HL7V2MessageValidationContextDefinition;
import gov.nist.healthcare.unified.validation.message.hl7.v2.context.MessageFailureInterpretationV2;
import gov.nist.healthcare.unified.validation.message.hl7.v2.context.MessageFailureV2;
import gov.nist.healthcare.unified.validation.message.hl7.v2.report.HL7V2MessageReport;
import gov.nist.healthcare.unified.validation.message.hl7.v2.report.HL7V2MessageValidationReport;
import gov.nist.healthcare.unified.validation.message.hl7.v2.report.Reasons;
import gov.nist.healthcare.unified.validation.message.hl7.v2.report.StackTrace;
import gov.nist.healthcare.unified.validation.message.hl7.v2.report.TestCaseReference;
import gov.nist.healthcare.unified.validation.message.hl7.v2.report.TraceElement;
import gov.nist.healthcare.unified.validation.metadata.MetaData;
import gov.nist.healthcare.unified.validation.metadata.Reference;
import gov.nist.healthcare.unified.validation.metadata.ValueSet;

public class XMLConverter implements Converter {

	@Override
	public String convert(EnhancedReport s) throws ConversionException {

		try {
			HL7V2MessageValidationReport mvr = new HL7V2MessageValidationReport();
			ReportHeader rh = new ReportHeader();
			HL7V2MessageReport.MetaData md = new HL7V2MessageReport.MetaData();
			HL7V2MessageValidationContextDefinition ctx = new HL7V2MessageValidationContextDefinition();
			HL7V2MessageReport.MetaData.Message msg = new HL7V2MessageReport.MetaData.Message();
			HL7V2MessageReport.MetaData.Profile profile = new HL7V2MessageReport.MetaData.Profile();
			TestCaseReference ref = new TestCaseReference();
			
			Section cursor = new Section("");
			StringRef tmp = new StringRef();


			ctx.setFailureInterpretation(getFailuresInterpretation(s
					.getMetadata().getArray("failuresInterpretation")));
			md.setContext(ctx);
			
			Section mds = s.getMetadata();
			if (mds != null) {
				
				if(mds.accessPrimitive("type", tmp)){
					rh.setType(tmp.value);
				}
				if(mds.accessPrimitive("date", tmp)){
					Date dob=null;
					DateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
					dob=df.parse(tmp.value);
					GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(dob);
					
					XMLGregorianCalendar xmlDate2 = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), (cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET)) / (60*1000));	
					rh.setDateOfTest(xmlDate2);
					
					XMLGregorianCalendar xmlDate3 = DatatypeFactory.newInstance().newXMLGregorianCalendarTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND), cal.get(Calendar.MILLISECOND),(cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET)) / (60*1000));
					rh.setTimeOfTest(xmlDate3);
				}
				if (mds.accessComplex("service", cursor)) {
					if (cursor.accessPrimitive("name", tmp))
						rh.setServiceName(tmp.toString());
					if (cursor.accessPrimitive("provider", tmp))
						rh.setServiceProvider(tmp.toString());
					if (cursor.accessPrimitive("validationVersion", tmp))
						rh.setServiceVersion(tmp.toString());
				}
				
				if (mds.accessComplex("testCase", cursor)) {
					if (cursor.accessPrimitive("plan", tmp))
						ref.setTestPlan(tmp.toString());
					if (cursor.accessPrimitive("group", tmp))
						ref.setTestGroup(tmp.toString());
					if (cursor.accessPrimitive("case", tmp))
						ref.setTestCase(tmp.toString());
					if (cursor.accessPrimitive("step", tmp))
						ref.setTestStep(tmp.toString());
					rh.setTestCaseReference(ref);
				}
				
				if (mds.accessComplex("profile", cursor)) {
					if (cursor.accessPrimitive("name", tmp))
						profile.setName(tmp.toString());
					if (cursor.accessPrimitive("orgName", tmp))
						profile.setOrganization(tmp.toString());
					if (cursor.accessPrimitive("version", tmp))
						profile.setVersion(tmp.toString());
					if (cursor.accessPrimitive("type", tmp))
						profile.setType(tmp.toString());
					if (cursor.accessPrimitive("hl7version", tmp))
						profile.setHL7Version(tmp.toString());
					if (cursor.accessPrimitive("messageType", tmp))
						profile.setMessageType(tmp.toString());
					if (cursor.accessPrimitive("date", tmp))
						profile.setDate(tmp.toString());
					if (cursor.accessPrimitive("identifier", tmp))
						profile.setIdentifier(tmp.toString());
					if (cursor.accessPrimitive("specification", tmp))
						profile.setSpecification(tmp.toString());
				}

				if (mds.accessComplex("counts", cursor)) {
					
					if (cursor.accessPrimitive("affirmative", tmp))
						rh.setAffirmCount(Int(tmp.toString()));
					if (cursor.accessPrimitive("alert", tmp))
						rh.setAlertCount(Int(tmp.toString()));
					if (cursor.accessPrimitive("error", tmp))
						rh.setErrorCount(Int(tmp.toString()));
					if (cursor.accessPrimitive("warning", tmp))
						rh.setWarningCount(Int(tmp.toString()));
					if (cursor.accessPrimitive("informational", tmp))
						rh.setInfoCount(Int(tmp.toString()));
				}

				if (mds.accessComplex("message", cursor)) {
					if (cursor.accessPrimitive("content", tmp))
						msg.setEr7Message(tmp.toString());
					msg.setEncoding(EncodingConstants.ER_7);
				}

				if (mds.accessPrimitive("standardType", tmp))
					rh.setStandardType(StandardTypeType.fromValue(tmp
							.toString()));
				if (mds.accessPrimitive("validationType", tmp))
					rh.setValidationType(ValidationType.fromValue(tmp
							.toString()));

			}

			md.setMessage(msg);
			md.setProfile(profile);
			HL7V2MessageReport rep = new HL7V2MessageReport();
			rep.setAssertionList(getAssertions(s.getDetections()));
			
			rep.setMetaData(md);

			mvr.setSpecificReport(rep);
			mvr.setHeaderReport(rh);

			try {
				return this.objToString(mvr);
			} catch (Exception e) {
				return "";
			}

		} catch (Exception e1) {
			e1.printStackTrace();
			throw new ConversionException();
		}
	}

	private HL7V2MessageReport.AssertionList getAssertions(Detections ds) throws NotFoundException, ConversionException {
		HL7V2MessageReport.AssertionList aL = new HL7V2MessageReport.AssertionList();
		for (Classification cl : ds.classes()) {
			for (Object s_cat : cl.categs()) {
				Collection categ = cl.getArray(s_cat.toString());
				for (int i = 0; i < categ.size(); i++) {
					HL7V2MessageReport.AssertionList.Assertion tmp = new HL7V2MessageReport.AssertionList.Assertion();
					HL7V2MessageReport.AssertionList.Assertion.Location loc = new HL7V2MessageReport.AssertionList.Assertion.Location();
					StringRef sb = new StringRef();
					Collection cursor = new Collection("");
					Section cursor_1 = new Section("");
					Section cursor_2 = new Section("");
					StackTrace st = new StackTrace();
					MetaData mdN = new MetaData();
					Section detection = categ.getObject(i);
					// ---

					if (detection.accessPrimitive("description", sb))
						tmp.setDescription(sb.toString());
					if (detection.accessPrimitive("column", sb))
						loc.setColumn(Int(sb.toString()));
					if (detection.accessPrimitive("line", sb))
						loc.setLine(Int(sb.toString()));
					if (detection.accessPrimitive("path", sb))
						loc.setPath(sb.toString());
					tmp.setResult(cl.getName().toString().toLowerCase());
					tmp.setType(s_cat.toString().toUpperCase()
							.replace(" ", "_").replace("-", "_"));

					if (detection.accessComplex("stackTrace", cursor)) {
						for (int j = 0; j < cursor.size(); j++) {
							TraceElement te = new TraceElement();
							Reasons reas = new Reasons();
							Section trace = cursor.getObject(j);
							Collection clR = new Collection("");
							// ---
							if (trace.accessPrimitive("assertion", sb))
								te.setAssertion(sb.toString());
							if (detection.accessComplex("reasons", clR)) {
								for (int k = 0; k < clR.size(); k++) {
									reas.getReasons().add(clR.getString(k));
								}
							}
							// ---
							te.setReasons(reas);
							st.getTraces().add(te);
						}
						tmp.setStackTrace(st);
					}

					if (detection.accessComplex("metaData", cursor_1)) {
						if (cursor_1.accessComplex("valueSet", cursor_2)) {
							ValueSet vs = new ValueSet();
							if (cursor_2.accessPrimitive("id", sb))
								vs.setId(sb.toString());
							if (cursor_2.accessPrimitive("stability", sb))
								vs.setStability(sb.toString());
							if (cursor_2.accessPrimitive("extensibility", sb))
								vs.setExtensibility(sb.toString());
							if (cursor_2.accessPrimitive("bindingStrength", sb))
								vs.setBindingStrength(sb.toString());
							if (cursor_2.accessPrimitive("bindingLocation", sb))
								vs.setBindingLocation(sb.toString());
							mdN.setVs(vs);
						} else if (cursor_1
								.accessComplex("reference", cursor_2)) {
							Reference ref = new Reference();
							if (cursor_2.accessPrimitive("chapter", sb))
								ref.setChapter(sb.toString());
							if (cursor_2.accessPrimitive("section", sb))
								ref.setSection(sb.toString());
							if (cursor_2.accessPrimitive("page", sb))
								ref.setPage(sb.toString());
							if (cursor_2.accessPrimitive("url", sb))
								ref.setUrl(sb.toString());
							if (cursor_2.accessPrimitive("source", sb))
								ref.setSource(sb.toString());
							if (cursor_2.accessPrimitive("generatedBy", sb))
								ref.setGeneratedBy(sb.toString());
							if (cursor_2.accessPrimitive("referencePath", sb))
								ref.setReferencePath(sb.toString());
							if (cursor_2.accessPrimitive(
									"testDataCategorization", sb))
								ref.setTestDataCategorization(sb.toString());
							mdN.setReference(ref);
						}
						tmp.setMetaData(mdN);
					}

					// ---
					tmp.setLocation(loc);
					aL.getAssertion().add(tmp);
				}
			}
		}
		return aL;
	}

	private MessageFailureInterpretationV2 getFailuresInterpretation(
			Collection s) throws NotFoundException, ConversionException {
		MessageFailureInterpretationV2 mfi = new MessageFailureInterpretationV2();
		for (int i = 0; i < s.size(); i++) {
			MessageFailureV2 mf = new MessageFailureV2();
			Section sect = s.getObject(i);
			for (Object k : sect.keys()) {
				mf.setType(k.toString());
				mf.setResult(sect.getString(k.toString()));
			}
			mfi.getMessageFailure().add(mf);
		}
		return mfi;
	}

	@Override
	public EnhancedReport unserialize(String s) {
		try {
			HL7V2MessageValidationReport mvr = this.stringToObj(s);
			EnhancedReport er = new EnhancedReport();
			Section metadata = new Section("metaData");
			Detections detections = new Detections();
			ReportHeader rh = mvr.getHeaderReport();// new ReportHeader();
			HL7V2MessageReport.MetaData md = mvr.getSpecificReport()
					.getMetaData();// new HL7V2MessageReport.MetaData();
			HL7V2MessageValidationContextDefinition ctx = md.getContext();// new
																			// HL7V2MessageValidationContextDefinition();
			HL7V2MessageReport.MetaData.Message msg = md.getMessage();// new
																		// HL7V2MessageReport.MetaData.Message();
			HL7V2MessageReport.MetaData.Profile profile = md.getProfile();// new
																			// HL7V2MessageReport.MetaData.Profile();
			Section tmp;

			// ---MetaData
			if (profile != null) {
				tmp = new Section("profile");
				tmp.put("name", profile.getName())
						.put("orgName", profile.getOrganization())
						.put("version", profile.getVersion())
						.put("type", profile.getType())
						.put("hl7version", profile.getHL7Version())
						.put("messageType",profile.getMessageType())
						.put("date", profile.getDate())
						.put("specification", profile.getSpecification())
						.put("identifier", profile.getIdentifier());
				metadata.put(tmp);
			}

			if (rh != null) {
				
				if(rh.getDateOfTest() != null && rh.getTimeOfTest() != null){
					Date d = dateTime(rh.getDateOfTest().toGregorianCalendar().getTime(),rh.getTimeOfTest().toGregorianCalendar().getTime());
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
					metadata.put("date",formatter.format(d));
				}
				
				tmp = new Section("service");
				tmp.put("name", rh.getServiceName())
						.put("provider", rh.getServiceProvider())
						.put("validationVersion", rh.getServiceVersion());
				metadata.put(tmp);
				tmp = new Section("counts");
				tmp.put("affirmative", Int(rh.getAffirmCount()))
						.put("alert", Int(rh.getAlertCount()))
						.put("error", Int(rh.getErrorCount()))
						.put("warning", Int(rh.getWarningCount()))
						.put("informational", Int(rh.getInfoCount()));
				metadata.put(tmp);
				if(rh.getTestCaseReference() != null){
					tmp = new Section("testCase");
					tmp.put("plan", rh.getTestCaseReference().getTestPlan())
							.put("group", rh.getTestCaseReference().getTestGroup())
							.put("case", rh.getTestCaseReference().getTestCase())
							.put("step", rh.getTestCaseReference().getTestStep());
					metadata.put(tmp);
				}
				
				metadata.put("standardType", rh.getStandardType().value());
				metadata.put("validationType", rh.getValidationType().value());
				metadata.put("type",rh.getType());
			}

			if (msg != null) {
				tmp = new Section("message");
				tmp.put("content", EnhancedReport.messageF(msg.getEr7Message(),false)).put("encoding",
						msg.getEncoding().value());
				er.setOriginalMsg(msg.getEr7Message());
				metadata.put(tmp);
			}

			// ---Failures
			MessageFailureInterpretationV2 mfi = ctx.getFailureInterpretation();
			Collection mf = new Collection("failuresInterpretation");
			for (MessageFailureV2 fail : mfi.getMessageFailure()) {
				Section ss = new Section("");
				ss.put(fail.getType(), fail.getResult());
				mf.put(ss);
			}
			metadata.put(mf);
			// ---Detections
			HL7V2MessageReport.AssertionList aL = mvr.getSpecificReport()
					.getAssertionList();
			ArrayList<Section> dets = new ArrayList<Section>();
			for (HL7V2MessageReport.AssertionList.Assertion asser : aL
					.getAssertion()) {
				Section t_section = new Section("");
				HL7V2MessageReport.AssertionList.Assertion.Location loc = asser
						.getLocation();
				t_section.put("description", asser.getDescription())
						.put("column", Int(loc.getColumn()))
						.put("line", Int(loc.getLine()))
						.put("path", loc.getPath())
						.put("category", asser.getType())
						.put("classification", asser.getResult().toLowerCase());

				// --StackTrace
				if (asser.getStackTrace() != null) {
					StackTrace str = asser.getStackTrace();
					Collection c_str = new Collection("stackTrace");
					for (TraceElement tr : str.getTraces()) {
						Section s_tr = new Section("trace");
						s_tr.put("assertion", tr.getAssertion());
						Reasons rs = tr.getReasons();
						if (rs != null && rs.getReasons() != null
								&& rs.getReasons().size() > 0) {
							Collection c_r = new Collection("reasons");
							for (String r : rs.getReasons()) {
								c_r.put(r);
							}
							s_tr.put(c_r);
						}
						c_str.put(s_tr);
					}
					t_section.put(c_str);
				}
				// ---
				// --MetaData
				if (asser.getMetaData() != null) {
					Section mdt = new Section("metaData");
					if (asser.getMetaData().getVs() != null) {
						ValueSet vs = asser.getMetaData().getVs();
						Section s_vs = new Section("valueSet");
						s_vs.put("id", vs.getId())
								.put("stability", vs.getStability())
								.put("extensibility", vs.getExtensibility())
								.put("bindingStrength", vs.getBindingStrength())
								.put("bindingLocation", vs.getBindingLocation());
						mdt.put(s_vs);
					} else if (asser.getMetaData().getReference() != null) {
						Reference ref = asser.getMetaData().getReference();
						Section s_ref = new Section("reference");
						s_ref.put("chapter", ref.getChapter())
								.put("section", ref.getSection())
								.put("page", ref.getPage())
								.put("url", ref.getUrl())
								.put("source", ref.getSource())
								.put("generatedBy", ref.getGeneratedBy())
								.put("referencePath", ref.getReferencePath())
								.put("testDataCategorization",
										ref.getTestDataCategorization());
						mdt.put(s_ref);
					}
					t_section.put(mdt);
				}
				// ---
				dets.add(t_section);
			}
			detections.put(dets);
			er.setMetadata(metadata);
			er.setDetections(detections);
			// ---
			return er;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String getFormat() {
		// TODO Auto-generated method stub
		return "xml";
	}

	private String objToString(HL7V2MessageValidationReport r)
			throws JAXBException, UnsupportedEncodingException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String context = "gov.nist.healthcare.unified.validation.message.hl7.v2.report";
		JAXBContext jc;
		jc = JAXBContext.newInstance(context);
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(r, baos);
		return new String(baos.toByteArray(), "UTF-8");
	}

	private HL7V2MessageValidationReport stringToObj(String s)
			throws JAXBException {
		JAXBContext jc = JAXBContext
				.newInstance("gov.nist.healthcare.unified.validation.message.hl7.v2.report");
		Unmarshaller u = jc.createUnmarshaller();
		StringReader reader = new StringReader(s);
		HL7V2MessageValidationReport o = (HL7V2MessageValidationReport) u
				.unmarshal(reader);
		return o;
	}
	
	public Date dateTime(Date date, Date time) {
	    return new Date(
	                     date.getYear(), date.getMonth(), date.getDay(), 
	                     time.getHours(), time.getMinutes(), time.getSeconds()
	                   );
	}

	private int Int(String x) {
		return Integer.parseInt(x);
	}

	private String Int(Integer x) {
		if(x == null)
			return null;
		return x + "";
	}

}
