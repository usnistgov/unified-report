package gov.nist.healthcare.unified.converters;

import gov.nist.healthcare.unified.exceptions.ConversionException;
import gov.nist.healthcare.unified.exceptions.NotFoundException;
import gov.nist.healthcare.unified.interfaces.Converter;
import gov.nist.healthcare.unified.model.Classification;
import gov.nist.healthcare.unified.model.Collection;
import gov.nist.healthcare.unified.model.Detections;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.model.Section;
import gov.nist.healthcare.unified.model.StringRef;
import gov.nist.healthcare.unified.model.impl.CategoryGroup;
import gov.nist.healthcare.unified.model.impl.ClassificationGroup;
import gov.nist.healthcare.unified.model.impl.Entry;
import gov.nist.healthcare.unified.model.impl.Metadata;
import gov.nist.healthcare.unified.model.impl.ModelImpl;
import gov.nist.healthcare.unified.model.impl.Reference;
import gov.nist.healthcare.unified.model.impl.Trace;
import gov.nist.healthcare.unified.model.impl.ValueSet;

public class ModelConverter implements Converter {

	@Override
	public Object convert(EnhancedReport s) throws ConversionException {
		try {
			ModelImpl mi = new ModelImpl();
			Metadata md = new Metadata();
			StringRef ref = new StringRef();
			if (s.accessPrimitive("metaData.standardType", ref))
				md.setStandardType(ref.value);
			if (s.accessPrimitive("metaData.validationType", ref))
				md.setValidationType(ref.value);
			if (s.accessPrimitive("metaData.service.name", ref))
				md.setServiceName(ref.value);
			if (s.accessPrimitive("metaData.service.version", ref))
				md.setServiceVersion(ref.value);
			if (s.accessPrimitive("metaData.service.provider", ref))
				md.setServiceProvider(ref.value);
			if (s.accessPrimitive("metaData.profile.orgName", ref))
				md.setProfileOrgName(ref.value);
			if (s.accessPrimitive("metaData.profile.version", ref))
				md.setProfileVersion(ref.value);
			if (s.accessPrimitive("metaData.profile.name", ref))
				md.setProfileName(ref.value);
			if (s.accessPrimitive("metaData.profile.type", ref))
				md.setProfileType(ref.value);
			if (s.accessPrimitive("metaData.profile.standard", ref))
				md.setProfileStandard(ref.value);
			if (s.accessPrimitive("metaData.counts.affirmative", ref))
				md.setAffirmativesCount(Int(ref.value));
			if (s.accessPrimitive("metaData.counts.alert", ref))
				md.setAlertsCount(Int(ref.value));
			if (s.accessPrimitive("metaData.counts.error", ref))
				md.setErrorsCount(Int(ref.value));
			if (s.accessPrimitive("metaData.counts.warning", ref))
				md.setWarningCount(Int(ref.value));
			if (s.accessPrimitive("metaData.counts.informational", ref))
				md.setInfoCount(Int(ref.value));
			if (s.accessPrimitive("metaData.message.content", ref))
				md.setMessageContent(ref.value);
			if (s.accessPrimitive("metaData.message.encoding", ref))
				md.setMessageEncoding(ref.value);

			Detections d = s.getDetections();
			for (Classification c : d.cl) {
				ClassificationGroup g = new ClassificationGroup();
				g.setClassification(c.name);
				for (String k : c.keys()) {
					CategoryGroup cg = new CategoryGroup();
					Collection co = c.getArray(k);
					cg.setCategory(k);
					for(int i = 0; i < co.size(); i++){
						cg.getEntries().add(transform(co.getObject(i)));
					}
					g.getCategories().add(cg);
				}
				mi.getClassifications().add(g);
			}
			
			mi.setMetadata(md);
			return mi;
		} catch (NotFoundException e) {
			return null;
		}
	}

	private Entry transform(Section s) throws NotFoundException,
			ConversionException {
		StringRef ref = new StringRef();
		Collection cursor1 = new Collection("");
		Collection cursor2 = new Collection("");
		Section cursor_1 = new Section("");
		Section cursor_2 = new Section("");
		Entry e = new Entry();
		if (s.accessPrimitive("line", ref))
			e.setLine(Int(ref.value));
		if (s.accessPrimitive("column", ref))
			e.setColumn(Int(ref.value));
		if (s.accessPrimitive("path", ref))
			e.setPath(ref.value);
		if (s.accessPrimitive("description", ref))
			e.setDescription(ref.value);
		if (s.accessPrimitive("classification", ref))
			e.setClassification(ref.value);
		if (s.accessPrimitive("category", ref))
			e.setCategory(ref.value);
		if (s.accessComplex("stackTrace", cursor1)) {
			for (int i = 0; i < cursor1.size(); i++) {
				Trace t = new Trace();
				Section ss = cursor1.getObject(i);
				if (ss.accessPrimitive("assertion", ref))
					t.setAssertion(ref.value);
				if (ss.accessComplex("reasons", cursor2)) {
					for (int j = 0; j < cursor2.size(); j++) {
						t.getReasons().add(cursor2.getString(j));
					}
				}
				e.getStacktrace().add(t);
			}
		}
		if (s.accessComplex("metaData", cursor_2)) {
			if (cursor_1.accessComplex("valueSet", cursor_2)) {
				ValueSet vs = new ValueSet();
				if (cursor_2.accessPrimitive("id", ref))
					vs.setId(ref.toString());
				if (cursor_2.accessPrimitive("stability", ref))
					vs.setStability(ref.toString());
				if (cursor_2.accessPrimitive("extensibility", ref))
					vs.setExtensibility(ref.toString());
				if (cursor_2.accessPrimitive("bindingStrength", ref))
					vs.setBindingStrength(ref.toString());
				if (cursor_2.accessPrimitive("bindingLocation", ref))
					vs.setBindingLocation(ref.toString());
				vs.setType("ValueSet");
				e.setMetadata(vs);
			} else if (cursor_1.accessComplex("reference", cursor_2)) {
				Reference refs = new Reference();
				if (cursor_2.accessPrimitive("chapter", ref))
					refs.setChapter(ref.toString());
				if (cursor_2.accessPrimitive("section", ref))
					refs.setSection(ref.toString());
				if (cursor_2.accessPrimitive("page", ref))
					refs.setPage(ref.toString());
				if (cursor_2.accessPrimitive("url", ref))
					refs.setUrl(ref.toString());
				if (cursor_2.accessPrimitive("source", ref))
					refs.setSource(ref.toString());
				if (cursor_2.accessPrimitive("generatedBy", ref))
					refs.setGeneratedBy(ref.toString());
				if (cursor_2.accessPrimitive("referencePath", ref))
					refs.setReferencePath(ref.toString());
				if (cursor_2.accessPrimitive("testDataCategorization", ref))
					refs.setTestDataCategorization(ref.toString());

				refs.setType("Reference");
				e.setMetadata(refs);
			}
		}
		return e;
	}

	@Override
	public EnhancedReport unserialize(String s) throws ConversionException {
		throw new ConversionException("Operation not supported");
	}

	@Override
	public String getFormat() {
		return "model";
	}

	private int Int(String x) {
		return Integer.parseInt(x);
	}

	private String Int(Integer x) {
		if (x == null)
			return null;
		return x + "";
	}

}
