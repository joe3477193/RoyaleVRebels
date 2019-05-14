/*package model.pieces;

import java.util.Hashtable;

public class PieceCache {
    private static Hashtable<String, Piece> pieceMap  = new Hashtable<String, Piece>();

    public static Piece getPiece(String pieceId) {
        Piece cachedPiece = pieceMap.get(pieceId);
        return (Piece) cachedPiece.clone();
    }

    // for each shape run database query and create piece
    // pieceMap.put(pieceKey, piece);

    public static void loadCache() {
        Piece royale = new Royale();
        royale.setId("1");
        pieceMap.put(royale.getId(),royale);

        Piece rebel = new Rebel();
        rebel.setId("2");
        rebelMap.put(rebel.getId(),rebel);
    }
}
*/