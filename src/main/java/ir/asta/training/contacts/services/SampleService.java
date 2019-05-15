package ir.asta.training.contacts.services;

import ir.asta.training.contacts.entities.TimeEntity;
import ir.asta.training.contacts.entities.BookEntity;

import javax.servlet.ServletException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/myservice")
public interface SampleService {
    @POST
    @Path("/book")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BookEntity processBook(BookEntity entity);

    @GET
    @Path("/time/{year}/{month}/{day}")
    @Produces(MediaType.APPLICATION_JSON)
    public TimeEntity convertDate(@PathParam("year") int year,
                                  @PathParam("month") int month,
                                  @PathParam(("day")) int day);

    @POST
    @Path("/mysubmit")
    @Produces(MediaType.TEXT_HTML)
    public void submit() throws ServletException, IOException;

}
