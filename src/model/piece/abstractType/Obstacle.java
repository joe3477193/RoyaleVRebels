package model.piece.abstractType;

import model.piece.abstractType.property.UnAttackable;
import model.piece.abstractType.property.UnMoveable;

public interface Obstacle extends UnMoveable, UnAttackable {

    String type = "Obstacle";

//    boolean moveable = false;
//    boolean attackable = false;
}
