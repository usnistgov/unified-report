package gov.nist.healthcare.unified.proxy;


import java.util.List;
import java.util.ArrayList;

import gov.nist.healthcare.unified.model.EnhancerH;
import validator.Util;
import validator.Validation;
import gov.nist.healthcare.unified.enums.Context;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.model.Section;
import gov.nist.validation.report.Report;

import javax.xml.validation.Schema;

public class XMLValidationProxy {

    private Section service;

    public XMLValidationProxy(String serName, String serProvider) {
        service = new Section("service");
        service.put("name", serName);
        service.put("provider", serProvider);
        service.put("validationVersion", buildinfo.Info.version());
    }

    public EnhancedReport validate(String msg, Schema schema, List<String> schematrons, String skeleton, String phase, Context context) {
        try {
            //String content = Util.streamAsString(msg);
            Report validationReport = Validation.validate(msg, schema, schematrons, skeleton, phase);
            String ctx = "";
            if (context == Context.Free) ctx = "Context-Free";
            else ctx = "Context-Based";
            EnhancedReport enhancedReport = new EnhancedReport();
            ArrayList<Section> mds = new ArrayList<Section>();
            mds.add(service);
            EnhancerH.enhanceHeader(enhancedReport, mds, msg);
            EnhancerH.enhanceWithReport(enhancedReport, validationReport);
            return enhancedReport;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
