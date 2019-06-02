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

    public void reduceCP(int value) {
         cp -= value;
    }

    public boolean isEnoughCP(int value){
        return cp - value > 0;
    }

    public String getFaction() {
        return faction;
    }

    // Forbid increasing CP
    public void increaseCP() {
//        Random random = new Random();
//        // increaseCP by 1 or 2 randomly
//        int enhancement = random.nextInt(2) + 1;
//        this.cp += enhancement;
    }
}
