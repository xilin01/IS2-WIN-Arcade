package business;

public class tPlayer extends tUser {
	private int points;
	private int gamesPlayed;
	
	public tPlayer(String name, String password, boolean active, int points, int gamesPlayed) {
		super(name, password, active);
		this.points = points;
		this.gamesPlayed = gamesPlayed;
	}
	
	public tPlayer(String name, String password) {
		this(name, password, true, 0, 0);
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
