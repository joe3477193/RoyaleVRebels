package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Piece.Faction;
import model.Piece.Type;
import model.Piece.Unit;

public class PieceTest {

    Unit u1 = Unit.GENERAL;
    Piece c1 = new Piece(u1);

    @Before
    public void setUp() throws Exception {

    }

    // test if the piece has been initialized as expected
    @Test
    public void testMove1() {
        assertEquals(c1.getAttack(), u1.attack);
        assertEquals(c1.getCode(), u1.code);
        assertEquals(c1.getCp(), u1.cp);
        assertEquals(c1.getInitHp(), u1.initHp);
        assertEquals(c1.getFaction(), Faction.REBEL.toString());
        assertEquals(c1.getType(), Type.TROOP.toString());
        assertEquals(c1.getMov(), u1.mov);
        assertEquals(c1.getName(), u1.toString());
        assertEquals(c1.getRange(), u1.range);
        assertEquals(c1.isAttackable(), u1.type.attackable);
        assertEquals(c1.isMoveable(), u1.type.moveable);
        assertEquals(c1.getHp(), u1.initHp);
    }

}
