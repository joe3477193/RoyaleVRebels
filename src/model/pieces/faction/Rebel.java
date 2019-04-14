package model.pieces.faction;

import model.pieces.Piece;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

@Guarded
public abstract class Rebel extends Piece {

    public Rebel(@NotNull @NotEmpty String name, @NotNull @NotEmpty String type, @NotNull @NotEmpty String code, @NotNegative int cp, @NotNegative int initHp, @NotNegative int attackPower, @NotNegative int moveSpeed, @NotNegative int attackRange,
                 boolean moveable, boolean attackable) {
        super(name, "Rebel", type, code, cp, initHp, attackPower, moveSpeed, attackRange, moveable, attackable);
    }
}
