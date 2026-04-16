package business;

import java.util.Map;

public class tGamesPackage {
	private String name;
	private double price;
	private Map<tGame, Integer> games;
	
	public tGamesPackage(String name, double price, Map<tGame, Integer> games) {
		this.name = name;
		this.price = price;
		this.games = games;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public Map<tGame, Integer> getGames() {
		return games;
	}
	
	public void setGames(Map<tGame, Integer> games) {
		this.games = games;
	}
}
