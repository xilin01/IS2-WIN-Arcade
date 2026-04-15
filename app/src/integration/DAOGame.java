package integration;

import java.util.Collection;
import business.tGame;

public interface DAOGame {
	
    int createGame(tGame game);
    tGame readGame(String name, int basePoints);
    int deleteGame(String name, int basePoints);
    Collection<tGame> readAllGames();
}
