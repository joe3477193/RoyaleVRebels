package model.board;

import java.util.ArrayList;

class BoardRows {

    private ArrayList<Tile> tiles;

    BoardRows() {
        tiles = new ArrayList<>(GameEngineFacade.BOARD_COLS);

        for (int i = 0; i < GameEngineFacade.BOARD_COLS; i++) {
            tiles.add(new Tile());
        }
    }

    Tile getTile( int i) {
        return tiles.get(i);
    }

}