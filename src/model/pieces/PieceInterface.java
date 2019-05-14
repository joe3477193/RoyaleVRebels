package model.pieces;

public interface PieceInterface {

    boolean isDead();

    String getFaction();

    boolean isMoveable();

    boolean isAttackable();

    int getMoveSpeed();

    int getAttackRange();

    int getAttackPower();

    int getHp();

    String getName();

    String getType();

    void setAttackPower(int attackPower);

    void setHP(int hp);

    void setOffensive();

    void setDefensive();
}

