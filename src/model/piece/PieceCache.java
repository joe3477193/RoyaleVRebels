package model.piece;

import model.piece.AbtractPiece.Piece;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

public class PieceCache {

    private static Hashtable<String, Piece> pieceMap = new Hashtable<>();

    public static Piece clonePiece(String name) {
        Piece cachedPiece = pieceMap.get(name);
        return (Piece) cachedPiece.clone();
    }

    public static Piece getPiece(String name) {
        return pieceMap.get(name);
    }

    public static void generatePieceMap(String[] rebel, String[] royale) {
        for (String name : rebel) {
            pieceMap.put(name, createPiece(name));
        }
        for (String name : royale) {
            pieceMap.put(name, createPiece(name));
        }
    }

    private static Piece createPiece(String name) {
        Piece piece = null;
        try {
            Class pieceCls = Class.forName("model.piece.concretePiece." + name);
            piece = (Piece) pieceCls.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return piece;
    }
}
