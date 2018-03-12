package practic;

import practic.domain.ProductEntity;
import practic.service.ProductEJB;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProductBean implements Serializable {

    private ProductEntity product = new ProductEntity();

    @EJB
    private ProductEJB productEJB;

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public void createProduct(){
        productEJB.createProduct(product);
        product = new ProductEntity();
    }

    public void deleteProduct(ProductEntity product){
        productEJB.deleteProduct(product);
    }

    public void updateProduct(ProductEntity product){
        productEJB.createProduct(product);
    }

    public List<ProductEntity> showProducts(){
        return productEJB.showProducts();
    }



}
