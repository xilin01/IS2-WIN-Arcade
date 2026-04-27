package business.player;

import java.util.Collection;
import integration.player.DAOPlayer;
import integration.player.DAOPlayerImp;

public class SAPlayerImp implements SAPlayer {
    private DAOPlayer daoPlayer;

    public SAPlayerImp() {
        this.daoPlayer = new DAOPlayerImp();
    }

    @Override
    public int createPlayer(tPlayer player) {
        return daoPlayer.createPlayer(player);
    }

    @Override
    public int createPlayer(String name, String password) {
        return createPlayer(new tPlayer(name, password, true, new tPlayer.tInventory(name), 0));
    }

    @Override
    public tPlayer readPlayer(String name) {
        return daoPlayer.readPlayer(name);
    }

    @Override
    public int updatePlayer(tPlayer player) {
        return daoPlayer.updatePlayer(player);
    }

    @Override
    public int deletePlayer(String name) {
        return daoPlayer.deletePlayer(name);
    }

    @Override
    public Collection<tPlayer> readAllPlayers() {
        return daoPlayer.readAllPlayers();
    }
}
