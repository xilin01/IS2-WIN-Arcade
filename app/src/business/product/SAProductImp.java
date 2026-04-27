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
        return daoProduct.createProduct(product);
    }

    @Override
    public tProduct readProduct(String name) {
        return daoProduct.readProduct(name);
    }

    @Override
    public int updateProduct(tProduct product) {
        return daoProduct.updateProduct(product);
    }

    @Override
    public int deleteProduct(String name) {
        return daoProduct.deleteProduct(name);
    }

    @Override
    public Collection<tProduct> readAllProducts() {
        return daoProduct.readAllProducts();
    }
}
