package model.board;

import java.util.HashMap;

public class TileFactory {

    private static HashMap<String, TileInt> hm = new HashMap<String, TileInt>();

    public static TileInt getTile(String type) {
        TileInt tile = null;

        if (hm.containsKey(type)) {
            tile = hm.get(type);
        }
        else if (type.equals("GrassTile")) {
            tile = new Tile();
        }
        else if (type.equals("WallTile")) {
            tile = new WallTile();
        }
        else if (type.equals("CastleTile")) {
            tile = new CastleTile();
        }

        hm.put(type, tile);
        return tile;
    }
}
