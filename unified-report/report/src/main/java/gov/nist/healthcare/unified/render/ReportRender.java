package gov.nist.healthcare.unified.render;

import org.json.JSONObject;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import gov.nist.healthcare.unified.exceptions.RenderException;
import gov.nist.healthcare.unified.model.EnhancedReport;

public class ReportRender implements Render {

	@Override
	public String getName() {
		return "iz-report";
	}

	@Override
	public String render(EnhancedReport r, JSONObject params) throws RenderException {
		try {
			String xml = r.to("xml").toString();
			InputStream is = new ByteArrayInputStream(xml.getBytes());
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer;
			
			transformer = tFactory.newTransformer(new StreamSource(this
					.getClass().getResourceAsStream("/stylesheets/"+this.getName()+".xsl")));
			if(params != null && params.has("excluded")){
				transformer.setParameter("excluded", params.getString("excluded"));
			}
			transformer.transform(new StreamSource(is), new StreamResult(os));
			String result = os.toString();
			return result;
		} catch (Exception e) {
			throw new RenderException();
		}

	}

}
