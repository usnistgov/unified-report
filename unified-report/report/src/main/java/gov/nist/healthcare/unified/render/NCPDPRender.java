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

import org.json.JSONObject;

public class NCPDPRender implements Render {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ncpdp-report";
	}

	@Override
	public String render(EnhancedReport r, JSONObject params) throws RenderException {

		try {
			r.showSeparators(false);
			String xml = r.to("xml").toString();
			InputStream is = new ByteArrayInputStream(xml.getBytes());
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer;
			
			transformer = tFactory.newTransformer(new StreamSource(this
					.getClass().getResourceAsStream("/stylesheets/"+this.getName()+".xsl")));
			
			transformer.setParameter("msgWithSeparators", EnhancedReport.messageF(r.getOriginalMsg(), true));
			
			if(params != null){
				
				if(params.has("excluded")){
					transformer.setParameter("excluded", params.getString("excluded"));
				}
				
				if(params.has("msgWithSeparators")){
					transformer.setParameter("msgWithSeparators", params.getString("msgWithSeparators"));
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
