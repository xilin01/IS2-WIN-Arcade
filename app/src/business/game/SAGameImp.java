package business.game;

import java.util.Collection;
import integration.game.DAOGame;
import integration.game.DAOGameImp;

public class SAGameImp implements SAGame {
    private DAOGame daoGame;

    public SAGameImp() {
        this.daoGame = new DAOGameImp();
    }

    @Override
    public int createGame(tGame game) {
        return daoGame.createGame(game);
    }

    @Override
    public tGame readGame(String name, int basePoints) {
        return daoGame.readGame(name, basePoints);
    }

    @Override
    public int deleteGame(String name, int basePoints) {
        return daoGame.deleteGame(name, basePoints);
    }

    @Override
    public Collection<tGame> readAllGames() {
        return daoGame.readAllGames();
    }
}
