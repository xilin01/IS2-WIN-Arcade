package business.packages;

import java.util.Map;
import business.game.tGame;

public class tGamesPackage {
    private String name;
    private double price;
    private Map<tGame, Integer> games;

    public tGamesPackage(String name, double price, Map<tGame, Integer> games) {
    	if(name == null || name.trim().isEmpty() || price < 0 || games == null) throw new IllegalArgumentException("Invalid args for Builder in tGamesPackage");
        this.name = name;
        this.price = price;
        this.games = games;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	if(name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Invalid arg for setName in tGamesPackage");
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
    	if(price < 0) throw new IllegalArgumentException("Invalid arg for setPrice in tGamesPackage");
        this.price = price;
    }

    public Map<tGame, Integer> getGames() {
        return games;
    }

    public void setGames(Map<tGame, Integer> games) {
    	if(games == null) throw new IllegalArgumentException("Invalid arg for setGames in tGamesPackage");
        this.games = games;
    }
}
