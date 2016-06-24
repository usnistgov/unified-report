package gov.nist.healthcare.unified.render;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import gov.nist.healthcare.unified.exceptions.RenderException;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.model.StringRef;

import org.json.JSONObject;

public class NCPDPRender implements Render {

	@Override
	public String getName() {
		return "report";
	}

	@Override
	public String render(EnhancedReport r, JSONObject params) throws RenderException {

		try {
			StringRef ref = new StringRef();
			
			if(EnhancedReport.nonPrintables(r.getMessage(), false, ref))
				r.setMessage(ref.value);
			
			String xml = r.to("xml").toString();
			InputStream is = new ByteArrayInputStream(xml.getBytes());
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer;
			
			transformer = tFactory.newTransformer(new StreamSource(this
					.getClass().getResourceAsStream("/stylesheets/"+this.getName()+".xsl")));
	
			
			if(params != null){
				
				if(params.has("excluded")){
					transformer.setParameter("excluded", params.getString("excluded"));
				}
				
			}
			transformer.transform(new StreamSource(is), new StreamResult(os));
			String result = os.toString();

			return result;
		} catch (Exception e) {
			throw new RenderException();
		}

	}

}
