package model.pieces.typeInterface;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public interface Troop {

    @NotNull
    @NotEmpty
    String type = "Troop";

    boolean moveable = true;
    boolean attackable = true;

}
