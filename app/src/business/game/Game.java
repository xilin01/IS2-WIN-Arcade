package business.game;

import java.util.Random;

public abstract class Game {
    protected static final Random RAND = new Random();

    protected String name;
    protected int basePoints;

    protected Game(String name, int basePoints) {
        this.name = name;
        this.basePoints = basePoints;
    }

    public int getPointsWon() {
        return basePoints;
    }

    abstract public int play();
}
