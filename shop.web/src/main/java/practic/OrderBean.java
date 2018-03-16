package practic;

import practic.domain.OrderEntity;
import practic.domain.ProductEntity;
import practic.domain.UsersEntity;
import practic.service.OrdersEJB;
import practic.service.ProductEJB;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class OrderBean implements Serializable {

    private List<ProductEntity> bascket = new ArrayList<>();

    @EJB
    private OrdersEJB ordersEJB;

    public List<ProductEntity> getBascket() {
        return bascket;
    }

    public void addToBascket(ProductEntity product){
        for (ProductEntity p : bascket) {
            if(product.equals(p)){
                p.setQuantity(p.getQuantity() + 1);
                return;
            }
        }
        bascket.add(product);
    }

    public void deleteFromBascket(ProductEntity product){
        for (ProductEntity p : bascket) {
            if(product.equals(p) && p.getQuantity() > 1){
                p.setQuantity(p.getQuantity() - 1);
                return;
            }
        }
        bascket.remove(product);
    }

    public void addOrder(UsersEntity user){
        if(bascket.isEmpty())
            return;
        ordersEJB.addOrder(bascket, user);
        bascket = new ArrayList<>();
    }

    public int basketSum(){
        int sum = 0;
        for(ProductEntity p : bascket){
            sum =+ p.getPrice();
        }
        return sum;
    }
}
