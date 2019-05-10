package model.board;

import java.util.HashMap;

public class TileFactory {

    private static HashMap<String, TileInterface> hm = new HashMap<String, TileInterface>();

    public static TileInterface getGrassTile(int row, int col) {
        TileInterface tile = null;
        String type = "GrassTile";

        if (hm.containsKey(type)) {
            tile = hm.get(type);
        }
        else {
            tile = new Tile(row, col);
            System.out.println("Created new grass tile");
        }

        hm.put(type, tile);
        return tile;
    }
    public static TileInterface getCastleTile(int row, int col) {
        TileInterface tile = null;
        String type = "CastleTile";

        if (hm.containsKey(type)) {
            tile = hm.get(type);
        }
        else {
            tile = new CastleTile(row, col);
            System.out.println("Created new castle tile");
        }

        hm.put(type, tile);
        return tile;
    }

    public static TileInterface getWallTile(int row, int col) {
        TileInterface tile = null;
        String type = "WallTile";

        if (hm.containsKey(type)) {
            tile = hm.get(type);
        }
        else {
            tile = new WallTile(row, col);
            System.out.println("Created new wall tile");
        }

        hm.put(type, tile);
        return tile;
    }
}