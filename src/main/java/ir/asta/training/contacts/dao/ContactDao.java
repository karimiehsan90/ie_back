package ir.asta.training.contacts.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import ir.asta.training.contacts.entities.ContactEntity;

@Named("contactDao")
public class ContactDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(ContactEntity entity) {
		entityManager.persist(entity);
	}
	
	public ContactEntity load(Long id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ContactEntity> query = cb.createQuery(ContactEntity.class);
		Root<ContactEntity> root = query.from(ContactEntity.class);
		query.select(root);
		query.where(cb.equal(root.get("id"), id));
		TypedQuery<ContactEntity> tq = entityManager.createQuery(query);
		List<ContactEntity> list = tq.getResultList();
		if (list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public List<ContactEntity> findAll() {
		Query query = entityManager.createQuery("select e from ContactEntity e");
		return query.getResultList();
	}
}
