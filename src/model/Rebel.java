package model;

public class Rebel extends Player {
	
	String name; 

	int CP;
	int startCP = 40;
	final String type = "REBEL";
	public Rebel(String name) {
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
		return 0;
	}

	@Override
	public int getCP() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCP(int cp) {
		// TODO Auto-generated method stub
		this.CP = cp;
	}


	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
