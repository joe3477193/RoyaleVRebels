package model.pieces;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

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

    public Piece(@NotNull @NotEmpty String name, @NotNull @NotEmpty String faction, @NotNull @NotEmpty String type, @NotNull @NotEmpty String code, @NotNegative int cp, @NotNegative int initHp, @NotNegative int attackPower, @NotNegative int moveSpeed, @NotNegative int attackRange,
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
            hp = 0;
            return true;
        }
        return false;
    }

    public void attackedBy(@NotNegative int attack) {
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

    public boolean isActionValid(@NotNegative int rowdiff, @NotNegative int tilediff, @NotNull @NotEmpty String actionType) {
        int range= getActionRange(actionType);
        return rowdiff==0 && range >= tilediff || tilediff==0 && range>=rowdiff;
    }

    public int getActionRange(@NotNull @NotEmpty String actionType){
        if(actionType.equals("moveSpeed")){
            return moveSpeed;
        }
        else if (actionType.equals("attackRange")){
            return attackRange;
        }
        return 0;
    }
}
