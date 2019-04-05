package model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    List<Player> playersB;

    protected static ArrayList<BoardRows> gridrows;
    public static final int BOARD_ROWS = 13; // increments in 5
    public static final int BOARD_COLS = 15; // increments in 4

    public Board() {

        Board.gridrows = new ArrayList<>(BOARD_ROWS);

        this.playersB = new ArrayList<>();
        ;

        for (int i = 0; i < BOARD_ROWS; i++) {
            gridrows.add(new BoardRows());
        }
    }

    public void moveGridP() {

        // method to update movements
    }

    public boolean checkInit(int row, int tile) {
        return gridrows.get(row).getTile(tile).hasCard();
    }

    public boolean checkMoveInit(int row, int tile) {
        return checkInit(row, tile) && gridrows.get(row).getTile(tile).getCard().isMoveable();
    }

    public boolean checkAttackInit(int row, int tile) {
        return checkInit(row, tile) && gridrows.get(row).getTile(tile).getCard().isAttackable();
    }

    public boolean checkMoveTarget(int row, int tile) {
        return !gridrows.get(row).getTile(tile).hasCard();
    }

    public boolean checkAttackTarget(Card card, int row, int tile) {
        Tile space = gridrows.get(row).getTile(tile);
        if (space.hasCard() && !space.getCard().getFaction().equals(card.getFaction())) {
            return true;
        }
        return false;
    }

    // didn't check if it can move or not
    public boolean move(int inRow, int inTile, int tgRow, int tgTile) {
        Tile inSpace = gridrows.get(inRow).getTile(inTile);
        Tile tgSpace = gridrows.get(tgRow).getTile(tgTile);
        if (!tgSpace.hasCard()) {
            tgSpace.setCard(inSpace.getCard());
            inSpace.removeCard();
            return true;
        }
        return false;
    }

    public boolean attack(Card card, int row, int tile) {
        Tile space = gridrows.get(row).getTile(tile);
        Card tgCard = space.getCard();
        if (!tgCard.getFaction().equals(card.getFaction())) {
            tgCard.attackBy(card.getAttack());
            if (tgCard.isDead()) {
                space.removeCard();
            }
            return true;
        }
        return false;
    }

}
