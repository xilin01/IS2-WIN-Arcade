package business.game;

import java.util.Collection;

public interface SAGame {
	
    int createGame(tGame game);
    tGame readGame(String name, int basePoints);
    int deleteGame(String name, int basePoints);
    Collection<tGame> readAllGames();
}
