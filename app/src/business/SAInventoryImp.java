package business;

import java.util.Collection;
import integration.DAOInventory;
import integration.DAOInventoryImp;

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
    public int deleteInventory(String playerName) {
        return daoInventory.deleteInventory(playerName);
    }

    @Override
    public Collection<tInventory> readAllInventories() {
        return daoInventory.readAllInventories();
    }
}
