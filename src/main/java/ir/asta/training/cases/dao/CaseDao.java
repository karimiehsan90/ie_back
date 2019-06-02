package ir.asta.training.cases.dao;

import ir.asta.training.auth.entities.CaseEntity;
import ir.asta.wise.core.response.CaseResponse;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Named("caseDao")
public class CaseDao {
    @PersistenceContext
    private EntityManager manager;

    public List<CaseEntity> getMyCases(String token){
        Query query = manager.createQuery("select e from CaseEntity e where e.from.mongoId=:mongo_id");
        query.setParameter("mongo_id", token);
        return query.getResultList();
    }

    public List<CaseEntity> getCaseToMe(String token){
        Query query = manager.createQuery("select e from CaseEntity e where e.to.mongoId=:mongo_id");
        query.setParameter("mongo_id", token);
        return query.getResultList();
    }

    public CaseEntity setCase(CaseEntity entity){
        System.out.println(entity.getClass());
        manager.persist(entity);
        return entity;
    }

    public void makeCaseInvalidFrom(String fromId){
        Query query = manager.createQuery("UPDATE CaseEntity e SET e.from_id = -1"+
                "WHERE e.from_id=:fromId");
        query.setParameter("fromId",fromId).executeUpdate();
    }

    public void makeCaseInvalidTo(String toId){
        Query query = manager.createQuery("UPDATE CaseEntity e SET e.to_id = -1"+
                "WHERE e.to_id=:toId");
        query.setParameter("toId",toId).executeUpdate();

    }

    public List<CaseEntity> getAllCases(String from, String to) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<CaseEntity> cq = cb.createQuery(CaseEntity.class);
        Root<CaseEntity> caseEntity = cq.from(CaseEntity.class);
        cq.select(caseEntity);
        List<Predicate> list = new ArrayList<>();
        if (from != null){
            Predicate predicate = cb.equal(caseEntity.get("from").get("mongoId"), from);
            list.add(predicate);
        }
        if (to != null){
            Predicate predicate = cb.equal(caseEntity.get("to").get("mongoId"), to);
            list.add(predicate);
        }
        Predicate[] predicates = new Predicate[list.size()];
        for (int i = 0; i < predicates.length; i++) {
            predicates[i] = list.get(i);
        }
        Predicate and = cb.and(predicates);
        cq.where(and);
        TypedQuery<CaseEntity> q = manager.createQuery(cq);
        return q.getResultList();
    }
}
