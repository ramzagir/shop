package practic.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Orders")
public class OrderEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    //здесь должен быть какой-то хешкод для создания серийного номера
    private int serialNumber = 1;

    private int sum;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "prod_in_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<ProductEntity> productsInOrder;

    @ManyToOne(cascade = CascadeType.ALL)
    private UsersEntity user;

    public OrderEntity(){};
    public OrderEntity(List<ProductEntity> productsInOrder, UsersEntity user, int sum){
        this.productsInOrder = productsInOrder;
        this.user = user;
        this.sum = sum;
    }

    public long getId() {
        return id;
    }

    public List<ProductEntity> getProductsInOrder() {
        return productsInOrder;
    }

    public void setProductsInOrder(List<ProductEntity> productsInOrder) {
        this.productsInOrder = productsInOrder;
    }

    public UsersEntity getUser() {
        return user;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public int getSum() {
        return sum;
    }
}
