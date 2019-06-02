package model.piece.AbtractPiece;

public interface PieceInterface {

    String getName();

    String getFaction();

    int getCp();

    int getInitHp();

    int getHp();

    void setHP(int hp);

    int getInitDefence();

    int getDefence();

    void setDefence(int defence);

    void buffDefence();

    void nerfDefence();

    int getInitAttackPower();

    int getAttackPower();

    void setAttackPower(int attackPower);

    void buffAttackPower();

    void nerfAttackPower();

    int getInitMoveSpeed();

    int getMoveSpeed();

    void setMoveSpeed(int moveSpeed);

    int getInitAttackRange();

    int getAttackRange();

    void setAttackRange(int attackRange);

    int getActionRange(String actionType);

    boolean isMoveable();

    boolean isAttackable();

    boolean isOffensive();

    boolean isDefensive();

    void resetMode();

    boolean isActionValid(int rowDiff, int colDiff, String actionType);

    void attackedBy(int attack);

    boolean isDead();
}

