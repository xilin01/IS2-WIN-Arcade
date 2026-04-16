package business;

import java.util.Collection;
import business.tPlayer.tInventory;
import integration.DAOPlayer;
import integration.DAOPlayerImp;
import integration.DAOInventory;
import integration.DAOInventoryImp;

public class SAPlayerImp implements SAPlayer {
    private DAOPlayer daoPlayer;
    private DAOInventory daoInventory;

    public SAPlayerImp() {
        this.daoPlayer = new DAOPlayerImp();
        this.daoInventory = new DAOInventoryImp();
    }

    @Override
    public int createPlayer(tPlayer player) {
        if(daoPlayer.readPlayer(player.getName()) == null) return daoPlayer.createPlayer(player);
        return -1;
    }

    @Override
    public tPlayer readPlayer(String name) {
        return daoPlayer.readPlayer(name);
    }

    @Override
    public int updatePlayer(tPlayer player) {
        if(daoPlayer.readPlayer(player.getName()) != null) return daoPlayer.updatePlayer(player);
        return -1;
    }

    @Override
    public int deletePlayer(String name) {
        if(daoPlayer.readPlayer(name) != null) return daoPlayer.deletePlayer(name);
        return -1;
    }

    @Override
    public Collection<tPlayer> readAllPlayers() {
        return daoPlayer.readAllPlayers();
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
    public int deleteInventory(String playerName) {
        if(daoPlayer.readPlayer(playerName) != null) return daoInventory.deleteInventory(playerName);
        return -1;
    }

    @Override
    public Collection<tInventory> readAllInventories() {
        return daoInventory.readAllInventories();
    }
}
