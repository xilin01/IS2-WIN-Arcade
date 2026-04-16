package business;

import java.util.Collection;
import integration.DAOPlayer;
import integration.DAOPlayerImp;

public class SAPlayerImp implements SAPlayer {
    private DAOPlayer daoPlayer;

    public SAPlayerImp() {
        this.daoPlayer = new DAOPlayerImp();
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
}
