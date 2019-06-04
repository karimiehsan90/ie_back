package ir.asta.training.auth.services.impl;

import ir.asta.training.auth.manager.UserManager;
import ir.asta.training.auth.services.UsersService;
import ir.asta.wise.core.datamanagement.ActionResult;
import ir.asta.wise.core.response.ManageUserResponse;
import ir.asta.wise.core.response.UserResponse;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Named(value = "userService")
public class UsersServiceImpl implements UsersService {
    @Inject
    private UserManager manager ;

    // dual request .
    @Override
    public ActionResult<UserResponse> editPro(String name, String email, String phone, String pass, String ppass , String token) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return manager.edit(name,pass,email,ppass,phone,token);
    }

    @Override
    public ActionResult<ManageUserResponse> getUsers(String token) {
        return manager.getUsers(token);
    }
}
