package practic.service;

import practic.domain.OrderEntity;
import practic.domain.ProductEntity;
import practic.domain.UsersEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
public class OrdersEJB {

    @PersistenceContext(name = "employeesdb")
    private EntityManager entityManager;

    public boolean addOrder(List<ProductEntity> bascket, UsersEntity user){

        OrderEntity order = new OrderEntity(bascket, user);
        entityManager.persist(order);

        return true;
    }
}
