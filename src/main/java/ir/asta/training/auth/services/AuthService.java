package ir.asta.training.auth.services;

import ir.asta.training.auth.entities.UserEntity;
import ir.asta.wise.core.datamanagement.ActionResult;
import ir.asta.wise.core.response.UserResponse;
import ir.asta.wise.core.response.UserResponseOthers;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Path("/auth")
public interface AuthService {
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<UserResponse> login(@FormParam("email") String email,
                                            @FormParam("password") String password
    ) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<UserResponse> register(@FormParam("password") String password,
                                               @FormParam("re_password") String rePassword,
                                               @FormParam("name") String name,
                                               @FormParam("email") String email,
                                               @FormParam("role") String role
    ) throws UnsupportedEncodingException, NoSuchAlgorithmException;


    @Path("/registerByManager")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<UserResponse> registerByManager(@FormParam("password") String password,
                                               @FormParam("re_password") String rePassword,
                                               @FormParam("name") String name,
                                               @FormParam("email") String email,
                                               @FormParam("role") String role
    ) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    @Path("/deleteUser")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<UserResponse> deleteUser(@FormParam("token") String token,
                                               @FormParam("id") String id
    ) throws UnsupportedEncodingException, NoSuchAlgorithmException;


    @Path("/setAccept")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<Integer> setAccept(@FormParam("id") String id,
                                           @FormParam("token") String token
    ) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    @Path("/setActive")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<Integer> setActive(@FormParam("id") String id,
                                           @FormParam("token") String token
    ) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    @Path("/setdeactive")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public ActionResult<Integer> setdeactive(@FormParam("id") String id,
                                             @FormParam("token") String token)
            throws UnsupportedEncodingException, NoSuchAlgorithmException;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public ActionResult<List<UserResponseOthers>> getToPossibles(
            @FormParam("token") String token
    );

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/google")
    public ActionResult<UserResponse> googleLogin(@FormParam("token") String idToken) throws GeneralSecurityException, IOException;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/forget")
    public ActionResult<Boolean> forgetPassword(@FormParam("phone") String phone);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/forget/submit")
    public ActionResult<Boolean> submitForgetPassword(@FormParam("phone") String phone,
                                                      @FormParam("code") String code) throws UnsupportedEncodingException, NoSuchAlgorithmException;

}
