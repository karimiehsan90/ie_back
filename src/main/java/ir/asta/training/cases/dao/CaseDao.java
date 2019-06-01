package ir.asta.training.cases.dao;

import ir.asta.training.auth.entities.CaseEntity;
import ir.asta.wise.core.response.CaseResponse;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
}
