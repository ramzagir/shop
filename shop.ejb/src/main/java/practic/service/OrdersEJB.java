package practic.service;

import org.omg.CORBA.UserException;
import practic.domain.OrderEntity;
import practic.domain.ProductEntity;
import practic.domain.UsersEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Order;
import javax.xml.registry.infomodel.User;
import java.util.List;
import java.util.logging.Level;

@Stateless
@LocalBean
public class OrdersEJB {

    @PersistenceContext(name = "employeesdb")
    private EntityManager entityManager;

    public boolean addOrder(OrderEntity order) {

        entityManager.merge(order);

        return true;
    }

    public List<OrderEntity> UsersOrders(UsersEntity user) {
        TypedQuery<OrderEntity> query = entityManager.createQuery(
                "select o from OrderEntity o where o.user =:user", OrderEntity.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    public List<ProductEntity> getProductsInOrder(OrderEntity order) {
        order = entityManager.find(OrderEntity.class, order.getId());
        if (order == null)
            return null;
        return order.getProductsInOrder();
    }

    public UsersEntity findUser(String login) {
        TypedQuery<UsersEntity> query = entityManager.createQuery(
                "select u FROM UsersEntity u", UsersEntity.class);

        List<UsersEntity> list = query.getResultList();

        for (UsersEntity u : list) {
            if (u.getLogin().equals(login))
                return u;
        }
        return null;
    }
}
