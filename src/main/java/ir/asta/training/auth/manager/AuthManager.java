package ir.asta.training.auth.manager;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import ir.asta.training.auth.dao.AuthDao;
import ir.asta.training.auth.fixed.Role;
import ir.asta.training.cases.dao.ActionDao;
import ir.asta.training.cases.dao.CaseDao;
import ir.asta.training.cases.manager.NotificationManager;
import ir.asta.wise.core.datamanagement.ActionResult;
import ir.asta.wise.core.response.UserResponse;
import ir.asta.wise.core.response.UserResponseOthers;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Named("authManager")
public class AuthManager {
    @Inject
    private AuthDao dao;
    @Inject
    private CaseDao caseDao;

    @Inject
    private NotificationManager notificationManager;

    @Inject
    private ActionDao actionDao;

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

    String hashPassword(String password)
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

    @Transactional
    public ActionResult<UserResponse> registerByManager(
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
                UserResponse response = dao.registerUserByManager(hashPassword(password), name, email, role);
                result.setData(response);
                result.setSuccess(true);
            } else {
                result.setMessage("این ایمیل قبلا ثبت شده است");
            }
        }
        return result;
    }
    @Transactional
    public ActionResult<UserResponse> deleteUser(String token, String id) {
        ActionResult<UserResponse> result = new ActionResult<>();
        String massage = "";
        UserResponse manager = dao.authenticate(token);
        long idUser = dao.getUserSQLIDBymongoID(id);
        String idUserStr = ""+idUser ;
        System.out.println(idUser);
        UserResponse user = dao.containsUserAsStuTeach(idUser);
        if(manager != null){
            if(manager.getRole().equals(Role.manager)){
                if(user !=null){
                    caseDao.makeCaseInvalidFrom(idUserStr);
                    caseDao.makeCaseInvalidTo(idUserStr);
                    actionDao.makeActionInvalidFrom(idUserStr);
                    dao.deleteUser(idUserStr,user);
                    massage += "removing is successful";
                    result.setSuccess(true);
                    result.setData(user);
                }else {
                    massage += "this user doesn't exist";
                }
            }else {
                massage += "it doesn't exist permission";
            }

        }else {
            massage += "fail";
        }
        result.setMessage(massage);
        return result;
    }

    public ActionResult<Integer> setAccept(String id, String token) {
        ActionResult<Integer> result = new ActionResult<>();
        String massage = "";
        int modified = 0;
        UserResponse userResponse = dao.authenticate(token);
        if (userResponse != null) {
            String role = userResponse.getRole();
            if (role.equals(Role.manager)) {
                modified = dao.setAccept(id);
                if (modified == 1) {
                    massage += "it's done";
                    result.setSuccess(true);
                } else {
                    massage += "fail";
                }
            } else {
                massage += "it dosen't exist permission";
            }
        }
        result.setMessage(massage);
        result.setData(modified);

        return result;
    }

    public ActionResult<Integer> setActive(String id, String token) {
        ActionResult<Integer> result = new ActionResult<>();
        String massage = "";
        Integer modified = 0;
        UserResponse userResponse = dao.authenticate(token);
        if (userResponse != null) {
            String role = userResponse.getRole();
            if (role.equals(Role.manager)) {
                modified = dao.setActive(id);
                if (modified == 1) {
                    massage += "it's done";
                    result.setSuccess(true);
                } else {
                    massage += "fail";
                }
            } else {
                massage += "it dosen't exist permission";
            }
        }
        result.setMessage(massage);
        result.setData(modified);

        return result;
    }

    public ActionResult<Integer> setdeactive(String id, String token) {
        ActionResult<Integer> result = new ActionResult<>();
        String massage = "";
        Integer modified = 0;
        UserResponse userResponse = dao.authenticate(token);
        if (userResponse != null) {
            String role = userResponse.getRole();
            if (role.equals(Role.manager)) {
                modified = dao.setDeactive(id);
                if (modified == 1) {
                    massage += "it's done";
                    result.setSuccess(true);
                } else {
                    massage += "fail";
                }
            } else {
                massage += "it doesn't exist permission";
            }
        }
        result.setMessage(massage);
        result.setData(modified);

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

    public ActionResult<List<UserResponseOthers>> getPossibles(String token) {
        UserResponse authenticate = dao.authenticate(token);
        ActionResult<List<UserResponseOthers>> result = new ActionResult<>();
        if (authenticate != null) {
            result.setSuccess(true);
            result.setData(dao.getPossibles());
        } else {
            result.setMessage("شما لاگین نیستید");
        }
        return result;
    }

    public ActionResult<UserResponse> googleLogin(String idTokenString) throws GeneralSecurityException, IOException {
        ActionResult<UserResponse> result = new ActionResult<>();
        JsonFactory jsonFactory = new JacksonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jsonFactory)
                .setAudience(Collections.singletonList("689217686363-o57mhgp17553uibqed439j0u2mk3sc54.apps.googleusercontent.com"))
                .build();
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null){
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            UserResponse response = dao.getByEmail(email);
            result.setData(response);
            result.setSuccess(response != null);
            if (response == null){
                result.setMessage("کاربر یافت نشد");
            }
        }
        else {
            result.setMessage("توکن معتبر نیست");
        }
        return result;
    }

    public ActionResult<Boolean> forgetPassword(String phone) {
        ActionResult<Boolean> result = new ActionResult<>();
        if (phone != null && dao.containsPhone(phone)){
            String otp = generateOTP();
            Thread thread = new Thread(() -> {
                String text = "your code : " + otp;
                try {
                    notificationManager.sendSMS(text, phone);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            dao.setOTP(phone, otp);
            result.setSuccess(true);
            result.setMessage("کد با موفقیت به شماره تلفن شما ارسال شد");
        }
        else {
            result.setMessage("شماره تلفن یافت نشد");
        }
        return result;
    }

    private String generateOTP() {
        StringBuilder s = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            s.append((char) ('0' + random.nextInt(10)));
        }
        return s.toString();
    }

    public ActionResult<Boolean> submitForgetPassword(String phone, String code) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String otp = dao.getOTP(phone);
        ActionResult<Boolean> result = new ActionResult<>();
        if (otp != null && otp.equals(code)){
            String newPassword = generatePassword();
            dao.updateByPhone(phone, hashPassword(newPassword));
            new Thread(() -> {
                String text = "your new password : " + newPassword;
                try {
                    notificationManager.sendSMS(text, phone);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            result.setSuccess(true);
            result.setMessage("رمز عبور جدید به شماره تلفن شما ارسال شد");
        }
        else {
            result.setMessage("کد درست نیست");
        }
        return result;
    }

    private String generatePassword() {
        return generateOTP();
    }
}
