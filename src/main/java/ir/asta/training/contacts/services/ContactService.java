package ir.asta.training.contacts.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ir.asta.training.contacts.entities.ContactEntity;
import ir.asta.wise.core.datamanagement.ActionResult;

import java.util.List;

@Path("/contact")
public interface ContactService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/load/{pk}")
	public ContactEntity load(@PathParam("pk") Long id);

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ActionResult<Long> save(ContactEntity entity);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findall")
	public List<ContactEntity> findAll();
	
}
