package ir.asta.training.cases.dao;

import ir.asta.training.auth.entities.ActionEntity;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named("actionDao")
public class ActionDao {
    @PersistenceContext
    private EntityManager manager;

    public void save(ActionEntity entity){
        manager.persist(entity);
    }
}
