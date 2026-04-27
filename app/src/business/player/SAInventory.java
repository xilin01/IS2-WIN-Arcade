package business.player;

import java.util.Collection;
import business.game.tGame;
import business.player.tPlayer.tInventory;

public interface SAInventory {

    tInventory readInventory(String playerName);
    int updateInventory(tInventory inventory);
    Collection<tInventory> readAllInventories();
    void consumeGame(String playerName, tGame game);
}
