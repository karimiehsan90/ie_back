package ir.asta.training.cases.services;

import ir.asta.wise.core.datamanagement.ActionResult;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import ir.asta.wise.core.response.CaseResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Path("/case")
public interface CaseService {
    @Path("/setCase")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ActionResult<String> setCase(@Multipart("title") String title,
                                        @Multipart("to") String to,
                                        @Multipart("important") String importance,
                                        @Multipart("body") String body,
                                        @Multipart("token") String token,
                                        @Multipart(value = "file", required = false)Attachment attachment)
            throws IOException, NoSuchAlgorithmException;

    @Path("/getMyCase")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<List<CaseResponse>> getMyCase(@FormParam("token") String token
    )throws UnsupportedEncodingException, NoSuchAlgorithmException;

    @Path("/getCaseToMe")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<List<CaseResponse>> getCaseToMe(@FormParam("token") String token
    )throws UnsupportedEncodingException, NoSuchAlgorithmException;

    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<List<CaseResponse>> getAllCases(@FormParam("token") String token,
                                                        @FormParam("from") String from,
                                                        @FormParam("to") String to);

    @Path("/setRate")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<Boolean> setRate(    @FormParam("token") String token,
                                                        @FormParam("id") long id,
                                             @FormParam("happy") boolean h)  throws IOException, NoSuchAlgorithmException;


}
