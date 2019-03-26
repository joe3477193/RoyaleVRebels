package xvy;

public class Card {
	private int hp;
	private static enum Faction{
		ROYALE, REBEL;
	}
	private static enum Type{
		TROOP(true, true), ARTILLERY(true, false), TRAP(false, false);
		final boolean attackable;
		final boolean moveable;
		
		private Type(boolean attackable, boolean moveable) {
			this.attackable = attackable;
			this.moveable = moveable;
		}
	}
	private static enum Unit{
		GENERAL(Faction.REBEL, "GA", Type.TROOP, 5, 50, 30, 1, 1), TANK(Faction.REBEL, "TA", Type.TROOP, 3, 100, 10, 1, 1),
		INFANTRY(Faction.REBEL, "IF", Type.TROOP, 1, 20, 20, 3, 1), ASSASSIN(Faction.REBEL, "AS", Type.TROOP, 3, 20, 50, 4, 3),
		ARCHER(Faction.REBEL, "AC", Type.TROOP, 3, 40, 30, 1, 6), CANNON(Faction.ROYALE, "CN", Type.ARTILLERY, 3, 50, 30, 1, 1), 
		CATAPULT(Faction.ROYALE, "CT", Type.ARTILLERY, 5, 50, 30, 1, 1), GUNNER(Faction.ROYALE, "GU", Type.TROOP, 5, 50, 30, 1, 1),
		BOULDER(Faction.ROYALE, "BD", Type.TRAP, 4, 200, 0, 0, 0);
		final Faction faction;
		final String code;
		final Type type;
		final int cp;
		final int initHp;
		final int attack;
		final int mov;
		final int range;
		
		private Unit(Faction faction, String code, Type type, int cp, int initHp, int attack, int mov, int range) {
			this.faction = faction;
			this.code = code;
			this.type = type;
			this.cp = cp;
			this.initHp = initHp;
			this.attack = attack;
			this.mov = mov;
			this.range = range;
		}
	}
	private Unit unit;
	
	public Card(Unit unit) {
		this.unit = unit;
		this.hp = unit.initHp;
	}

	public int getHp() {
		return hp;
	}
	
	public int getInitHp() {
		return unit.initHp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public boolean isAttackable() {
		return unit.type.attackable;
	}
	
	public boolean isMoveable() {
		return unit.type.moveable;
	}
	
	public String getFaction() {
		return unit.faction.toString();
	}
	
	public String getName() {
		return unit.toString();
	}
	
	public String getCode() {
		return unit.code;
	}
	
	public String getType() {
		return unit.type.toString();
	}
	
	public int getCp() {
		return unit.cp;
	}
	
	public int getAttack() {
		return unit.attack;
	}
	
	public int getMov() {
		return unit.mov;
	}
	
	public int getRange() {
		return unit.range;
	}
	
	public String[] getInfo() {
		return new String[] {unit.faction.toString(), unit.toString(), unit.type.toString()};
	}
	
	public int[] getStatus() {
		return new int[] {unit.initHp, unit.attack, unit.mov, unit.range};
	}

}
