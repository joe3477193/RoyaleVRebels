package model.pieces;

public abstract class Piece {

    String name;
    private String faction;
    private String type;
    private String code;
    private int cp;
    private int initHp;
    private int hp;
    private int attackPower;
    private int moveSpeed;
    private int attackRange;
    private boolean moveable;
    private boolean attackable;

    public Piece(String name, String faction, String type, String code, int cp, int initHp, int attackPower, int moveSpeed, int attackRange,
                 boolean moveable, boolean attackable) {
        this.name= name;
        this.faction= faction;
        this.type= type;
        this.code= code;
        this.cp= cp;
        this.initHp= initHp;
        this.hp= initHp;
        this.attackPower = attackPower;
        this.moveSpeed = moveSpeed;
        this.attackRange = attackRange;
        this.moveable= moveable;
        this.attackable= attackable;
    }

    public boolean isDead() {
        if (hp <= 0) {
            return true;
        }
        return false;
    }

    public void attackedBy(int attack) {
        hp-=attack;
        if (hp <= 0) {
            hp=0;
        }
    }

    public String getFaction() {
        return faction;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public boolean isAttackable() {
        return attackable;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getHp() {
        return hp;
    }

    public  String getName(){
        return name;
    }

    public boolean isMoveValid(int difference, String movType) {
        boolean valid= false;
        if (movType.equals("moveSpeed")) {
            valid= moveSpeed >= difference;
        } else if (movType.equals("attackRange")) {
            valid= attackRange >= difference;
        }
        return valid;
    }
}
