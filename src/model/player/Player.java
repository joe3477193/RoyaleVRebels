package model.player;

public abstract class Player {

    static final int REBEL_INIT_CP = 80;
    static final int ROYALE_INT_CP = 50;

    private final String faction;
    private int cp;
    private String name;

    public Player(String name, int cp, String faction) {
        this.name = name;
        this.cp = cp;
        this.faction = faction;
    }

    public String getName() {
        return name;
    }

    public int getCP() {
        return cp;
    }

    public void reduceCP(int value) {
        cp -= value;
    }

    public boolean isEnoughCP(int value) {
        return cp - value >= 0;
    }

    public String getFaction() {
        return faction;
    }
}
