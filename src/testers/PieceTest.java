package testers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import unsorted.Piece;
import unsorted.PieceType;
import unsorted.Space;

public class PieceTest {

	Piece kingPiece, queenPiece, rookPiece, bishopPiece, knightPiece, pawnPiece, wallPiece;

	@Before
	public void setUp() throws Exception {
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
		kingPiece = null;
		queenPiece = null;
		rookPiece = null;
		bishopPiece = null;
		knightPiece = null;
		pawnPiece = null;
		wallPiece = null;
		
		assertNull(kingPiece);
		assertNull(queenPiece);
		assertNull(rookPiece);
		assertNull(bishopPiece);
		assertNull(knightPiece);
		assertNull(pawnPiece);
		assertNull(wallPiece);
	}

	@Test
	public void toStringTest() {
		assertEquals("K", kingPiece.toString());
		assertEquals("Q", queenPiece.toString());
		assertEquals("R", rookPiece.toString());
		assertEquals("B", bishopPiece.toString());
		assertEquals("N", knightPiece.toString());
		assertEquals("P", pawnPiece.toString());
		assertEquals("W", wallPiece.toString());
	}
	
	@Test
	public void getTypeTest() {
		assertEquals(PieceType.KING, kingPiece.getType());
		assertEquals(PieceType.QUEEN, queenPiece.getType());
		assertEquals(PieceType.ROOK, rookPiece.getType());
		assertEquals(PieceType.BISHOP, bishopPiece.getType());
		assertEquals(PieceType.KNIGHT, knightPiece.getType());
		assertEquals(PieceType.PAWN, pawnPiece.getType());
		assertEquals(PieceType.WALL, wallPiece.getType());
	}

}
