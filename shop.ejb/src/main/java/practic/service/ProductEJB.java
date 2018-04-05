package practic.service;


import practic.domain.ProductEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ProductEJB {

    @PersistenceContext(name = "employeesdb")
    private EntityManager entityManager;

    public ProductEntity createProduct(ProductEntity product){
        entityManager.persist(product);

        return product;
    }

    public boolean deleteProduct(ProductEntity product){
        entityManager.remove(product);
        return true;
    }

    public List<ProductEntity> showProducts(){
        TypedQuery<ProductEntity> query =  entityManager.createQuery(
                "select p from ProductEntity p", ProductEntity.class);
        return query.getResultList();
    }


}
