package model.pieces;

public abstract class Piece implements PieceInterface {

    boolean isOffensive;
    boolean isDefensive;
    private String name;
    private String faction;
    private String type;
    private String code;
    private int cp;
    private int initHp;
    private int hp;
    private int initAp;
    private int attackPower;
    private int initSpeed;
    private int moveSpeed;
    private int initAr;
    private int attackRange;
    private boolean moveable;
    private boolean attackable;

    public Piece(String name, String faction, String type, String code, int cp, int initHp, int attackPower, int moveSpeed, int attackRange, boolean moveable, boolean attackable) {
        this.name = name;
        this.faction = faction;
        this.type = type;
        this.code = code;
        this.cp = cp;
        this.initHp = initHp;
        this.hp = initHp;
        this.initAp = attackPower;
        this.attackPower = attackPower;
        this.initSpeed = moveSpeed;
        this.moveSpeed = moveSpeed;
        this.initAr = attackRange;
        this.attackRange = attackRange;
        this.moveable = moveable;
        this.attackable = attackable;
        this.isOffensive = false;
        this.isDefensive = false;
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

    public int getInitAp() {
        return initAp;
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

    public int getInitSpeed() {
        return initSpeed;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int speed) {
        moveSpeed = speed;
    }

    public int getInitAr() {
        return initAr;
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

    public void resetStatus() {
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
