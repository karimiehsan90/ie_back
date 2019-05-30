package ir.asta.training.cases.services;

import ir.asta.training.cases.entities.CaseEntity;
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
import java.util.Set;

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
                                        @Multipart("file")Attachment attachment)
            throws IOException, NoSuchAlgorithmException;

    @Path("/getMyCase")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public ActionResult<List<CaseResponse>> getMyCase(@FormParam("token") String token
    )throws UnsupportedEncodingException, NoSuchAlgorithmException;


}
