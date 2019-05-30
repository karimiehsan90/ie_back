package ir.asta.training.cases.services.impl;

import ir.asta.training.cases.dao.CaseDao;
import ir.asta.training.cases.entities.CaseEntity;
import ir.asta.training.cases.manager.CaseManager;
import ir.asta.training.cases.services.CaseService;
import ir.asta.wise.core.datamanagement.ActionResult;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
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
    public ActionResult<List<CaseEntity>> cases(String token) {
        List<CaseEntity> entities = dao.getMyCases(token);
        return new ActionResult<>(false, null, entities);
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
}
