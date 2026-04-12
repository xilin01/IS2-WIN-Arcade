package business;

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
}
