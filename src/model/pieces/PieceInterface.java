package model.pieces;

public interface PieceInterface {

    String getName();

    String getFaction();

    String getType();

    String getCode();

    int getCp();

    int getInitHp();

    int getHp();

    int getInitAp();

    int getAttackPower();

    void setAttackPower(int attackPower);

    int getInitSpeed();

    int getMoveSpeed();

    int getInitAr();

    int getAttackRange();

    int getActionRange(String actionType);

    boolean isMoveable();

    boolean isAttackable();

    boolean isOffensive();

    boolean isDefensive();

    void setHP(int hp);

    void resetStatus();

    void setOffensive();

    void setDefensive();

    boolean isActionValid(int rowdiff, int tilediff, String actionType);

    void attackedBy(int attack);

    boolean isDead();


}

