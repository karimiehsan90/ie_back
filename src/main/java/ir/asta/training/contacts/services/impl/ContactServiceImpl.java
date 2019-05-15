package ir.asta.training.contacts.services.impl;

import javax.inject.Inject;
import javax.inject.Named;

import ir.asta.training.contacts.entities.ContactEntity;
import ir.asta.training.contacts.manager.ContactManager;
import ir.asta.training.contacts.services.ContactService;
import ir.asta.wise.core.datamanagement.ActionResult;

import java.util.ArrayList;
import java.util.List;

@Named("contactService")
public class ContactServiceImpl implements ContactService {

	@Inject
	private ContactManager manager;
	
	@Override
	public ContactEntity load(Long id) {
		return manager.load(id);
	}

	@Override
	public ActionResult<Long> save(ContactEntity entity) {
		manager.save(entity);
		return new ActionResult<Long>(true, "New contact saved successfully.", entity.getId());
	}

	@Override
	public List<ContactEntity> findAll() {
		return manager.findAll();
//		return new ArrayList<>();
	}
}
