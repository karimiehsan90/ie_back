package ir.asta.training.auth.manager;

import ir.asta.training.auth.dao.AuthDao;
import ir.asta.training.auth.dao.UserDao;
import ir.asta.training.auth.entities.UserEntity;
import ir.asta.wise.core.datamanagement.ActionResult;
import ir.asta.wise.core.response.UserResponse;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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
}
