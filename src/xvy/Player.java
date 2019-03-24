package xvy;

public class Player {
	private String name;
	private int id;
	private int cp;
	private int start_cp;
	private int remaining_cp;
	private int remaining_hp;
	
	
	Player(int id, String name){
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
	
	public int getCP() {
		return remaining_cp;
	}
	
	public int getHP() {
		return remaining_hp;
	}
	
	public void setCP(int cp) {
		this.cp = cp;
	}
	
}
