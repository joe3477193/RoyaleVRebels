package model.pieces;

public abstract class Piece {

    String faction;
    String typeOf;
    String code;
    int cp;
    int initHp;
    int hp;
    int attack;
    int mov;
    int range;
    boolean moveableOf;
    boolean attackableOf;

    Piece() {
    }

    public boolean isDead() {
        if (hp <= 0) {
            return true;
        }
        return false;
    }

    public void attackedBy(int attack) {
        hp-=attack;
    }

    public String getFaction() {
        return faction;
    }

    public boolean isMoveable() {
        return moveableOf;
    }

    public boolean isAttackable() {
        return attackableOf;
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
}
