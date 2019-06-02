package model.tile;

import java.util.HashMap;

public class TileFactory {

    private static final String GRASS_TILE = "GrassTile";
    private static final String CASTLE_TILE = "CastleTile";
    private static final String WALL_TILE = "WallTile";

    private static HashMap<String, TileInterface> tiles = new HashMap<>();

    public static TileInterface getTile(String type) {
        TileInterface tile = tiles.get(type);
        if (tiles.containsKey(type)) {
            return tile;
        }
        else if (type.equals(GRASS_TILE)) {
            tile = new GrassTile();
        }
        else if (type.equals(CASTLE_TILE)) {
            tile = new CastleTile();
        }
        else if (type.equals(WALL_TILE)) {
            tile = new WallTile();
        }
        tiles.put(type, tile);
        return tile;
    }
}
