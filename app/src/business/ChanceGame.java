package business;

public abstract class ChanceGame extends Game {
	protected int multiplier;
	
	protected ChanceGame(String name, int basePoints, int multiplier) {
		super(name, basePoints);
		this.multiplier = multiplier;
	}
	
	@Override 
	public int getPointsWon() {
		return basePoints * multiplier;
	}
}
