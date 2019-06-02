package controller.command_mementoPattern;

import model.piece.AbtractPiece.PieceInterface;

public class TurnType {

    private String image;
    private int fromRow;
    private int fromCol;
    private int toRow;
    private int toCol;
    private int damageDealt;
    private boolean death;
    private int prevHp;
    private PieceInterface pieceInterface;

    public TurnType(String image, int fromRow, int fromCol, int toRow, int toCol, int dmg, boolean killed, int prevHp, PieceInterface pieceInterface) {
        this.image = image;
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.damageDealt = dmg;
        this.death = killed;
        this.prevHp = prevHp;
        this.pieceInterface = pieceInterface;
    }
    
    public String returnImage() {
    	return image;
    }
    
    public int fromRow() {
    	return fromRow;
    }
    
    public int fromCol() {
    	return fromCol;
    }
    
    public int tooRow() {
        return toRow;
    }
    
    public int tooCol() {
        return toCol;
    }
    
    public int damageDealt() {
    	return damageDealt;
    }
    
    public int prevHp() {
    	return prevHp;
    }
    
    public boolean death() {
    	return death;
    }
    
    public PieceInterface returnPiece() {
        return pieceInterface;
    }
}
