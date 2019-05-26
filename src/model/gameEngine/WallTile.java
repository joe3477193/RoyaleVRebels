package model.gameEngine;

import java.io.Serializable;

public class WallTile implements Serializable, TileInterface {
    private static final long serialVersionUID = 3L;

    private int row, col;

    public WallTile () {
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
