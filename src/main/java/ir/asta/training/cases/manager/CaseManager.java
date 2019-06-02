package ir.asta.training.cases.manager;

import ir.asta.training.auth.entities.UserEntity;
import ir.asta.training.auth.dao.AuthDao;
import ir.asta.training.auth.fixed.Role;
import ir.asta.training.cases.dao.CaseDao;
import ir.asta.training.auth.entities.CaseEntity;
import ir.asta.wise.core.datamanagement.ActionResult;
import ir.asta.wise.core.enums.Importance;
import ir.asta.wise.core.enums.Status;
import ir.asta.wise.core.response.CaseResponse;
import ir.asta.wise.core.response.JalaliCalendar;
import ir.asta.wise.core.response.UserResponse;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named("caseManager")
public class CaseManager {

    @Inject
    private AuthDao authDao;

    @Inject
    private CaseDao caseDao;


    @Transactional
    public ActionResult<String> setCase(
            String title,
            String to,
            String importance,
            String body,
            String token,
            Attachment attachment) throws IOException {

        ActionResult<String> result = new ActionResult<>();
        String[] massages = validateSetCase(title,
                to,
                importance,
                body);
        if (massages.length <= 0) {
            long toId = Long.valueOf(to);
            UserEntity toEntity = authDao.containsUserAndValid(toId);
            if (toEntity != null) {
                Importance imp = null;
                switch (importance) {
                    case "0":
                        imp = Importance.LOW;
                        break;
                    case "1":
                        imp = Importance.MEDIUM;
                        break;
                    case "2":
                        imp = Importance.HIGH;
                }
                UserResponse authenticate = authDao.authenticate(token);
                if (authenticate != null) {
                    UserEntity from = authDao.getByToken(token);
                    Date now = new Date();
                    String file = null;
                    if (attachment != null) {
                        String filename = "webapps/ticketing/" + attachment.getContentDisposition().getParameter("filename");
                        Path path;
                        do {
                            int index = filename.lastIndexOf(".");
                            String fName = filename.substring(0, index);
                            String extension = filename.substring(index + 1);
                            filename = fName + "1." + extension;
                            path = Paths.get(filename);
                        } while (Files.exists(path));
                        file = filename.substring("webapps".length());
                        InputStream in = attachment.getObject(InputStream.class);
                        Files.copy(in, path);
                    }
                    CaseEntity caseEntity = new CaseEntity(title, body, now, now, from, toEntity, imp, Status.OPEN, file);
                    caseDao.setCase(caseEntity);
                    result.setSuccess(true);
                    result.setMessage("با موفقیت ارسال شد");
                    result.setData(null);
                } else {
                    result.setMessage("شما اجازه دسترسی به این مورد را ندارید");
                }
            } else {
                result.setMessage("گیرنده نامعتبر است");
            }

        } else {
            result.setMessage(String.join("\n", massages));
        }
        return result;
    }

    public ActionResult<List<CaseResponse>> getMyCase(String token) {
        UserResponse authenticate = authDao.authenticate(token);
        ActionResult<List<CaseResponse>> result = new ActionResult<>();
        if (authenticate != null) {
            List<CaseEntity> cases = caseDao.getMyCases(token);
            result.setData(convertCaseEntitiesToResponse(cases));
            result.setSuccess(true);
            result.setMessage(null);
        } else {
            result.setMessage("شما لاگین نیستید");
        }
        return result;
    }

    private List<CaseResponse> convertCaseEntitiesToResponse(List<CaseEntity> list) {
        List<CaseResponse> responses = new ArrayList<>();
        for (CaseEntity entity : list) {
            String createdDate = convertDateToString(entity.getCreatedDate());
            String lastUpdate = convertDateToString(entity.getLastUpdate());
            CaseResponse response = new CaseResponse();
            response.setBody(entity.getBody());
            response.setCreatedDate(createdDate);
            response.setFile(entity.getFile());
            response.setFrom(authDao.authenticate(entity.from.getMongoId()).getName());
            response.setImportance(entity.getImportance());
            response.setLastUpdate(lastUpdate);
            response.setStatus(entity.getStatus());
            response.setTitle(entity.getTitle());
            response.setTo(authDao.authenticate(entity.to.getMongoId()).getName());
            responses.add(response);
        }
        return responses;
    }

    private String convertDateToString(Date dt) {
        JalaliCalendar jalaliCalendar = new JalaliCalendar(dt);
        String date = "";
        date += jalaliCalendar.getYear();
        date += "/";
        String month = String.valueOf(jalaliCalendar.getMonth());
        month = month.length() == 1 ? "0" + month : month;
        date += month;
        date += "/";
        String day = String.valueOf(jalaliCalendar.getDay());
        day = day.length() == 1 ? "0" + day : day;
        date += day;
        return date;
    }

    public ActionResult<List<CaseResponse>> getCaseToMe(String token) {
        UserResponse authenticate = authDao.authenticate(token);
        ActionResult<List<CaseResponse>> result = new ActionResult<>();
        if (authenticate != null && !authenticate.getRole().equals(Role.student)) {
            List<CaseEntity> cases = caseDao.getCaseToMe(token);
            result.setData(convertCaseEntitiesToResponse(cases));
            result.setSuccess(true);
            result.setMessage(null);
        } else {
            result.setMessage("شما اجازه دسترسی به این مورد را ندارید");
        }
        return result;
    }

    private String[] validateSetCase(String title,
                                     String to,
                                     String importance,
                                     String body) {
        List<String> massages = new ArrayList<>();
        if (title == null || title.length() == 0) {
            massages.add("عنوان را تعیین کنید");
        }
        if (to == null || to.length() == 0 || !to.matches("^[0-9]{1,11}$")) {
            massages.add("گیرنده را تعیین کنید");
        }
        if (importance == null || importance.length() == 0 || !importance.matches("^[0-2]$")) {
            massages.add("میزان اهمیت را تعیین کنید");
        }
        if (body == null || body.length() == 0) {
            massages.add("فیلد متن خالی است");
        }
        String[] ans = new String[massages.size()];
        for (int i = 0; i < massages.size(); i++) {
            ans[i] = massages.get(i);
        }
        return ans;
    }

    public ActionResult<List<CaseResponse>> getAllCases(String token, String from, String to) {
        UserResponse authenticate = authDao.authenticate(token);
        ActionResult<List<CaseResponse>> result = new ActionResult<>();
        if (authenticate != null && authenticate.getRole().equals(Role.manager)) {
            result.setSuccess(true);
            result.setData(convertCaseEntitiesToResponse(caseDao.getAllCases(from, to)));
        } else {
            result.setMessage("شما اجازه دسترسی به این مورد را ندارید");
        }
        return result;

    }
}
