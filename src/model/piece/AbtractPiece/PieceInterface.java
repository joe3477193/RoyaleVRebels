package model.piece.AbtractPiece;

public interface PieceInterface {

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

    void buffMoveSpeed();

    void nerfMoveSpeed();

    int getInitAttackRange();

    int getAttackRange();

    void setAttackRange(int attackRange);

    void buffAttackRange();

    void nerfAttackRange();

    int getActionRange(String actionType);

    boolean isMoveable();

    boolean isAttackable();

    boolean isOffensive();

    boolean isDefensive();

    void resetMode();

    void setOffensive();

    void setDefensive();

    boolean isActionValid(int rowDiff, int colDiff, String actionType);

    void attackedBy(int attack);

    boolean isDead();
    
}

