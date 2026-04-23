package business.product;

import java.util.Collection;
import integration.product.DAOProduct;
import integration.product.DAOProductImp;

public class SAProductImp implements SAProduct {
    private DAOProduct daoProduct;

    public SAProductImp() {
        this.daoProduct = new DAOProductImp();
    }

    @Override
    public int createProduct(tProduct product) {
        if(daoProduct.readProduct(product.getName()) == null) return daoProduct.createProduct(product);
        return -1;
    }

    @Override
    public tProduct readProduct(String name) {
        return daoProduct.readProduct(name);
    }

    @Override
    public int updateProduct(tProduct product) {
        if(daoProduct.readProduct(product.getName()) != null) return daoProduct.updateProduct(product);
        return -1;
    }

    @Override
    public int deleteProduct(String name) {
        if(daoProduct.readProduct(name) != null) return daoProduct.deleteProduct(name);
        return -1;
    }

    @Override
    public Collection<tProduct> readAllProducts() {
        return daoProduct.readAllProducts();
    }
}
