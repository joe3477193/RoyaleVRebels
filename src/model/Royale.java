package model;

public class Royale extends Player {
	
	private int hp;
	
	public Royale(String name){
		super(name, 40,40, "ROYALE");
		hp= 100;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp=hp;
	}
}