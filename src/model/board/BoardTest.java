package model.board;

import model.pieces.Catapult;
import model.pieces.General;
import model.pieces.Piece;
import model.players.Player;
import model.players.RebelPlayer;
import model.players.RoyalePlayer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BoardTest {


    Piece c1 = new General();
    Piece c2 = new Catapult();
    Player p1 = new RoyalePlayer("Andy");
    Player p2 = new RebelPlayer("Betty");
    ArrayList<Player> players = new ArrayList<Player>();
    Board b = new Board();
    Tile t1 = Board.boardRows.get(0).getTile(0);
    Tile t2 = Board.boardRows.get(1).getTile(0);
    
    @Before
    public void setUp() throws Exception {
        players.add(p1);
        players.add(p2);
        t1.setPiece(c1);
    }
    
    // Check if pieces has been initialized successfully in current tile
    @Test
    public void testCheckInit() {
        assertTrue(b.checkInit(0, 0));
    }
    
    // Check if pieces in current tile is moveable
    @Test
    public void testCheckMoveInit() {
        assertTrue(b.checkMoveInit(0, 0));
    }

    // Check if pieces in current tile is attackable
    @Test
    public void testCheckAttackInit() {
        assertTrue(b.checkAttackInit(0, 0));
    }

    // Check if pieces can move from current tile to target tile
    @Test
    public void testCheckMoveTarget() {
        assertTrue(b.checkMoveTarget(1, 0));
        
    }

    // Check if pieces can move from current tile to target tile
    @Test
    public void testCheckMoveTarget2() {
        assertFalse(b.checkMoveTarget(0, 0));
        
    }
    
    // Check if pieces can attack target from current tile
    @Test
    public void testCheckAttackTarget() {
        t2.setPiece(c2);
        assertTrue(b.checkAttackTarget(c1, 1, 0));
    }
    
 // Check if pieces can attack target from current tile
    @Test
    public void testCheckAttackTarget2() {
        t2.setPiece(c1);
        assertFalse(b.checkAttackTarget(c1, 1, 0));
    }

    // Check if pieces moved from current tile to target tile
    @Test
    public void testMove1() {
        assertTrue(b.move(0, 0, 0, 1));
        assertEquals(Board.boardRows.get(0).getTile(0).getPiece(), null);
        assertEquals(Board.boardRows.get(0).getTile(1).getPiece(), c1);
    }
    
    // Check if pieces moved from current tile to target tile
    @Test
    public void testMove2() {
        t2.setPiece(c2);
        assertFalse(b.move(0, 0, 1, 0));
        assertEquals(Board.boardRows.get(0).getTile(0).getPiece(), c1);
        assertEquals(Board.boardRows.get(1).getTile(0).getPiece(), c2);
        
    }

    // Check if pieces attacked the target pieces
    @Test
    public void testAttack() {
        t2.setPiece(c2);
        assertTrue(b.attack(0,0, 1, 0));
        assertEquals(Board.boardRows.get(1).getTile(0).getPiece().getHp(), 20);
    }

}
