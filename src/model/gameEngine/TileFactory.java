package model.gameEngine;

import java.util.HashMap;

public class TileFactory {

    private static HashMap<String, TileInterface> tiles = new HashMap<>();

    public static TileInterface getTile(String type) {
        TileInterface tile = tiles.get(type);

        if (tiles.containsKey(type)) {
            return tile;
        }
        else if (type.equals("GrassTile")){
            tile = new GrassTile();
            System.out.println("Created new grass flyweight");
        }
        else if (type.equals("CastleTile")){
            tile = new CastleTile();
            System.out.println("Created new castle flyweight");
        }
        else if (type.equals("WallTile")){
            tile = new WallTile();
            System.out.println("Created new wall flyweight");
        }


        tiles.put(type, tile);
        return tile;
    }
}
