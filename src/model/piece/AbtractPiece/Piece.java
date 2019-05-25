package model.piece.AbtractPiece;

public abstract class Piece implements PieceInterface {

    private boolean isOffensive;
    private boolean isDefensive;
    private String name;
    private String faction;
    private String type;
    private String code;
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

    public Piece(String name, String faction, String type, String code, int cp, int hp, int defence, int attackPower, int moveSpeed, int attackRange, boolean moveable, boolean attackable) {
        this.name = name;
        this.faction = faction;
        this.type = type;
        this.code = code;
        this.cp = cp;
        this.initHp = hp;
        this.hp = hp;
        this.initDefence = defence;
        this.defence = defence;
        this.initAttackPower = attackPower;
        this.attackPower = attackPower;
        this.initMoveSpeed = moveSpeed;
        this.moveSpeed = moveSpeed;
        this.initAttackRange = attackRange;
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

    public int getInitDefence() {
        return initDefence;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
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

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
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
    
    public void addHP(int hp) {
    	
    	this.hp += hp;
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

        int trueDamage = attack - defence;

        if (trueDamage > 0) {
            hp -= trueDamage;
        }

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
