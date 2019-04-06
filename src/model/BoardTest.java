package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Piece.Unit;

public class BoardTest {

    Unit u1 = Unit.GENERAL;
    Piece c1 = new Piece(u1);
    Unit u2 = Unit.CATAPULT;
    Piece c2 = new Piece(u2);
    Player p1 = new Royal("Andy");
    Player p2 = new Rebel("Betty");
    ArrayList<Player> players = new ArrayList<Player>();
    Board b = new Board(players);
    Tile t1 = Board.gridrows.get(0).getTile(0);
    Tile t2 = Board.gridrows.get(1).getTile(0);
    
    @Before
    public void setUp() throws Exception {
        players.add(p1);
        players.add(p2);
        t1.setPiece(c1);
    }
    
    // Check if piece has been initialized successfully in current tile
    @Test
    public void testCheckInit() {
        assertTrue(b.checkInit(0, 0));
    }
    
    // Check if piece in current tile is moveable 
    @Test
    public void testCheckMoveInit() {
        assertTrue(b.checkMoveInit(0, 0));
    }

    // Check if piece in current tile is attackable
    @Test
    public void testCheckAttackInit() {
        assertTrue(b.checkAttackInit(0, 0));
    }

    // Check if piece can move from current tile to target tile
    @Test
    public void testCheckMoveTarget() {
        assertTrue(b.checkMoveTarget(1, 0));
        
    }

    // Check if piece can move from current tile to target tile
    @Test
    public void testCheckMoveTarget2() {
        assertFalse(b.checkMoveTarget(0, 0));
        
    }
    
    // Check if piece can attack target from current tile
    @Test
    public void testCheckAttackTarget() {
        t2.setPiece(c2);
        assertTrue(b.checkAttackTarget(c1, 1, 0));
    }
    
 // Check if piece can attack target from current tile
    @Test
    public void testCheckAttackTarget2() {
        t2.setPiece(c1);
        assertFalse(b.checkAttackTarget(c1, 1, 0));
    }

    // Check if piece moved from current tile to target tile
    @Test
    public void testMove1() {
        assertTrue(b.move(0, 0, 0, 1));
        assertEquals(Board.gridrows.get(0).getTile(0).getPiece(), null);
        assertEquals(Board.gridrows.get(0).getTile(1).getPiece(), c1);
    }
    
    // Check if piece moved from current tile to target tile
    @Test
    public void testMove2() {
        t2.setPiece(c2);
        assertFalse(b.move(0, 0, 1, 0));
        assertEquals(Board.gridrows.get(0).getTile(0).getPiece(), c1);
        assertEquals(Board.gridrows.get(1).getTile(0).getPiece(), c2);
        
    }

    // Check if piece attacked the target piece
    @Test
    public void testAttack() {
        t2.setPiece(c2);
        assertTrue(b.attack(c1, 1, 0));
        assertEquals(Board.gridrows.get(1).getTile(0).getPiece().getHp(), 20);
    }

}
