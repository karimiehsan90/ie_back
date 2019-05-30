package ir.asta.training.cases.manager;

import ir.asta.training.auth.entities.UserEntity;
import ir.asta.training.auth.dao.AuthDao;
import ir.asta.training.cases.dao.CaseDao;
import ir.asta.training.cases.entities.CaseEntity;
import ir.asta.wise.core.datamanagement.ActionResult;
import ir.asta.wise.core.enums.Importance;
import ir.asta.wise.core.enums.Status;
import ir.asta.wise.core.response.CaseResponse;
import ir.asta.wise.core.response.UserResponse;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
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
            String token){

        ActionResult<String> result = new ActionResult<>();
        String[] massages = validateSetCase(title,
                to,
                importance,
                body);
        if(massages.length <= 0){
            long toId = Long.valueOf(to);
            UserEntity toEntity = authDao.containsUserAndValid(toId);
            if(toEntity != null){
                Importance imp = null;
                switch (importance){
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
                    CaseEntity caseEntity = new CaseEntity(title, body, now, now, from, toEntity, imp, Status.OPEN, null);
                    caseDao.setCase(caseEntity);
                    result.setSuccess(true);
                    result.setMessage("با موفقیت ارسال شد");
                    result.setData(null);
                }
                else {
                    result.setMessage("شما اجازه دسترسی به این مورد را ندارید");
                }
            }else {
                result.setMessage("گیرنده نامعتبر است");
            }

        }else {
            result.setMessage(String.join("\n", massages));
        }
        return result;
    }

    public ActionResult<List<CaseResponse>> getMyCase(String token){
        List<CaseResponse> cases = caseDao.getMyCases(token);
        ActionResult result = new ActionResult();

        result.setData(cases);
        result.setSuccess(true);
        result.setMessage(null);

        return result;
    }

    private String[] validateSetCase(String title,
                                     String to,
                                     String importance,
                                     String body){
        List<String> massages = new ArrayList<>();
        if(title == null || title.length() == 0){
            massages.add("عنوان را تعیین کنید");
        }
        if(to == null || to.length() == 0 || !to.matches("^[0-9]{1,11}$")){
            massages.add("گیرنده را تعیین کنید");
        }
        if(importance == null || importance.length() == 0 || !importance.matches("^[0-2]$")){
            massages.add("میزان اهمیت را تعیین کنید");
        }
        if(body == null || body.length() == 0){
            massages.add("فیلد متن خالی است");
        }
        String[] ans = new String[massages.size()];
        for (int i = 0; i <massages.size() ; i++) {
            ans[i] = massages.get(i);
        }
        return ans;
    }
}
