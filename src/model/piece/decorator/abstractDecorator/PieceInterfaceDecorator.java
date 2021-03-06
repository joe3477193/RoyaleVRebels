package model.piece.decorator.abstractDecorator;

import model.piece.AbtractPiece.PieceInterface;

public abstract class PieceInterfaceDecorator implements PieceInterface {

    private static final String MOVE_TYPE = "move";
    private static final String ATTACK_TYPE = "attack";

    protected PieceInterface piece;
    protected boolean isOffensive;
    protected boolean isDefensive;
    private String name;
    private String faction;
    private int cp;
    private int initHp;
    private int hp;
    private int initDefence;
    private int defence;
    private int initAttackPower;
    private int attackPower;
    private int initMoveSpeed;
    private int moveSpeed;
    private int initAttackRange;
    private int attackRange;
    private boolean moveable;
    private boolean attackable;

    protected PieceInterfaceDecorator(PieceInterface piece) {
        this.piece = piece;
        name = piece.getName();
        faction = piece.getFaction();
        cp = piece.getCp();
        initHp = piece.getInitHp();
        hp = piece.getHp();
        initDefence = piece.getInitDefence();
        defence = piece.getDefence();
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

    public int getCp() {
        return cp;
    }

    public int getInitHp() {
        return initHp;
    }

    public int getHp() {
        return hp;
    }

    public void setHP(int hp) {
        if (hp < 0) {
            this.hp = 0;
        }
        else {
            this.hp = hp;
        }
    }

    public int getInitDefence() {
        return initDefence;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        if (defence < 0) {
            this.defence = 0;
        }
        else {
            this.defence = defence;
        }
    }

    public void buffDefence() {
    }

    public void nerfDefence() {
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
        }
        else {
            this.attackPower = attackPower;
        }
    }

    public void buffAttackPower() {
    }

    public void nerfAttackPower() {
    }

    public int getInitMoveSpeed() {
        return initMoveSpeed;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        if (moveSpeed < 0) {
            this.moveSpeed = 0;
        }
        else {
            this.moveSpeed = moveSpeed;
        }
    }

    public int getInitAttackRange() {
        return initAttackRange;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        if (attackRange < 0) {
            this.attackRange = 0;
        }
        else {
            this.attackRange = attackRange;
        }
    }

    public int getActionRange(String actionType) {
        if (actionType.equals(MOVE_TYPE)) {
            return moveSpeed;
        }
        else if (actionType.equals(ATTACK_TYPE)) {
            return attackRange;
        }
        else {
            return 0;
        }
    }

    public boolean isMoveable() {
        return moveable;
    }

    public boolean isAttackable() {
        return attackable;
    }

    public void resetMode() {}

    // check if the piece is offensive or not
    public boolean isOffensive() {
        if (attackPower > initAttackPower) {
            isOffensive = true;
        }
        return isOffensive;
    }

    // check if the piece is defensive or not
    public boolean isDefensive() {
        if (defence > initDefence) {
            isDefensive = true;
        }
        return isDefensive;
    }

    // check if action of movement or attack is allowed
    public boolean isActionValid(int rowDiff, int colDiff, String actionType) {
        int range = getActionRange(actionType);
        return rowDiff == 0 && range >= colDiff || colDiff == 0 && range >= rowDiff;
    }

    // damage dealt on the piece from another piece
    public void attackedBy(int attack) {
        // true damage = attacking piece's attack power - attacked piece's defence
        int trueDamage = attack - defence;
        if (trueDamage > 0) {
            hp -= trueDamage;
        }
        if (hp <= 0) {
            hp = 0;
        }
    }

    // check if the piece is dead, remove the piece on the board
    public boolean isDead() {
        if (hp <= 0) {
            hp = 0;
            return true;
        }
        return false;
    }
}
