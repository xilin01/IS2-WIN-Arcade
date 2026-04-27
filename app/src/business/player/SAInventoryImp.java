package business.player;

import java.util.Collection;
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
}
