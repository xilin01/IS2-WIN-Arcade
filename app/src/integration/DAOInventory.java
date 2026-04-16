package integration;

import java.util.Collection;
import business.tPlayer.tInventory;

public interface DAOInventory {
	
    int createInventory(tInventory inventory);
    tInventory readInventory(String playerName);
    int updateInventory(tInventory inventory);
    int deleteInventory(String playerName);
    Collection<tInventory> readAllInventories();
}
