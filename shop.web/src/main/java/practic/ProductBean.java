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

    private String searchName;
    private List<ProductEntity> selectedProducts;
    private List<ProductEntity> filteredProducts;

    private ProductEntity product = new ProductEntity();

    @EJB
    private ProductEJB productEJB;

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public List<ProductEntity> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<ProductEntity> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public List<ProductEntity> getFilteredProducts() {
        return filteredProducts;
    }

    public void setFilteredProducts(List<ProductEntity> filteredProducts) {
        this.filteredProducts = filteredProducts;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
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

    public void findProduct(){
        for(ProductEntity p : showProducts()) {
            if (p.getName().equalsIgnoreCase(searchName))
                product = p;
        }
    }

    public List<ProductEntity> showProducts(){
        return productEJB.showProducts();
    }



}
