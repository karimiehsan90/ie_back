package ir.asta.training.cases.services.impl;

import ir.asta.training.cases.dao.CaseDao;
import ir.asta.training.cases.entities.CaseEntity;
import ir.asta.training.cases.manager.CaseManager;
import ir.asta.training.cases.services.CaseService;
import ir.asta.wise.core.datamanagement.ActionResult;
import ir.asta.wise.core.response.CaseResponse;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.FormParam;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

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
    public ActionResult<String> setCase(String title,
                                        String to,
                                        String importance,
                                        String body,
                                        String token)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return manager.setCase(title,to,importance,body,token);
    }
}
