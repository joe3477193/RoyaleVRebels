package model.pieces;

public abstract class Piece {

    String name;
    private String faction;
    private String type;
    private String code;
    private int cp;
    private int initHp;
    private int hp;
    private int attack;
    private int mov;
    private int range;
    private boolean moveable;
    private boolean attackable;

    public Piece(String name, String faction, String type, String code, int cp, int initHp, int attack, int mov, int range,
                 boolean moveable, boolean attackable) {
        this.name= name;
        this.faction= faction;
        this.type= type;
        this.code= code;
        this.cp= cp;
        this.initHp= initHp;
        this.hp= initHp;
        this.attack= attack;
        this.mov= mov;
        this.range= range;
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

    public int getMov() {
        return mov;
    }

    public int getRange() {
        return range;
    }

    public int getAttack() {
        return attack;
    }

    public int getHp() {
        return hp;
    }

    public  String getName(){
        return name;
    }

    public boolean isMoveValid(int difference, String movType) {
        boolean valid= false;
        if (movType.equals("mov")) {
            valid= mov >= difference;
        } else if (movType.equals("range")) {
            valid= range >= difference;
        }
        return valid;
    }
}
