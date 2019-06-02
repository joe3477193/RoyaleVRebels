package controller.commandPattern;

import com.google.java.contract.Invariant;
import model.piece.AbtractPiece.PieceInterface;

@Invariant({"image != null", "fromRow >=0", "fromCol >=0", "tooRow>=0", "tooCol>= 0", "damageDealt >= 0", "prevHp >= 0", "p != null"})
public class TurnType {

    private String image;
    private int fromRow;
    private int fromCol;
    private int tooRow;
    private int tooCol;
    private int damageDealt;
    private boolean death;
    private int prevHp;
    private PieceInterface p;

    public TurnType(String image, int fr, int fc, int tr, int tc, int dmg, boolean killed, int prevHp, PieceInterface p) {
        this.image = image;
        this.fromRow = fr;
        this.fromCol = fc;
        this.tooRow = tr;
        this.tooCol = tc;
        this.damageDealt = dmg;
        this.death = killed;
        this.prevHp = prevHp;
        this.p = p;
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
        return tooRow;
    }

    public int tooCol() {
        return tooCol;
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
        return p;
    }
}
