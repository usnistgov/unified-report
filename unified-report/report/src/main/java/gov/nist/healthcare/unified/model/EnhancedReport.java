package gov.nist.healthcare.unified.model;

import gov.nist.healthcare.unified.converters.JSONConverter;
import gov.nist.healthcare.unified.converters.ModelConverter;
import gov.nist.healthcare.unified.converters.XMLConverter;
import gov.nist.healthcare.unified.exceptions.ConversionException;
import gov.nist.healthcare.unified.exceptions.NotFoundException;
import gov.nist.healthcare.unified.interfaces.Converter;
import gov.nist.healthcare.unified.render.Render;
import gov.nist.healthcare.unified.render.ReportRender;
import gov.nist.validation.report.Report;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class EnhancedReport implements AccessibleObject {
	private Section metadata = new Section("metaData");
	private Detections detections = new Detections();
	private static ArrayList<Converter> converters = new ArrayList<Converter>();
	private static ArrayList<Render> renders = new ArrayList<Render>();
	private static boolean inited = false;

	public EnhancedReport() {
		super();

	}

	public static void initC() {
		if (inited)
			return;

		converters.add(new XMLConverter());
		converters.add(new JSONConverter());
		converters.add(new ModelConverter());
		renders.add(new ReportRender());
	}

	public static EnhancedReport fromValidation(Report r, String message,
			String profile, String id, ArrayList<Section> mds, String context) {
		EnhancedReport rp = new EnhancedReport();
		if (mds != null)
			EnhancerH.enhanceHeader(rp, mds, message);
		else
			EnhancerH.enhanceHeader(rp, message);

		EnhancerH.enhanceWithReport(rp, r);
		EnhancerH.enhanceWithProfile(rp, profile, id);
		rp.getMetadata().put("type", context);
		return rp;
	}

	public void addEntries(ArrayList<Section> sects) {
		try {
			detections.put(sects);
		} catch (NotFoundException | ConversionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setTestCase(String testPlan,String testGroup,String testCase, String testStep){
		this.metadata.put("testCase.plan", testPlan);
		this.metadata.put("testCase.group", testGroup);
		this.metadata.put("testCase.case", testCase);
		this.metadata.put("testCase.step", testStep);
	}

	public static EnhancedReport from(String format, String content)
			throws Exception {
		initC();
		for (Converter c : converters) {
			if (c.getFormat().equals(format)) {
				return c.unserialize(content);
			}
		}
		throw new Exception("Unsupported Format");
	}

	public void put(ArrayList<Section> dets) {
		try {
			Section cts = new Section("counts");			
			for (Section s : dets) {
				if (this.getMetadata().accessComplex("counts", cts)) {
					StringRef r = new StringRef();
					if(cts.accessPrimitive(s.getString("classification").toLowerCase(), r)){
						cts.put(s.getString("classification").toLowerCase(), Integer.parseInt(r.value) + 1);
					}
					else
						cts.put(s.getString("classification"), "1");
				}
			}
			
			this.getMetadata().put(cts);
			this.detections.put(dets);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initCounts(){
		Section s = new Section("counts");
		s.put("informational",0);
		s.put("error", 0);
		s.put("affirmative",0);
		s.put("alert", 0);
		s.put("warning", 0);
		this.getMetadata().put(s);
	}

	public Object to(String format) throws Exception {
		initC();
		for (Converter c : converters) {
			if (c.getFormat().equals(format)) {
				return c.convert(this);
			}
		}
		throw new Exception("Unsupported Format");
	}

	public String render(String tmp, JSONObject params) throws Exception {
		initC();
		for (Render c : renders) {
			if (c.getName().equals(tmp)) {
				return c.render(this.to("xml").toString(), params);
			}
		}
		throw new Exception("Unsupported Template");
	}

	public Section getMetadata() {
		return metadata;
	}

	public void setMetadata(Section metadata) {
		this.metadata = metadata;
	}

	public Section getDetectionSection() {
		return detections.asSection();
	}

	public Detections getDetections() {
		return detections;
	}

	public void setDetections(Detections detections) {
		this.detections = detections;
	}

	public void setDetectionSection(Section sect) {
		this.detections.fromSection(sect);
	}

	@Override
	public String getName() {
		return "report";
	}

	@Override
	public String getString(String key) throws NotFoundException,
			ConversionException {
		if (key.startsWith("metaData."))
			return metadata.getString(key.replace("metaData.", ""));
		throw new NotFoundException();
	}

	@Override
	public Integer getInt(String key) throws NotFoundException,
			ConversionException {
		if (key.startsWith("metaData."))
			return metadata.getInt(key.replace("metaData.", ""));
		throw new NotFoundException();
	}

	@Override
	public AccessibleObject getObject(String key) throws NotFoundException,
			ConversionException {
		if (key.startsWith("metaData."))
			return metadata.getObject(key.replace("metaData.", ""));
		else if (key.startsWith("detections."))
			return detections.getObject(key.replace("detections.", ""));
		return null;
	}

	@Override
	public AccessibleArray getArray(String key) throws NotFoundException,
			ConversionException {
		if (key.startsWith("metaData."))
			return metadata.getArray(key.replace("metaData.", ""));
		else if (key.startsWith("detections."))
			return detections.getArray(key.replace("detections.", ""));
		return null;
	}

	@Override
	public List<String> keys() {
		ArrayList<String> arL = new ArrayList<String>();
		arL.add("metaData");
		arL.add("detections");
		return arL;
	}

	@Override
	public boolean accessPrimitive(String key, StringRef container) {
		try {
			container.value = this.getString(key);
			return true;
		} catch (ConversionException e) {
			try {
				container.value = this.getInt(key) + "";
				return true;
			} catch (NotFoundException | ConversionException e1) {
				return false;
			}
		} catch (NotFoundException e) {
			return false;
		}
	}

	@Override
	public boolean accessComplex(String key, AccessibleObject container) {
		try {
			Section s = (Section) this.getObject(key);
			((Section) container).setRaw(s.raw());
			return true;
		} catch (NotFoundException | ConversionException e) {
			return false;
		}
	}

	@Override
	public boolean accessComplex(String key, AccessibleArray container) {
		try {
			Collection c = (Collection) this.getArray(key);
			((Collection) container).setRaw(c.raw());
			return true;
		} catch (NotFoundException | ConversionException e) {
			return false;
		}
	}

	@Override
	public boolean isEmpty() {
		return this.metadata.isEmpty() && this.detections.isEmpty();
	}
}
