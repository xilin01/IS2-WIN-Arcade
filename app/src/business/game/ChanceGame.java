package business.game;

public abstract class ChanceGame extends Game {
    protected int multiplier;

    protected ChanceGame(int basePoints, int multiplier) {
        super(basePoints);
        if(multiplier < 1) throw new IllegalArgumentException("Invalid args for Builder in ChanceGame");
        this.multiplier = multiplier;
    }

    @Override
    public int getPointsWon() {
        return basePoints * multiplier;
    }
}
