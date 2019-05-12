package model.board;

import java.util.HashMap;

public class TileFactory {

    private static HashMap<String, Tile> hm = new HashMap<String, Tile>();

    public static Tile getGrassTile() {
        Tile tile;
        String type = "GrassTile";

        if (hm.containsKey(type)) {
            tile = hm.get(type);
        }
        else {
            tile = new GrassTile();
            System.out.println("Created new grass tile");
        }

        hm.put(type, tile);
        return tile;
    }
    public static Tile getCastleTile() {
        Tile tile;
        String type = "CastleTile";

        if (hm.containsKey(type)) {
            tile = hm.get(type);
        }
        else {
            tile = new CastleTile();
            System.out.println("Created new castle tile");
        }

        hm.put(type, tile);
        return tile;
    }

    public static Tile getWallTile() {
        Tile tile;
        String type = "WallTile";

        if (hm.containsKey(type)) {
            tile = hm.get(type);
        }
        else {
            tile = new WallTile();
            System.out.println("Created new wall tile");
        }

        hm.put(type, tile);
        return tile;
    }
}