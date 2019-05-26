package controller.commandPattern;

public class TurnType {

    public String MoveType;
    public String image;
    public int fromRow;
    public int fromCol;
    public int tooRow;
    public int tooCol;
    public int damageDealt;
    public boolean death;
    public int prevHp;

    public TurnType(String MoveType,String image, int fr, int fc, int tr, int tc, int dmg, boolean killed, int prevHp) {
        this.MoveType = MoveType;
        this.image = image;
        this.fromRow = fr;
        this.fromCol = fc;
        this.tooRow = tr;
        this.tooCol = tc;
        this.damageDealt = dmg;
        this.death = killed;
        this.prevHp = prevHp;
    }
}
