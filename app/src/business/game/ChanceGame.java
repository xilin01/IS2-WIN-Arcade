package business.game;

public abstract class ChanceGame extends Game {
    protected int multiplier;

    protected ChanceGame(int basePoints, int multiplier) {
        super(basePoints);
        this.multiplier = multiplier;
    }

    @Override
    public int getPointsWon() {
        return basePoints * multiplier;
    }
}
