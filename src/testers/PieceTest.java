package testers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import unsorted.Piece;
import unsorted.PieceType;

public class PieceTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void toStringTest() {
		Piece kingPiece = new Piece(PieceType.KING);
		Piece queenPiece = new Piece(PieceType.QUEEN);
		Piece rookPiece = new Piece(PieceType.ROOK);
		Piece bishopPiece = new Piece(PieceType.BISHOP);
		Piece knightPiece = new Piece(PieceType.KNIGHT);
		Piece pawnPiece = new Piece(PieceType.PAWN);
		Piece wallPiece = new Piece(PieceType.WALL);
		
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
		Piece kingPiece = new Piece(PieceType.KING);
		Piece queenPiece = new Piece(PieceType.QUEEN);
		Piece rookPiece = new Piece(PieceType.ROOK);
		Piece bishopPiece = new Piece(PieceType.BISHOP);
		Piece knightPiece = new Piece(PieceType.KNIGHT);
		Piece pawnPiece = new Piece(PieceType.PAWN);
		Piece wallPiece = new Piece(PieceType.WALL);
		
		assertEquals(PieceType.KING, kingPiece.getType());
		assertEquals(PieceType.QUEEN, queenPiece.getType());
		assertEquals(PieceType.ROOK, rookPiece.getType());
		assertEquals(PieceType.BISHOP, bishopPiece.getType());
		assertEquals(PieceType.KNIGHT, knightPiece.getType());
		assertEquals(PieceType.PAWN, pawnPiece.getType());
		assertEquals(PieceType.WALL, wallPiece.getType());
	}

}
