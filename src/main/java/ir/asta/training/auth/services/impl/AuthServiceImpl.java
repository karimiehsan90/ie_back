package ir.asta.training.auth.services.impl;

import ir.asta.training.auth.dao.AuthDao;
import ir.asta.training.cases.entities.CaseEntity;
import ir.asta.training.auth.manager.AuthManager;
import ir.asta.training.auth.services.AuthService;
import ir.asta.wise.core.datamanagement.ActionResult;
import ir.asta.wise.core.response.UserResponse;
import ir.asta.wise.core.response.UserResponseOthers;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Named("authService")
public class AuthServiceImpl implements AuthService {
    @Inject
    private AuthManager manager;

    @Override
    public ActionResult<UserResponse> login(String email, String password)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return manager.login(email, password);
    }

    @Override
    public ActionResult<UserResponse> register(
                                               String password,
                                               String rePassword,
                                               String name,
                                               String email,
                                               String role
                                               )
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return manager.register(password, rePassword, name, email, role);
    }

    @Override
    public ActionResult<List<UserResponseOthers>> getToPossibles(String token) {
        return manager.getPossibles(token);
    }


}
