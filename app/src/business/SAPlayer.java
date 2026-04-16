package business;

import java.util.Collection;

public interface SAPlayer {

	int createPlayer(tPlayer player);
    tPlayer readPlayer(String name);
    int updatePlayer(tPlayer player);
    int deletePlayer(String name);
    Collection<tPlayer> readAllPlayers();
}
