package model.piece.abstractType;

import model.piece.abstractType.property.Attackable;
import model.piece.abstractType.property.Moveable;

public interface Troop extends Moveable, Attackable {

    String type = "Troop";

//    boolean moveable = true;
//    boolean attackable = true;
}
