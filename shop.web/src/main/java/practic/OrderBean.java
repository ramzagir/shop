package practic;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.SelectableDataModel;
import practic.domain.OrderEntity;
import practic.domain.ProductEntity;
import practic.domain.UsersEntity;
import practic.service.OrdersEJB;
import practic.service.ProductEJB;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class OrderBean implements Serializable {

    private List<ProductEntity> bascket = new ArrayList<>();
    private OrderEntity order;
    private String message;
    private OrderEntity selectedOrder = new OrderEntity();

    @EJB
    private OrdersEJB ordersEJB;

    public String getMessage() {
        return message;
    }

    public List<ProductEntity> getBascket() {
        return bascket;
    }

    public OrderEntity getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(OrderEntity order) {
        selectedOrder = order;
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

        FacesContext context = FacesContext.getCurrentInstance();

        if(user == null) {
            message = "Пользователь не найден";
            return;
        }
        if(bascket.isEmpty())
            return;
        //Достаем пользователя из БД
        user = ordersEJB.findUser(user.getLogin());
        //Создаем заказ
        order = new OrderEntity(bascket, user, basketSum());
        //Добавляем зака
        if(ordersEJB.addOrder(order))
            context.addMessage(null, new FacesMessage("Seccessful", "Order added"));
        else context.addMessage(null, new FacesMessage("Error", "Order delete"));


        bascket = new ArrayList<>();
    }

    public int basketSum(){
        int sum = 0;
        for(ProductEntity p : bascket){
            if(p.getQuantity() > 1) {
                sum = sum + p.getPrice() * p.getQuantity();
                continue;
            }
            sum = sum + p.getPrice();
        }
        return sum;
    }

    public List<OrderEntity> UserOrders(UsersEntity user){
        user = ordersEJB.findUser(user.getLogin());
        return user.getUserOrders();
    }

    public List<ProductEntity> getProductsInOrder(){
        return ordersEJB.getProductsInOrder(selectedOrder);
    }
}
