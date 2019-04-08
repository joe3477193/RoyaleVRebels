package model;

public class Piece {
	private int hp;
	// total num of pieces on board
	private final int num;
	private static int numCount;
	static enum Type{
        TROOP(true, true), ARTILLERY(true, false), TRAP(false, false);
		final boolean attackable;
		final boolean moveable;

		private Type(boolean attackable, boolean moveable) {
			this.attackable = attackable;
			this.moveable = moveable;
		}
	}
	static enum Unit{
		LEADER("Rebel", "LD", Type.TROOP, 5, 50, 30, 1, 1),
		SCOUNDREL("Rebel", "SC", Type.TROOP, 3, 100, 10, 1, 1),
		MOBSTER("Rebel", "MB", Type.TROOP, 1, 20, 20, 3, 1),
		ANGRYMAN("Rebel", "AG", Type.TROOP, 3, 20, 50, 4, 3),
		SPEARER("Rebel", "SP", Type.TROOP, 3, 40, 30, 1, 6),
		CATAPULT("Rebel", "CA", Type.ARTILLERY, 5, 50, 20, 1, 8),
		GENERAL("Royale", "GN", Type.TROOP, 5, 50, 50, 1, 1),
		LIUTENANT("Royale", "LT", Type.TROOP, 3, 30, 30, 2, 1),
		INFANTRY("Royale", "IF", Type.TROOP, 5, 20, 20, 3, 1),
		BALISTA("Royale", "BA", Type.ARTILLERY, 8, 40, 30, 1, 8),
		CANNON("Royale", "CA", Type.ARTILLERY, 10, 50, 50, 1, 10),
		ARCHER("Royale", "AC", Type.TROOP, 5, 50, 30, 2, 6);

		final String faction;
		final String code;
		final Type type;
		final int cp;
		final int initHp;
		final int attack;
		final int mov;
		final int range;

		private Unit(String faction, String code, Type type, int cp, int initHp, int attack, int mov, int range) {
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

	public Piece(Unit unit) {
		this.unit = unit;
		this.hp = unit.initHp;
		numCount += 1;
		num = numCount;
	}
	
	public Piece(String name) {
		if(name.equals("general")) {
		unit= Unit.GENERAL;	
		}
		this.hp = unit.initHp;
		numCount += 1;
		num = numCount;
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
		return unit.faction;
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

	public boolean isDead() {
		if (hp<= 0) {
			return true;
		}
		return false;
	}

	public void attackedBy(int attack) {
		hp-=attack;
	}

	public int getNum() {
	    return num;
	}
}
