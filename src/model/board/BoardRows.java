package model.board;

import java.util.ArrayList;

public class BoardRows {

    private ArrayList<Tile> tiles;

    public BoardRows() {
        tiles = new ArrayList<>(Board.BOARD_COLS);

        for (int i = 0; i < Board.BOARD_COLS; i++) {
            tiles.add(new Tile());
        }
    }

    public ArrayList<Tile> getRow() {
        if (tiles != null) {
            return tiles;
        } else {
            return null;
        }
    }

    public Tile getTile(int i) {
        return tiles.get(i);
    }

}