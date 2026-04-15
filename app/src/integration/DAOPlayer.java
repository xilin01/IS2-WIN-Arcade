package integration;

import java.util.Collection;
import business.tPlayer;

public interface DAOPlayer {
	
    int createPlayer(tPlayer player);
    tPlayer readPlayer(String name);
    int updatePlayer(tPlayer player);
    int deletePlayer(String name);
    Collection<tPlayer> readAllPlayers();
}
