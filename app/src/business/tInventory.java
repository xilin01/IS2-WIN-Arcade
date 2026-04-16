package business;

import java.util.HashMap;
import java.util.Map;

public class tInventory {
    private String playerName;
    private Map<tGame, Integer> games;
    private Map<tProduct, Integer> products;
    
    public tInventory(String playerName, Map<tGame, Integer> games, Map<tProduct, Integer> products) {
    	this.playerName = playerName;
    	this.games = games;
    	this.products = products;
    }
    
    public tInventory(String playerName) {
    	this(playerName, new HashMap<>(), new HashMap<>());
    }

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Map<tGame, Integer> getGames() {
		return games;
	}

	public void setGames(Map<tGame, Integer> games) {
		this.games = games;
	}

	public Map<tProduct, Integer> getProducts() {
		return products;
	}

	public void setProducts(Map<tProduct, Integer> products) {
		this.products = products;
	}
}
