package model.piece.abstractType;

import model.piece.abstractType.property.Attackable;
import model.piece.abstractType.property.UnMoveable;

public interface Artillery extends UnMoveable, Attackable {

    String type = "Artillery";

//    boolean moveable = false;
//    boolean attackable = true;
}
