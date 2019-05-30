package ir.asta.training.cases.services;

import ir.asta.training.cases.entities.CaseEntity;
import ir.asta.wise.core.datamanagement.ActionResult;
import ir.asta.wise.core.response.CaseResponse;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

@Path("/case")
public interface CaseService {
    @Path("/setCase")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<String> setCase(@FormParam("title") String title,
                                        @FormParam("to") String to,
                                        @FormParam("important") String importance,
                                        @FormParam("body") String body,
                                        @FormParam("token") String token)
            throws UnsupportedEncodingException, NoSuchAlgorithmException;

    @Path("/getMyCase")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<List<CaseResponse>> getMyCase(@FormParam("token") String token
    )throws UnsupportedEncodingException, NoSuchAlgorithmException;


}
