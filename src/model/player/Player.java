package model.player;

import java.util.Random;

public abstract class Player {

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

    public int getStartCP() {
        return start_cp;
    }

    public int getCP() {
        return cp;
    }

    public void reduceCP(int value) {
        this.cp -= value;
    }

    public String getFaction() {
        return faction;
    }

    public void increaseCP() {
        Random random = new Random();

        // increaseCP by 1 or 2 randomly
        int enhancement = random.nextInt(2) + 1;
        this.cp += enhancement;
    }
}
