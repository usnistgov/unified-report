package gov.nist.healthcare.unified.proxy;


import java.util.ArrayList;

import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.model.EnhancerH;
import gov.nist.healthcare.unified.model.Section;
import gov.nist.validation.report.Report;
import validator.Validation;

public class WCTPValidationProxy {

    private Section service;

    public WCTPValidationProxy(String serName, String serProvider) {
        service = new Section("service");
        service.put("name", serName);
        service.put("provider", serProvider);
        service.put("validationVersion", buildinfo.Info.version());
    }

    public EnhancedReport validate(String msg) {
        try {
            //String content = Util.streamAsString(msg);
            Report validationReport = Validation.validateWCTP(msg);
//            String ctx = "";
//            if (context == Context.Free) ctx = "Context-Free";
//            else ctx = "Context-Based";
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
