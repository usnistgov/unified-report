package gov.nist.healthcare.validation.plugin.vsproxy;

import hl7.v2.validation.vs.Code;
import hl7.v2.validation.vs.ValueSet;
import hl7.v2.validation.vs.factory.ValueSetFacade;
import hl7.v2.validation.vs.factory.impl.java.ValueSetProxy;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SSAdministrativeDiagnosisCDCICD10CMValueSetProxy extends ValueSetProxy {

	public SSAdministrativeDiagnosisCDCICD10CMValueSetProxy(ValueSetFacade valueSetFacade) {
		super(valueSetFacade);
	}

	@Override
	public ValueSet getValueSet() {
		return valueSet().getValueSet();
	}

	@Override
	public List<Code> getCodeMatches(String value) throws Exception {
		return valueSet().getCodeMatches(normalizeValue(value));
	}

	@Override
	public boolean contains(String value) throws Exception {
		return valueSet().contains(normalizeValue(value));
	}

	@Override
	public List<Code> getCodeList() throws Exception {
		List<Code> codes = valueSet().getCodeList();
		List<Code> codesIncludingDotted = new ArrayList<>(codes);
		for (Code code : codes) {
			String dotted = code.value().substring(0, 3) + "." + code.value().substring(3);
			codesIncludingDotted.add(
					code.copy(
							dotted,
							code.description(),
							code.usage(),
							code.codeSys(),
							code.pattern()
					)
			);
		}
		return codesIncludingDotted;
	}

	private String normalizeValue(String value) {
		int dots = StringUtils.countMatches(value, ".");
		if(dots == 1) {
			int index = StringUtils.lastIndexOf(value, ".");
			if(index == 3) {
				return value.replace(".", "");
			}
		}
		return value;
	}
}
