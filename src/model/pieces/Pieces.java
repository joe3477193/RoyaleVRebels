package model.pieces;

public class Pieces {
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
		LEADER("RebelPlayer", "LD", Type.TROOP, 5, 50, 30, 1, 1),
		SCOUNDREL("RebelPlayer", "SC", Type.TROOP, 3, 100, 10, 1, 1),
		MOBSTER("RebelPlayer", "MB", Type.TROOP, 1, 20, 20, 3, 1),
		ANGRYMAN("RebelPlayer", "AG", Type.TROOP, 3, 20, 50, 4, 3),
		RASCAL("RebelPlayer", "RC", Type.TROOP, 3, 40, 30, 1, 6),
		CATAPULT("RebelPlayer", "CT", Type.ARTILLERY, 5, 50, 20, 0, 8),
		GENERAL("RoyalePlayer", "GN", Type.TROOP, 5, 50, 50, 1, 1),
		LIUTENANT("RoyalePlayer", "LT", Type.TROOP, 3, 30, 30, 2, 1),
		INFANTRY("RoyalePlayer", "IF", Type.TROOP, 5, 20, 20, 3, 1),
		BALISTA("RoyalePlayer", "BA", Type.ARTILLERY, 8, 40, 30, 0, 8),
		CANNON("RoyalePlayer", "CA", Type.ARTILLERY, 10, 50, 50, 0, 10),
		ARCHER("RoyalePlayer", "AC", Type.TROOP, 5, 50, 30, 2, 6);

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

	public Pieces(Unit unit) {
		this.unit= unit;
		this.hp = unit.initHp;
		numCount += 1;
		num = numCount;
	}
	
	public Pieces(String name) {
		switch(name) {
		case "General":
			unit= Unit.GENERAL;
			break;
		case "Liutenant":
			unit= unit.LIUTENANT;
			break;
		case "Leader":
			unit= Unit.LEADER;
			break;
		case "Scoundrel":
			unit= unit.SCOUNDREL;
			break;
		case "Rascal":
			unit= unit.RASCAL;
			break;
		case "Mobster":
			unit= unit.MOBSTER;
			break;
		case "Angryman":
			unit= unit.ANGRYMAN;
			break;
		case "Catapult":
			unit= unit.CATAPULT;
			break;
		case "Infantry":
			unit= unit.INFANTRY;
			break;
		case "Cannon":
			unit= unit.CANNON;
			break;
		case "Balista":
			unit= unit.BALISTA;
			break;
		case "Archer":
			unit= unit.ARCHER;
			break;
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