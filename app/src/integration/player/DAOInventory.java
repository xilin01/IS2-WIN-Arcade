package integration.player;

import java.util.Collection;
import business.player.tPlayer.tInventory;

public interface DAOInventory {
	
    int createInventory(tInventory inventory);
    tInventory readInventory(String playerName);
    int updateInventory(tInventory inventory);
    Collection<tInventory> readAllInventories();
}
