package business.player;

import java.util.Collection;
import business.player.tPlayer.tInventory;
import integration.player.DAOInventory;
import integration.player.DAOInventoryImp;
import integration.player.DAOPlayer;
import integration.player.DAOPlayerImp;

public class SAInventoryImp implements SAInventory {
	private DAOPlayer daoPlayer;
    private DAOInventory daoInventory;

    public SAInventoryImp() {
    	this.daoPlayer = new DAOPlayerImp();
        this.daoInventory = new DAOInventoryImp();
    }

    @Override
    public tInventory readInventory(String playerName) {
        return daoInventory.readInventory(playerName);
    }

    @Override
    public int updateInventory(tInventory inventory) {
        if(daoPlayer.readPlayer(inventory.getPlayerName()) != null) return daoInventory.updateInventory(inventory);
        return -1;
    }

    @Override
    public Collection<tInventory> readAllInventories() {
        return daoInventory.readAllInventories();
    }
}
