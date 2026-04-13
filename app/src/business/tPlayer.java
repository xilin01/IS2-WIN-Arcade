package business;

public class tPlayer extends tUser {
	private tInventory inventory;
	private int points;
	
	public tPlayer(String name, String password, boolean active, tInventory inventory, int points) {
		super(name, password, active);
		this.inventory = inventory;
		this.points = points;
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
}
