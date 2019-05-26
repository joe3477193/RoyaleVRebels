package model.gameEngine;

import java.io.Serializable;

public class GrassTile implements Serializable, TileInterface {
    private static final long serialVersionUID = 3L;

    private int row, col;

    public GrassTile() {
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
