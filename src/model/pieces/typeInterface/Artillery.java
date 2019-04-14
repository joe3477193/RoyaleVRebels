package model.pieces.typeInterface;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

public interface Artillery {

    @NotNull
    @NotEmpty
    String type = "Artillery";

    boolean moveable = false;
    boolean attackable = true;

}
