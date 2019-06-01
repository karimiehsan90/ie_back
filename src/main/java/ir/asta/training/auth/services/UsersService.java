package ir.asta.training.auth.services;

import ir.asta.wise.core.datamanagement.ActionResult;
import ir.asta.wise.core.response.ManageUserResponse;
import ir.asta.wise.core.response.UserResponse;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Path("/user")
public interface UsersService {
    @Path("/editPro")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<UserResponse> editPro ( @FormParam("name") String name ,
                                                @FormParam("email") String email ,
                                                @FormParam("phone") String phone ,
                                                @FormParam("password") String pass ,
                                                @FormParam("perv_pass") String ppass ,
                                                @FormParam("token") String token
    ) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    @Path("/manage")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public ActionResult<ManageUserResponse> getUsers(@FormParam("token") String token);

}
