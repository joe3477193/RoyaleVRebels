package model;

public abstract class Player {

    int start_cp = 30; // temp for start
    public int start_hp = 100; // temp for start

    public abstract String getName();


    public abstract int getStartCP();


    public abstract int getCP();
    

    public abstract void setCP(int cp);
    
    public abstract String getType();

	public int start_hp() {
		// TODO Auto-generated method stub
		return start_hp;
	}



}
