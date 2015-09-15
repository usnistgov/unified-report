package gov.nist.healthcare.unified.render;

import gov.nist.healthcare.unified.exceptions.RenderException;

import org.json.JSONObject;

public interface Render {
	String getName();
	String render(String xml,JSONObject params) throws RenderException;
}
