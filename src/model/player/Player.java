package model.player;

public abstract class Player {

    static final int REBEL_INIT_CP = 10;
    static final int ROYALE_INT_CP = 50;

    private final String faction;
    private int cp;
    private int start_cp;
    private String name;

    public Player(String name, int cp, int start_cp, String faction) {
        this.name = name;
        this.cp = cp;
        this.start_cp = start_cp;
        this.faction = faction;
    }

    public String getName() {
        return name;
    }

    public int getCP() {
        return cp;
    }

    public boolean reduceCP(int value) {
        this.cp -= value;
        return cp >= 0;
    }

    public String getFaction() {
        return faction;
    }
}
