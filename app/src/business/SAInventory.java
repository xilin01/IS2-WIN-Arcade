package business;

import java.util.Collection;

public interface SAInventory {
	
    tInventory readInventory(String playerName);
    int updateInventory(tInventory inventory);
    int deleteInventory(String playerName);
    Collection<tInventory> readAllInventories();
}
