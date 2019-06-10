package ir.asta.training.cases.manager;

import ir.asta.training.auth.dao.AuthDao;
import ir.asta.training.auth.entities.ActionEntity;
import ir.asta.training.auth.entities.CaseEntity;
import ir.asta.training.auth.entities.UserEntity;
import ir.asta.training.auth.fixed.Role;
import ir.asta.training.cases.dao.ActionDao;
import ir.asta.training.cases.dao.CaseDao;
import ir.asta.wise.core.datamanagement.ActionResult;
import ir.asta.wise.core.enums.Status;
import ir.asta.wise.core.response.UserResponse;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Date;

@Named("actionManager")
public class ActionManager {
    @Inject
    private AuthDao authDao;
    @Inject
    private CaseDao caseDao;
    @Inject
    private ActionDao actionDao;
    @Inject
    private FileManager fileManager;
    @Inject
    private NotificationManager notificationManager;

    @Transactional
    public ActionResult<Boolean> setAction(String token, long caseId, String content, int status, Attachment attachment, Long to) throws IOException {
        UserResponse authenticate = authDao.authenticate(token);
        CaseEntity caseEntity = caseDao.getByFromOrTo(caseId, token);
        ActionResult<Boolean> result = new ActionResult<>();
        if (authenticate != null && caseEntity != null && caseEntity.getStatus() != Status.CLOSE){
            UserEntity userEntity = authDao.getByToken(token);
            UserEntity toEntity = caseEntity.to;
            if (to != null){
                UserEntity byId = authDao.getById(to);
                if (byId != null){
                    UserResponse response = authDao.authenticate(byId.getMongoId());
                    if (response != null && !response.getRole().equals(Role.student)) {
                        toEntity = byId;
                    }
                }
            }
            Status validate = validate(status);
            if (validate != null){
                Date date = new Date();
                ActionEntity entity = new ActionEntity();
                entity.setContent(content);
                entity.setDate(date);
                entity.setStatus(validate);
                entity.caseEntity = caseEntity;
                entity.from = userEntity;
                entity.setFile(fileManager.saveFile(attachment));
                actionDao.save(entity);
                caseEntity.to = toEntity;
                caseEntity.setStatus(validate);
                caseEntity.setLastUpdate(date);
                caseDao.update(caseEntity);
                result.setSuccess(true);
                if (caseEntity.from != null){
                    String fromToken = caseEntity.from.getMongoId();
                    UserResponse fromResponse = authDao.authenticate(fromToken);
                    String phone = authDao.getPhone(fromToken);
                    if (fromResponse != null){
                        String email = fromResponse.getEmail();
                        new Thread(() -> {
                            notificationManager.sendEmail("تغییر در مورد ارسالی شما", "در مورد ارسالی شما تغییر ایجاد شد! لطفا بررسی فرمایید", email);
                            try {
                                notificationManager.sendSMS("There is an update in your case! Please check it.", phone);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                }
            }
            else {
                result.setMessage("وضعیت یافت نشد");
            }
        }
        else {
            result.setMessage("شما اجازه دسترسی به این مورد را ندارید");
        }
        return result;
    }

    private Status validate(int status) {
        if (status >= 0 && status < Status.values().length){
            return Status.values()[status];
        }
        return null;
    }
}
