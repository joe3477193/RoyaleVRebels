package model.piece.AbtractPiece;

public interface PieceInterface {

    String getName();

    String getFaction();

    String getType();

    String getCode();

    int getCp();

    int getInitHp();

    int getHp();

    int getInitDefence();

    int getDefence();

    void setDefence(int defence);

    int getInitAttackPower();

    int getAttackPower();

    void setAttackPower(int attackPower);

    int getInitMoveSpeed();

    int getMoveSpeed();

    int getInitAttackRange();

    int getAttackRange();

    void setAttackRange(int attackRange);

    int getActionRange(String actionType);

    boolean isMoveable();

    boolean isAttackable();

    boolean isOffensive();

    boolean isDefensive();

    void setHP(int hp);

    void resetMode();

    void setOffensive();

    void setDefensive();

    boolean isActionValid(int rowdiff, int tilediff, String actionType);

    void attackedBy(int attack);

    boolean isDead();


}

