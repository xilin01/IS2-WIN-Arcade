package business;

public class tPlayer extends tUser {
	private int points;
	private int gamesPlayed;
	
	public tPlayer(String name, String password) {
		super(name, password, true);
		points = 0;
		gamesPlayed = 0;
	}
	
	public tPlayer(String name, String password, boolean active, int points, int gamesPlayed) {
		super(name, password, active);
		this.points = points;
		this.gamesPlayed = gamesPlayed;
	}

	public int getPoints() {
		return points;
	}

	public void addPoints(int points) {
		this.points += points;
	}
	
	public void removePoints(int points) {
		this.points -= points;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void addGamePlayed() {
		this.gamesPlayed++;
	}
}
