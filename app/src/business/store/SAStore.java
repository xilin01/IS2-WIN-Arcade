package business.store;

import business.packages.tGamesPackage;
import business.product.tProduct;

public interface SAStore {
    boolean buyProduct(String playerName, tProduct product);
    boolean buyPackage(String playerName, tGamesPackage pack);
}
