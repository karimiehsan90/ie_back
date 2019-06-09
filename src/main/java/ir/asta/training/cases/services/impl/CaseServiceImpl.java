package ir.asta.training.cases.services.impl;

import ir.asta.training.cases.dao.CaseDao;
import ir.asta.training.cases.manager.CaseManager;
import ir.asta.training.cases.services.CaseService;
import ir.asta.wise.core.datamanagement.ActionResult;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import ir.asta.wise.core.response.CaseResponse;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Named("caseService")
public class CaseServiceImpl implements CaseService {

    @Inject
    private CaseManager manager;

    @Inject
    private CaseDao dao;

    @Override
    public ActionResult<List<CaseResponse>> getMyCase(String token)
            throws UnsupportedEncodingException, NoSuchAlgorithmException{
        return manager.getMyCase(token);
    }

    @Override
    public ActionResult<List<CaseResponse>> getCaseToMe(String token)
            throws UnsupportedEncodingException, NoSuchAlgorithmException{
        return manager.getCaseToMe(token);
    }

    @Override
    public ActionResult<List<CaseResponse>> getAllCases(String token, String from, String to) {
        return manager.getAllCases(token, from, to);
    }

    @Override
    public ActionResult<List<CaseResponse>> getAllVotedCasesBetween(String token, String fromDate, String toDate) {
        return manager.getVotedAllCases(token, fromDate, toDate);
    }

    @Override
    public ActionResult<String> setCase(String title,
                                        String to,
                                        String importance,
                                        String body,
                                        String token,
                                        Attachment attachment)
            throws IOException, NoSuchAlgorithmException {
        return manager.setCase(title,to,importance,body,token, attachment);
    }
    @Override
    public ActionResult<Boolean>setRate(String token,long caseID,boolean h)  throws IOException, NoSuchAlgorithmException{
        return manager.setRate(token,caseID,h);
    }
}
