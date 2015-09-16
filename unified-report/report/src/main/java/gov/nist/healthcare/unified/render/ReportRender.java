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

public class ReportRender implements Render {

	@Override
	public String getName() {
		return "iz-report";
	}

	@Override
	public String render(String xml, JSONObject params) throws RenderException {
		InputStream is = new ByteArrayInputStream(xml.getBytes());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer;

		try {
			transformer = tFactory.newTransformer(new StreamSource(this
					.getClass().getResourceAsStream("/stylesheets/"+this.getName()+".xsl")));
			transformer.transform(new StreamSource(is), new StreamResult(os));
			String result = os.toString();
			if(params != null){
				for(Object k : params.keySet()){
					result = result.replaceAll(k.toString(), params.getString(k.toString()));
				}
			}
			return result;
		} catch (TransformerException e) {
			throw new RenderException();
		}

	}

}
