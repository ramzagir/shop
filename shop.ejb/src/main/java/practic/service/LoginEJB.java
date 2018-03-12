package practic.service;

import practic.domain.UsersEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class LoginEJB {

    @PersistenceContext(name = "employeesdb")
    private EntityManager entityManager;

    public boolean addUser(UsersEntity user){
        entityManager.persist(user);
        return true;
    }

    public List<UsersEntity> showUsers(){
        TypedQuery<UsersEntity> query = entityManager.createQuery(
                "select u from UsersEntity u", UsersEntity.class);
        return query.getResultList();


    }
}
