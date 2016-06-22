package gov.nist.healthcare.unified.render;

import gov.nist.healthcare.unified.exceptions.RenderException;
import gov.nist.healthcare.unified.model.EnhancedReport;

import org.json.JSONObject;

public interface Render {
	String getName();
	String render(EnhancedReport r,JSONObject params) throws RenderException;
}
