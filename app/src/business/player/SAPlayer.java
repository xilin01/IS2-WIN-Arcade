package business.player;

import java.util.Collection;
import business.player.tPlayer.tInventory;

public interface SAPlayer {
	
    int createPlayer(tPlayer player);
    int createPlayer(String name, String password);
    tPlayer readPlayer(String name);
    int updatePlayer(tPlayer player);
    int deletePlayer(String name);
    Collection<tPlayer> readAllPlayers();

    tInventory readInventory(String playerName);
    int updateInventory(tInventory inventory);
    Collection<tInventory> readAllInventories();
}
