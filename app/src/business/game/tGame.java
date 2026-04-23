package business.game;

import java.util.Objects;

public class tGame {
    private String name;
    private int basePoints;

    public tGame(String name, int basePoints) {
        this.name = name;
        this.basePoints = basePoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBasePoints() {
        return basePoints;
    }

    public void setBasePoints(int basePoints) {
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
