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
        if(daoGame.readGame(game.getName(), game.getBasePoints()) == null) return daoGame.createGame(game);
        return -1;
    }

    @Override
    public tGame readGame(String name, int basePoints) {
        return daoGame.readGame(name, basePoints);
    }

    @Override
    public int deleteGame(String name, int basePoints) {
        if(daoGame.readGame(name, basePoints) != null) return daoGame.deleteGame(name, basePoints);
        return -1;
    }

    @Override
    public Collection<tGame> readAllGames() {
        return daoGame.readAllGames();
    }
}
