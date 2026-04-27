package business.player;

import java.util.Collection;
import java.util.Map;
import business.game.tGame;
import business.player.tPlayer.tInventory;
import integration.player.DAOInventory;
import integration.player.DAOInventoryImp;

public class SAInventoryImp implements SAInventory {
    private DAOInventory daoInventory;

    public SAInventoryImp() {
        this.daoInventory = new DAOInventoryImp();
    }

    @Override
    public tInventory readInventory(String playerName) {
        return daoInventory.readInventory(playerName);
    }

    @Override
    public int updateInventory(tInventory inventory) {
        return daoInventory.updateInventory(inventory);
    }

    @Override
    public Collection<tInventory> readAllInventories() {
        return daoInventory.readAllInventories();
    }

    @Override
    public void consumeGame(String playerName, tGame game) {
        tInventory inventory = daoInventory.readInventory(playerName);
        Map<tGame, Integer> games = inventory.getGames();
        Integer amount = games.get(game);
        if (amount == null) return;
        if (amount >= 2) games.put(game, amount - 1);
        else games.remove(game);
        daoInventory.updateInventory(inventory);
    }
}
