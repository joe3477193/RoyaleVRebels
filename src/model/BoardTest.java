package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Card.Unit;

public class BoardTest {

    Unit u1 = Unit.GENERAL;
    Card c1 = new Card(u1);
    Unit u2 = Unit.CATAPULT;
    Card c2 = new Card(u2);
    Board b = new Board();
    Tile t1 = Board.gridrows.get(0).getTile(0);
    Tile t2 = Board.gridrows.get(1).getTile(0);
    Player p1 = new Player(1, "Andy");
    Player p2 = new Player(2, "Betty");
    
    @Before
    public void setUp() throws Exception {
        t1.setCard(c1);
    }
    
    // Check if card has been initialized successfully in current tile
    @Test
    public void testCheckInit() {
        assertTrue(b.checkInit(0, 0));
    }
    
    // Check if card in current tile is moveable 
    @Test
    public void testCheckMoveInit() {
        assertTrue(b.checkMoveInit(0, 0));
    }

    // Check if card in current tile is attackable
    @Test
    public void testCheckAttackInit() {
        assertTrue(b.checkAttackInit(0, 0));
    }

    // Check if card can move from current tile to target tile
    @Test
    public void testCheckMoveTarget() {
        assertTrue(b.checkMoveTarget(1, 0));
        
    }

    // Check if card can move from current tile to target tile
    @Test
    public void testCheckMoveTarget2() {
        assertFalse(b.checkMoveTarget(0, 0));
        
    }
    
    // Check if card can attack target from current tile
    @Test
    public void testCheckAttackTarget() {
        t2.setCard(c2);
        assertTrue(b.checkAttackTarget(c1, 1, 0));
    }
    
 // Check if card can attack target from current tile
    @Test
    public void testCheckAttackTarget2() {
        t2.setCard(c1);
        assertFalse(b.checkAttackTarget(c1, 1, 0));
    }

    // Check if card moved from current tile to target tile
    @Test
    public void testMove1() {
        assertTrue(b.move(0, 0, 0, 1));
        assertEquals(Board.gridrows.get(0).getTile(0).getCard(), null);
        assertEquals(Board.gridrows.get(0).getTile(1).getCard(), c1);
    }
    
    // Check if card moved from current tile to target tile
    @Test
    public void testMove2() {
        t2.setCard(c2);
        assertFalse(b.move(0, 0, 1, 0));
        assertEquals(Board.gridrows.get(0).getTile(0).getCard(), c1);
        assertEquals(Board.gridrows.get(1).getTile(0).getCard(), c2);
        
    }

    // Check if card attacked the target card
    @Test
    public void testAttack() {
        t2.setCard(c2);
        assertTrue(b.attack(c1, 1, 0));
        assertEquals(Board.gridrows.get(1).getTile(0).getCard().getHp(), 20);
    }

}
