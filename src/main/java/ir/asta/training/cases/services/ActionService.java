package ir.asta.training.cases.services;

import ir.asta.wise.core.datamanagement.ActionResult;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/action")
public interface ActionService {
    @Path("/set")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public ActionResult<Boolean> setAction(@Multipart("token") String token,
                                           @Multipart("case_id") long caseId,
                                           @Multipart("content") String content,
                                           @Multipart("status") int status,
                                           @Multipart(value = "file", required = false) Attachment attachment,
                                           @Multipart(value = "to", required = false) Long to) throws IOException;
}
