package model.board;

import java.util.ArrayList;

class BoardRows {

    private ArrayList<Tile> tiles;

    BoardRows() {
        tiles = new ArrayList<>(Board.BOARD_COLS);

        for (int i = 0; i < Board.BOARD_COLS; i++) {
            tiles.add(new Tile());
        }
    }

    Tile getTile(int i) {
        return tiles.get(i);
    }

}