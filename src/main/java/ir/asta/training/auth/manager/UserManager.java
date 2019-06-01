package ir.asta.training.auth.manager;

import com.mongodb.client.FindIterable;
import ir.asta.training.auth.dao.AuthDao;
import ir.asta.training.auth.dao.UserDao;
import ir.asta.training.auth.fixed.Role;
import ir.asta.training.auth.fixed.UserMongo;
import ir.asta.wise.core.datamanagement.ActionResult;
import ir.asta.wise.core.response.ManageUserResponse;
import ir.asta.wise.core.response.UserAcceptResponse;
import ir.asta.wise.core.response.UserResponse;
import ir.asta.wise.core.response.UserResponseManager;
import org.bson.Document;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Named("userManager")
public class UserManager {
    @Inject
    private UserDao dao ;
    @Inject
    private AuthManager authManager;
    @Inject
    private AuthDao authDao;

    public ActionResult<UserResponse> edit(String name , String password , String email , String ppass , String phone , String token) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserResponse userResponse = authDao.authenticate(token);
        ActionResult<UserResponse> result = new ActionResult<>();
        result.setData(userResponse);
        result.setSuccess(false);
        if (userResponse != null){
            String hashpass = authManager.hashPassword(ppass);
            if (password.length()<6){
                result.setMessage("پسورد قبلی را اشتباه وارد کردید");
            }else if (!hashpass.equals(dao.getPass(token))){
                result.setMessage("پسورد باید حداقل 6 رقم باشد");
            }else if (name == null){
                result.setMessage("اسم خالی نباشد");
            }else if (email == null || !email.matches("^[a-z]([a-z0-9]|_[a-z0-9]|.[a-z0-9])+@[a-z0-9_]+([.][a-z0-9]+)+$")){
                result.setMessage("ایمیل خالی یا نامعتبر است");
            }
            else {
                dao.updatePro(name,authManager.hashPassword(password),email,phone,token);
                result.setMessage("تغییرات با موفقیت اعمال شد");
                result.setSuccess(true);
            }
        }
        return result;
    }

    public ActionResult<ManageUserResponse> getUsers(String token) {
        ActionResult<ManageUserResponse> result = new ActionResult<>();
        UserResponse userResponse = authDao.authenticate(token);
        if (userResponse != null && userResponse.getRole().equals(Role.manager)){
            ManageUserResponse response = new ManageUserResponse();
            List<UserResponseManager> responseManagers = new ArrayList<>();
            FindIterable<Document> accepteds = dao.getAccepteds();
            for (Document d:accepteds) {
                String name = d.getString(UserMongo.firstName);
                String email = d.getString(UserMongo.email);
                String role = d.getString(UserMongo.role);
                role = role.equals(Role.manager)
                        ? "مدیر" : role.equals(Role.teacher)
                        ? "استاد" : role.equals(Role.student)
                        ? "دانشجو" : null;
                String id = d.getObjectId(UserMongo.objectId).toHexString();
                responseManagers.add(new UserResponseManager(name, email, role, id));
            }
            response.setManages(responseManagers);
            List<UserAcceptResponse> acceptResponses = new ArrayList<>();
            FindIterable<Document> notAccepteds = dao.getNotAccepteds();
            for (Document d:notAccepteds) {
                String name = d.getString(UserMongo.firstName);
                String email = d.getString(UserMongo.email);
                String id = d.getObjectId(UserMongo.objectId).toHexString();
                acceptResponses.add(new UserAcceptResponse(name, email, id));
            }
            response.setAccepts(acceptResponses);
            result.setData(response);
            result.setSuccess(true);
        }
        else {
            result.setMessage("شما اجازه دسترسی به این مورد را ندارید");
        }
        return result;
    }
}
