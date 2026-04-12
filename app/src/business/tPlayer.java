package business;

public class tPlayer extends tUser {
	private tInventory inventory;
	private int points;
	private int gamesPlayed;
	
	public tPlayer(String name, String password, boolean active, tInventory inventory, int points, int gamesPlayed) {
		super(name, password, active);
		this.inventory = inventory;
		this.points = points;
		this.gamesPlayed = gamesPlayed;
	}
	
	public tInventory getInventory() {
		return inventory;
	}

	public void setInventory(tInventory inventory) {
		this.inventory = inventory;
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}
}
