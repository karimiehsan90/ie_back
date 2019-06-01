package ir.asta.training.auth.services;

import ir.asta.wise.core.datamanagement.ActionResult;
import ir.asta.wise.core.response.UserResponse;
import ir.asta.wise.core.response.UserResponseOthers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
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

    @Path("/setAccept")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<Integer> setAccept(@FormParam("id") String id ,
                                          @FormParam("token") String token
            )throws UnsupportedEncodingException, NoSuchAlgorithmException;

    @Path("/setActive")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<Integer> setActive(@FormParam("id") String id ,
                                           @FormParam("token") String token
    )throws UnsupportedEncodingException, NoSuchAlgorithmException;

    @Path("/setdeactive")
    public ActionResult<Integer> setdeactive(@FormParam("id") String id ,
                                             @FormParam("token") String token)
            throws UnsupportedEncodingException, NoSuchAlgorithmException;



    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public ActionResult<List<UserResponseOthers>> getToPossibles(
            @FormParam("token") String token
    );





}
