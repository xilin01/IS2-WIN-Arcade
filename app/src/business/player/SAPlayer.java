package business.player;

import java.util.Collection;

public interface SAPlayer {
	
    int createPlayer(tPlayer player);
    int createPlayer(String name, String password);
    tPlayer readPlayer(String name);
    int updatePlayer(tPlayer player);
    int deletePlayer(String name);
    Collection<tPlayer> readAllPlayers();
}
