package model.board;

import java.util.HashMap;

public class TileFactory {

    private static HashMap<String, TileInterface> hm = new HashMap<String, TileInterface>();

    public static TileInterface getGrassTile() {
        TileInterface tile;
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
    public static TileInterface getCastleTile() {
        TileInterface tile;
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

    public static TileInterface getWallTile() {
        TileInterface tile;
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