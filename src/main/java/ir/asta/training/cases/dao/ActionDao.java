package ir.asta.training.cases.dao;

import ir.asta.training.auth.entities.ActionEntity;
import ir.asta.training.auth.entities.CaseEntity;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Named("actionDao")
public class ActionDao {
    @PersistenceContext
    private EntityManager manager;

    public void save(ActionEntity entity){
        manager.persist(entity);
    }

    public void makeActionInvalidFrom(String fromId){
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaUpdate<ActionEntity> cq = cb.createCriteriaUpdate(ActionEntity.class);
        Root<ActionEntity> root = cq.from(ActionEntity.class);
        Predicate equal = cb.equal(root.get("from").get("id"), Long.valueOf(fromId));
        cq.where(equal);
        cq.set("from", null);
        Query query = manager.createQuery(cq);
        query.executeUpdate();
    }
}
