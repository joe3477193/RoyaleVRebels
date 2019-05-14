package model.pieces.decorator;

import model.pieces.PieceInterface;
import model.pieces.Piece;

public abstract class PieceInterfaceDecorator implements PieceInterface {

    protected PieceInterface piece;

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

    public PieceInterfaceDecorator(PieceInterface piece) {
        this.piece = piece;
    }

    public boolean isDead() {
        if (hp <= 0) {
            hp = 0;
            return true;
        }
        return false;
    }

    public void attackedBy( int attack) {
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

    public boolean isActionValid( int rowdiff,  int tilediff,   String actionType) {
        int range= getActionRange(actionType);
        return rowdiff==0 && range >= tilediff || tilediff==0 && range>=rowdiff;
    }

    public int getActionRange(  String actionType){
        if(actionType.equals("moveSpeed")){
            return moveSpeed;
        }
        else if (actionType.equals("attackRange")){
            return attackRange;
        }
        return 0;
    }

    public String getType(){
        return type;
    }

    public void setAttackPower(int attackPower) {
        if (attackPower < 0) {
            this.attackPower = 0;
        }
        else {
            this.attackPower = attackPower;
        }
    }

    public void setHP(int hp) {
        if (hp < 0) {
            this.hp = 0;
        }
        else {
            this.hp = hp;
        }
    }

    public void setOffensive(){}

    public void setDefensive(){}
}

