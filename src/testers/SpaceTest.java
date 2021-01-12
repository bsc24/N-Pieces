package testers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import unsorted.Space;
import unsorted.Piece;
import unsorted.PieceType;

public class SpaceTest {
	

	Space space;
	Piece kingPiece, queenPiece, rookPiece, bishopPiece, knightPiece, pawnPiece, wallPiece;

	@Before
	public void setUp() throws Exception {
		space = new Space();
		kingPiece = new Piece(PieceType.KING);
		queenPiece = new Piece(PieceType.QUEEN);
		rookPiece = new Piece(PieceType.ROOK);
		bishopPiece = new Piece(PieceType.BISHOP);
		knightPiece = new Piece(PieceType.KNIGHT);
		pawnPiece = new Piece(PieceType.PAWN);
		wallPiece = new Piece(PieceType.WALL);
	}

	@After
	public void tearDown() throws Exception {
		space = null;
		kingPiece = null;
		queenPiece = null;
		rookPiece = null;
		bishopPiece = null;
		knightPiece = null;
		pawnPiece = null;
		wallPiece = null;
		
		assertNull(space);
		assertNull(kingPiece);
		assertNull(queenPiece);
		assertNull(rookPiece);
		assertNull(bishopPiece);
		assertNull(knightPiece);
		assertNull(pawnPiece);
		assertNull(wallPiece);
	}

	@Test
	public void guardingTests() {
		assertFalse(space.isGuarded());
		
		space.addGuardingPiece(queenPiece);
		assertTrue(space.isGuarded());
		
		space.addGuardingPiece(kingPiece);
		assertTrue(space.isGuarded());
		
		space.removeGuardingPiece(queenPiece);
		assertTrue(space.isGuarded());
		
		space.removeGuardingPiece(kingPiece);
		assertFalse(space.isGuarded());
	}
	
	@Test
	public void placingTests() {
		assertFalse(space.hasPiece());
		assertEquals(null, space.getPiece());
		
		space.placePiece(queenPiece);
		
		assertTrue(space.hasPiece());
		assertEquals(queenPiece, space.getPiece());
		
		space.removePiece();
		assertFalse(space.hasPiece());
		assertEquals(null, space.getPiece());
	}
	
	@Test
	public void toStringTest() {
		assertEquals(null, space.getPiece());
		assertEquals("o", space.toString());
		assertFalse(space.hasPiece());
		
		space.placePiece(kingPiece);
		assertEquals(kingPiece, space.getPiece());
		assertEquals("K", space.toString());
		assertTrue(space.hasPiece());
		
		space.placePiece(queenPiece);
		assertEquals(queenPiece, space.getPiece());
		assertEquals("Q", space.toString());
		assertTrue(space.hasPiece());
		
		space.placePiece(rookPiece);
		assertEquals(rookPiece, space.getPiece());
		assertEquals("R", space.toString());
		assertTrue(space.hasPiece());
		
		space.placePiece(bishopPiece);
		assertEquals(bishopPiece, space.getPiece());
		assertEquals("B", space.toString());
		assertTrue(space.hasPiece());
		
		space.placePiece(knightPiece);
		assertEquals(knightPiece, space.getPiece());
		assertEquals("N", space.toString());
		assertTrue(space.hasPiece());
		
		space.placePiece(pawnPiece);
		assertEquals(pawnPiece, space.getPiece());
		assertEquals("P", space.toString());
		assertTrue(space.hasPiece());

		space.placePiece(wallPiece);
		assertEquals(wallPiece, space.getPiece());
		assertEquals("W", space.toString());
		assertTrue(space.hasPiece());
		
		space.removePiece();
		assertEquals(null, space.getPiece());
		assertEquals("o", space.toString());
		assertFalse(space.hasPiece());
		
		space.addGuardingPiece(pawnPiece);
		assertEquals(null, space.getPiece());
		assertEquals("x", space.toString());
		assertFalse(space.hasPiece());
	}

}
