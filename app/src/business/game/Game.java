package business.game;

import java.util.Random;

public abstract class Game {
    protected static final Random RAND = new Random();

    protected int basePoints;

    protected Game(int basePoints) {
        this.basePoints = basePoints;
    }

    public int getPointsWon() {
        return basePoints;
    }

    abstract public int play();
}
