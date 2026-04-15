package integration;

import java.util.Collection;
import business.tProduct;

public interface DAOProduct {
	
    int createProduct(tProduct product);
    tProduct readProduct(String name);
    int updateProduct(tProduct product);
    int deleteProduct(String name);
    Collection<tProduct> readAllProducts();
}
