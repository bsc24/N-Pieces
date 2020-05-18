package testers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import unsorted.Space;
import unsorted.Piece;
import unsorted.PieceType;

public class SpaceTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void guardingTests() {
		Space space = new Space();
		assertFalse(space.isGuarded());
		
		Piece queenPiece = new Piece(PieceType.QUEEN);
		Piece kingPiece = new Piece(PieceType.KING);
		
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
		Space space = new Space();
		assertFalse(space.hasPiece());
		assertEquals(null, space.getPiece());
		
		Piece queenPiece = new Piece(PieceType.QUEEN);
		space.placePiece(queenPiece);
		
		assertTrue(space.hasPiece());
		assertEquals(queenPiece, space.getPiece());
		
		space.removePiece();
		assertFalse(space.hasPiece());
		assertEquals(null, space.getPiece());
	}
	
	@Test
	public void toStringTest() {
		Space space = new Space();

		Piece kingPiece = new Piece(PieceType.KING);
		Piece queenPiece = new Piece(PieceType.QUEEN);
		Piece rookPiece = new Piece(PieceType.ROOK);
		Piece bishopPiece = new Piece(PieceType.BISHOP);
		Piece knightPiece = new Piece(PieceType.KNIGHT);
		Piece pawnPiece = new Piece(PieceType.PAWN);
		Piece wallPiece = new Piece(PieceType.WALL);

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
