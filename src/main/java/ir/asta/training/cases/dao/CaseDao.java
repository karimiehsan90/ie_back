package ir.asta.training.cases.dao;

import ir.asta.training.cases.entities.CaseEntity;
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

    public List<CaseResponse> getMyCases(String token){
        Query query = manager.createQuery("select e from CaseEntity e where e.from.mongoId=:mongo_id");
        query.setParameter("mongo_id", token);
        List<CaseEntity> cases= query.getResultList();
        List<CaseResponse> caseResponses= new ArrayList<>();
        CaseResponse caseResponse = new CaseResponse();

        if(cases != null) {
            for (Object caseEntity : cases) {
                System.out.println(caseEntity.getClass());
                System.out.println(CaseEntity.class);
                /*caseResponse.setTitle(caseEntity.getTitle());
                caseResponse.setBody(caseEntity.getBody());
                caseResponse.setImportance(caseEntity.getImportance());
                caseResponse.setStatus(caseEntity.getStatus());
                caseResponse.setFile(caseEntity.getFile());
                caseResponse.setTo(caseEntity.to);
                caseResponse.setFrom(caseEntity.from);

                caseResponses.add(caseResponse);*/
            }
        }

        return caseResponses;
    }

    public CaseEntity setCase(CaseEntity entity){
        manager.persist(entity);
        return entity;
    }
}
