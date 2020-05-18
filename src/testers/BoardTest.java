package testers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import unsorted.Board;
import unsorted.Piece;
import unsorted.PieceType;

public class BoardTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void isValidSolutionTest() {
		Board board = new Board(7, 7);
		assertTrue(board.isValidSolution());
		
		board.placePiece(new Piece(PieceType.QUEEN), 1, 1);
		board.placePiece(new Piece(PieceType.QUEEN), 2, 4);
		board.placePiece(new Piece(PieceType.QUEEN), 3, 2);
		board.placePiece(new Piece(PieceType.QUEEN), 4, 5);
		board.placePiece(new Piece(PieceType.QUEEN), 5, 3);
		assertTrue(board.isValidSolution());
		
		board.placePiece(new Piece(PieceType.QUEEN), 1, 2);
		assertFalse(board.isValidSolution());
	}
	
	/* toString tests, one for each piece placement */
	@Test
	public void toStringBlankTest() {
		Board board = new Board(7, 7);
		String boardString = 
				"W|W|W|W|W|W|W\n" +
				"W|o|o|o|o|o|W\n" +
				"W|o|o|o|o|o|W\n" +
				"W|o|o|o|o|o|W\n" +
				"W|o|o|o|o|o|W\n" +
				"W|o|o|o|o|o|W\n" +
				"W|W|W|W|W|W|W";
		assertEquals(boardString, board.toString());
	}
	
	@Test
	public void toStringKingTest() {
		Board board = new Board(7, 7);
		Piece kingPiece = new Piece(PieceType.KING);
		String boardString = 
				"W|W|W|W|W|W|W\n" +
				"W|o|o|o|o|o|W\n" +
				"W|o|x|x|x|o|W\n" +
				"W|o|x|K|x|o|W\n" +
				"W|o|x|x|x|o|W\n" +
				"W|o|o|o|o|o|W\n" +
				"W|W|W|W|W|W|W";
		
		board.placePiece(kingPiece, 3, 3);
		assertEquals(boardString, board.toString());
	}
	
	@Test
	public void toStringQueenTest() {
		Board board = new Board(7, 7);
		Piece queenPiece = new Piece(PieceType.QUEEN);
		String boardString = 
				"W|W|W|W|W|W|W\n" +
				"W|x|o|x|o|x|W\n" +
				"W|o|x|x|x|o|W\n" +
				"W|x|x|Q|x|x|W\n" +
				"W|o|x|x|x|o|W\n" +
				"W|x|o|x|o|x|W\n" +
				"W|W|W|W|W|W|W";
		
		board.placePiece(queenPiece, 3, 3);
		assertEquals(boardString, board.toString());
	}
	
	@Test
	public void toStringRookTest() {
		Board board = new Board(7, 7);
		Piece rookPiece = new Piece(PieceType.ROOK);
		String boardString = 
				"W|W|W|W|W|W|W\n" +
				"W|o|o|x|o|o|W\n" +
				"W|o|o|x|o|o|W\n" +
				"W|x|x|R|x|x|W\n" +
				"W|o|o|x|o|o|W\n" +
				"W|o|o|x|o|o|W\n" +
				"W|W|W|W|W|W|W";

		board.placePiece(rookPiece, 3, 3);
		assertEquals(boardString, board.toString());
	}
	
	@Test
	public void toStringBishopTest() {
		Board board = new Board(7, 7);
		Piece bishopPiece = new Piece(PieceType.BISHOP);
		String boardString = 
				"W|W|W|W|W|W|W\n" +
				"W|x|o|o|o|x|W\n" +
				"W|o|x|o|x|o|W\n" +
				"W|o|o|B|o|o|W\n" +
				"W|o|x|o|x|o|W\n" +
				"W|x|o|o|o|x|W\n" +
				"W|W|W|W|W|W|W";

		board.placePiece(bishopPiece, 3, 3);
		assertEquals(boardString, board.toString());
	}
	
	@Test
	public void toStringKnightTest() {
		Board board = new Board(7, 7);
		Piece knightPiece = new Piece(PieceType.KNIGHT);
		String boardString = 
				"W|W|W|W|W|W|W\n" +
				"W|o|x|o|x|o|W\n" +
				"W|x|o|o|o|x|W\n" +
				"W|o|o|N|o|o|W\n" +
				"W|x|o|o|o|x|W\n" +
				"W|o|x|o|x|o|W\n" +
				"W|W|W|W|W|W|W";

		board.placePiece(knightPiece, 3, 3);
		assertEquals(boardString, board.toString());
	}
	
	@Test
	public void toStringPawnTest() {
		Board board = new Board(7, 7);
		Piece pawnPiece = new Piece(PieceType.PAWN);
		String boardString = 
				"W|W|W|W|W|W|W\n" +
				"W|o|o|o|o|o|W\n" +
				"W|o|x|o|x|o|W\n" +
				"W|o|o|P|o|o|W\n" +
				"W|o|x|o|x|o|W\n" +
				"W|o|o|o|o|o|W\n" +
				"W|W|W|W|W|W|W";

		board.placePiece(pawnPiece, 3, 3);
		assertEquals(boardString, board.toString());
	}
	
	@Test
	public void toStringWallTest() {
		Board board = new Board(7, 7);
		String boardString = 
				"W|W|W|W|W|W|W\n" +
				"W|o|o|o|o|o|W\n" +
				"W|o|o|o|o|o|W\n" +
				"W|o|o|W|o|o|W\n" +
				"W|o|o|o|o|o|W\n" +
				"W|o|o|o|o|o|W\n" +
				"W|W|W|W|W|W|W";

		board.placeWall(3, 3);
		assertEquals(boardString, board.toString());
	}
	
}
