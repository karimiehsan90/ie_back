package ir.asta.training.cases.services.impl;

import ir.asta.training.cases.manager.ActionManager;
import ir.asta.training.cases.services.ActionService;
import ir.asta.wise.core.datamanagement.ActionResult;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import javax.inject.Inject;
import javax.inject.Named;

@Named("actionService")
public class ActionServiceImpl implements ActionService {

    @Inject
    private ActionManager manager;

    @Override
    public ActionResult<Boolean> setAction(String token, long caseId, String content, int status, Attachment attachment, Long to) {
        return manager.setAction(token, caseId, content, status, attachment, to);
    }
}
