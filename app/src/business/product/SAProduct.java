package business.product;

import java.util.Collection;

public interface SAProduct {
    int createProduct(tProduct product);
    tProduct readProduct(String name);
    int updateProduct(tProduct product);
    int deleteProduct(String name);
    Collection<tProduct> readAllProducts();
}
