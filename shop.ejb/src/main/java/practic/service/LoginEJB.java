package practic.service;

import practic.domain.UsersEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Stateless
public class LoginEJB {

    @PersistenceContext(name = "employeesdb")
    private EntityManager entityManager;

    public boolean addUser(UsersEntity user) {
        entityManager.persist(user);
        return true;
    }

    /*public List<UsersEntity> showUsers() {
        TypedQuery<UsersEntity> query = entityManager.createQuery(
                "select u from UsersEntity u", UsersEntity.class);
        return query.getResultList();
    }*/

    public UsersEntity findUser(UsersEntity user) {
        TypedQuery<UsersEntity> query =
                entityManager.createNamedQuery(UsersEntity.FIND_USER, UsersEntity.class);
        // Установим параметры поискапользователя
        query.setParameter("login", user.getLogin());

        return query.getSingleResult();
    }
}
