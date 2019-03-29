package xvy.Model;

public class Player {
	private String name;
	private int id;
	private int start_cp=30;		//temp for start
	private int start_hp = 100;		//temp for start
	private int remaining_cp;
	private int remaining_hp;
	
	
	public Player(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public int getStartCP() {
		return start_cp;
	}
	
	public int getStartHP() {
		return start_hp;
	}
	
	public int getCP() {
		return remaining_cp;
	}
	
	public int getHP() {
		return remaining_hp;
	}
	
	public void setCP(int cp) {
		this.remaining_cp = cp;
	}
	
	public void setHP(int hp) {
		this.remaining_hp = hp;
	}
	
}
