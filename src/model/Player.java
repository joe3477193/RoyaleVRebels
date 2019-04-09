package model;

public abstract class Player {

    private int cp;
    private int start_cp;
    private String name;
    private final String faction;
    private boolean turn;

    public Player(String name, int cp, int start_cp, String faction){
        this.name= name;
        this.cp= cp;
        this.start_cp= start_cp;
        this.faction= faction;
    }

    public String getName(){
        return name;
    }

    public int getStartCP(){
        return start_cp;
    }

    public int getCP(){
        return cp;
    }

    public void setCP(int cp){
        this.cp= cp;
    }
    public String getFaction(){
        return faction;
    }

    
    public boolean finishedTurn() {
    	return turn;
    }
}
