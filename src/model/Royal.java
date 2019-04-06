package model;

public class Royal extends Player {
	
	String name; 
	final String type = "ROYAL";
	int CP, HP;
	int startCP = 40;
	
	public Royal(String name) {
		this.name= name;
		CP = startCP;
		
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public int getStartCP() {
		// TODO Auto-generated method stub
		return startCP;
	}

	@Override
	public int getCP() {
		// TODO Auto-generated method stub
		return CP;
	}


	public int getHP() {
		// TODO Auto-generated method stub
		return HP;
	}

	@Override
	public void setCP(int cp) {
		this.CP = cp;
	}

	
	public void setHP(int hp) {
		this.HP=hp;
		
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
