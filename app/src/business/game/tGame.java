package business.game;

import java.util.Objects;

public class tGame {
	public static final String ROULETTE = "Ruleta";
    public static final String BLACKJACK = "Blackjack";

    private String name;
    private int basePoints;

    public tGame(String name, int basePoints) {
    	if(name == null || name.trim().isEmpty() || basePoints < 0) throw new IllegalArgumentException("Invalid args for Builder in tGame");
        this.name = name;
        this.basePoints = basePoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	if(name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Invalid arg for setName in tGame");
        this.name = name;
    }

    public int getBasePoints() {
        return basePoints;
    }

    public void setBasePoints(int basePoints) {
    	if(basePoints <= 0) throw new IllegalArgumentException("Invalid arg for setBasePoints in tGame");
        this.basePoints = basePoints;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        tGame game = (tGame)obj;
        return name.equals(game.name) && basePoints == game.basePoints;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, basePoints);
    }
}
