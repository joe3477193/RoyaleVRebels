package model;

public abstract class Player {

    private int cp;
    private int start_cp;
    private String name;
    private final String type;

    public Player(String name, int cp, int start_cp, String type){
        this.name= name;
        this.cp= cp;
        this.start_cp= start_cp;
        this.type= type;
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
    public String getType(){
        return type;
    }


}
