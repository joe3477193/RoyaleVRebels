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
		GENERAL("Rebel", "GA", Type.TROOP, 5, 50, 30, 1, 1),
		TANK("Rebel", "TA", Type.TROOP, 3, 100, 10, 1, 1),
		INFANTRY("Rebel", "IF", Type.TROOP, 1, 20, 20, 3, 1),
		ASSASSIN("Rebel", "AS", Type.TROOP, 3, 20, 50, 4, 3),
		ARCHER("Rebel", "AC", Type.TROOP, 3, 40, 30, 1, 6),
		CANNON("Rebel", "CN", Type.ARTILLERY, 3, 50, 30, 1, 1),
		CATAPULT("Rebel", "CT", Type.ARTILLERY, 5, 50, 30, 1, 1),
		GUNNER("Rebel", "GU", Type.TROOP, 5, 50, 30, 1, 1),
		BOULDER("Rebel", "BD", Type.TRAP, 4, 200, 0, 0, 0);
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
