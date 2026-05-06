package business.player;

import java.util.HashMap;
import java.util.Map;
import business.auth.AuthResult;
import business.game.tGame;
import business.product.tProduct;
import business.user.tUser;

public class tPlayer extends tUser {
    public static class tInventory {
        private String playerName;
        private Map<tGame, Integer> games;
        private Map<tProduct, Integer> products;

        public tInventory(String playerName, Map<tGame, Integer> games, Map<tProduct, Integer> products) {
        	if(playerName == null || playerName.trim().isEmpty() || games == null || products == null) throw new IllegalArgumentException("Invalid args for Builder in tInventory");
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
        	if(playerName == null || playerName.trim().isEmpty()) throw new IllegalArgumentException("Invalid arg for setPlayerName in tInventory");
            this.playerName = playerName;
        }

        public Map<tGame, Integer> getGames() {
            return games;
        }

        public void setGames(Map<tGame, Integer> games) {
        	if(games == null) throw new IllegalArgumentException("Invalid arg for setGames in tInventory");
            this.games = games;
        }

        public Map<tProduct, Integer> getProducts() {
            return products;
        }

        public void setProducts(Map<tProduct, Integer> products) {
        	if(products == null) throw new IllegalArgumentException("Invalid arg for setProducts in tInventory");
            this.products = products;
        }
    }

    private tInventory inventory;
    private int points;

    public tPlayer(String name, String password, boolean active, tInventory inventory, int points) {
        super(name, password, active);
        if(points < 0) throw new IllegalArgumentException("Invalid args for Builder in tPlayer");
        this.inventory = inventory;
        this.points = points;
    }

    public tInventory getInventory() {
        return inventory;
    }

    public void setInventory(tInventory inventory) {
    	if(inventory == null) throw new IllegalArgumentException("Invalid arg for setInventory in tPlayer");
        this.inventory = inventory;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
    	if(points < 0) throw new IllegalArgumentException("Invalid arg for setPoints in tPlayer");
        this.points = points;
    }
    
    @Override
    public tPlayer asPlayer() {
        return this;
    }

    @Override
    public AuthResult toAuthResult() {
        return AuthResult.playerOk(name);
    }
}
