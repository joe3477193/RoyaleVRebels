package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

public abstract class PieceInterfaceDecorator implements PieceInterface {

    protected PieceInterface piece;
    boolean isOffensive;
    boolean isDefensive;
    private String name;
    private String faction;
    private String type;
    private String code;
    private int cp;
    private int initHp;
    private int hp;
    private int initAttackPower;
    private int attackPower;
    private int initMoveSpeed;
    private int moveSpeed;
    private int initAttackRange;
    private int attackRange;
    private boolean moveable;
    private boolean attackable;

    public PieceInterfaceDecorator(PieceInterface piece) {
        this.piece = piece;
        name = piece.getName();
        faction = piece.getFaction();
        type = piece.getType();
        code = piece.getCode();
        cp = piece.getCp();
        initHp = piece.getInitHp();
        hp = piece.getHp();
        initAttackPower = piece.getInitAttackPower();
        attackPower = piece.getAttackPower();
        initMoveSpeed = piece.getInitMoveSpeed();
        moveSpeed = piece.getMoveSpeed();
        initAttackRange = piece.getInitAttackRange();
        attackRange = piece.getAttackRange();
        moveable = piece.isMoveable();
        attackable = piece.isAttackable();
        isOffensive = false;
        isDefensive = false;
    }

    public String getName() {
        return name;
    }

    public String getFaction() {
        return faction;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public int getCp() {
        return cp;
    }

    public int getInitHp() {
        return initHp;
    }

    public int getHp() {
        return hp;
    }

    public int getInitAttackPower() {
        return initAttackPower;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        if (attackPower < 0) {
            this.attackPower = 0;
        } else {
            this.attackPower = attackPower;
        }
    }

    public int getInitMoveSpeed() {
        return initMoveSpeed;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int speed) {
        moveSpeed = speed;
    }

    public int getInitAttackRange() {
        return initAttackRange;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getActionRange(String actionType) {
        if (actionType.equals("moveSpeed")) {
            return moveSpeed;
        } else if (actionType.equals("attackRange")) {
            return attackRange;
        }
        return 0;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public boolean isAttackable() {
        return attackable;
    }

    public boolean isOffensive() {
        return isOffensive;
    }

    public boolean isDefensive() {
        return isDefensive;
    }

    public void setHP(int hp) {
        if (hp < 0) {
            this.hp = 0;
        } else {
            this.hp = hp;
        }
    }

    public void resetMode() {
    }

    public void setOffensive() {
    }

    public void setDefensive() {
    }

    public boolean isActionValid(int rowdiff, int tilediff, String actionType) {
        int range = getActionRange(actionType);
        return rowdiff == 0 && range >= tilediff || tilediff == 0 && range >= rowdiff;
    }

    public void attackedBy(int attack) {
        hp -= attack;
        if (hp <= 0) {
            hp = 0;
        }
    }

    public boolean isDead() {
        if (hp <= 0) {
            hp = 0;
            return true;
        }
        return false;
    }
}
