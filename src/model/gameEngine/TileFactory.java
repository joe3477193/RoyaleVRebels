package model.gameEngine;

import java.util.HashMap;

class TileFactory {

    private static HashMap<String, TileInterface> tiles = new HashMap<>();

    static TileInterface getTile(String type) {
        TileInterface tile = tiles.get(type);
        if (tiles.containsKey(type)) {
            return tile;
        } else if (type.equals("GrassTile")) {
            tile = new GrassTile();
        } else if (type.equals("CastleTile")) {
            tile = new CastleTile();
        } else if (type.equals("WallTile")) {
            tile = new WallTile();
        }
        tiles.put(type, tile);
        return tile;
    }
}
