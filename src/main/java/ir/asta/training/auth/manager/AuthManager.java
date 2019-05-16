package ir.asta.training.auth.manager;

import ir.asta.training.auth.dao.AuthDao;
import ir.asta.training.auth.fixed.Role;
import ir.asta.wise.core.datamanagement.ActionResult;
import ir.asta.wise.core.response.CaseResponse;
import ir.asta.wise.core.response.UserResponse;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Named("authManager")
public class AuthManager {
    @Inject
    private AuthDao dao;

    public ActionResult<UserResponse> login(String email, String password)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserResponse authenticate = dao.authenticate(email, hashPassword(password));
        ActionResult<UserResponse> result = new ActionResult<>();
        if (authenticate != null) {
            result.setSuccess(true);
            result.setData(authenticate);
        } else {
            result.setSuccess(false);
            result.setMessage("ایمیل یا پسورد اشتباه است");
        }
        return result;
    }

    private String hashPassword(String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return new String(bytes, "UTF-8");
    }

    @Transactional
    public ActionResult<UserResponse> register(
            String password,
            String rePassword,
            String name,
            String email,
            String role
    ) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        ActionResult<UserResponse> result = new ActionResult<>();
        String[] validate = validateRegister(password, rePassword, name, email, role);
        if (validate.length > 0) {
            result.setMessage(String.join("\n", validate));
        } else {
            if (!dao.containsUser(email)) {
                UserResponse response = dao.registerUser(hashPassword(password), name, email, role);
                result.setData(response);
                result.setSuccess(true);
            } else {
                result.setMessage("این ایمیل قبلا ثبت شده است");
            }
        }
        return result;
    }

    private String[] validateRegister(
            String password,
            String rePassword,
            String name,
            String email,
            String role
    ) {
        List<String> invalid = new ArrayList<>();
        if (password == null) {
            invalid.add("لطفا پسورد را وارد نمایید");
        } else if (password.length() < 6) {
            invalid.add("پسورد ضعیف است");
        }
        if (rePassword == null) {
            invalid.add("لطفا رمز عبور را دوباره وارد نمایید");
        } else if (!rePassword.equals(password)) {
            invalid.add("رمز وارد شده دوباره شبیه رمز اول نیست");
        }
        if (name == null) {
            invalid.add("لطفا نام خود را وارد نمایید");
        }
        if (email == null || !email.matches("^[a-z]([a-z0-9]|_[a-z0-9]|.[a-z0-9])+@[a-z0-9_]+([.][a-z0-9]+)+$")) {
            invalid.add("ایمیل معتبر نیست");
        }
        if (role == null || (!role.equals(Role.student) && !role.equals(Role.teacher))) {
            invalid.add("لطفا نقش خود را درست وارد کنید");
        }
        String[] ans = new String[invalid.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = invalid.get(i);
        }
        return ans;
    }

    public ActionResult<CaseResponse> setCase(
            String title,String to,String importance,String body,String token){
        ActionResult<CaseResponse> result = new ActionResult<CaseResponse>();
        String[] massages = validateSetCase(title,
                                to,
                                importance,
                                body);
        if(massages.length <= 0){
            //to is a name
            if(dao.containsUserAndValid(to)){
                //CaseResponse response = dao.setCase(title,body,to,token,importance);
                result.setSuccess(true);
                result.setMessage(null);
                result.setData(null);
            }else {
                result.setMessage("گیرنده نامعتبر است");
            }

        }else {
            result.setMessage(String.join("\n", massages));
        }
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
        if(to == null || to.length() == 0){
            massages.add("گیرنده را تعیین کنید");
        }
        if(importance == null || importance.length() == 0){
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
