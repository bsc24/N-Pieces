package testers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import unsorted.Board;
import unsorted.Piece;
import unsorted.PieceType;
import unsorted.Space;

public class BoardTest {

	Board board;
	Piece kingPiece, queenPiece, rookPiece, bishopPiece, knightPiece, pawnPiece, wallPiece;
	
	String blankBoardString = 
			"o|o|o|o|o|o|o\n" +
			"o|o|o|o|o|o|o\n" +
			"o|o|o|o|o|o|o\n" +
			"o|o|o|o|o|o|o\n" +
			"o|o|o|o|o|o|o\n" +
			"o|o|o|o|o|o|o\n" +
			"o|o|o|o|o|o|o";

	@Before
	public void setUp() throws Exception {
		board = new Board(7,7);
		kingPiece = new Piece(PieceType.KING);
		queenPiece = new Piece(PieceType.QUEEN);
		rookPiece = new Piece(PieceType.ROOK);
		bishopPiece = new Piece(PieceType.BISHOP);
		knightPiece = new Piece(PieceType.KNIGHT);
		pawnPiece = new Piece(PieceType.PAWN);
		wallPiece = new Piece(PieceType.WALL);
		assertEquals(blankBoardString, board.toString());
	}

	@After
	public void tearDown() throws Exception {
		board = null;
		kingPiece = null;
		queenPiece = null;
		rookPiece = null;
		bishopPiece = null;
		knightPiece = null;
		pawnPiece = null;
		wallPiece = null;
		
		assertNull(board);
		assertNull(kingPiece);
		assertNull(queenPiece);
		assertNull(rookPiece);
		assertNull(bishopPiece);
		assertNull(knightPiece);
		assertNull(pawnPiece);
		assertNull(wallPiece);
	}
	
	@Test
	public void isValidSolutionTest() {
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
	public void toStringKingTest() {
		String boardString = 
				"o|o|o|o|o|o|o\n" +
				"o|o|o|o|o|o|o\n" +
				"o|o|x|x|x|o|o\n" +
				"o|o|x|K|x|o|o\n" +
				"o|o|x|x|x|o|o\n" +
				"o|o|o|o|o|o|o\n" +
				"o|o|o|o|o|o|o";
		
		board.placePiece(kingPiece, 3, 3);
		assertEquals(boardString, board.toString());
		board.removePiece(3, 3);
		assertEquals(blankBoardString, board.toString());
	}
	
	@Test
	public void toStringQueenTest() {
		String boardString = 
				"x|o|o|x|o|o|x\n" +
				"o|x|o|x|o|x|o\n" +
				"o|o|x|x|x|o|o\n" +
				"x|x|x|Q|x|x|x\n" +
				"o|o|x|x|x|o|o\n" +
				"o|x|o|x|o|x|o\n" +
				"x|o|o|x|o|o|x";
		
		board.placePiece(queenPiece, 3, 3);
		assertEquals(boardString, board.toString());
		board.removePiece(3, 3);
		assertEquals(blankBoardString, board.toString());
	}
	
	@Test
	public void toStringRookTest() {
		String boardString = 
				"o|o|o|x|o|o|o\n" +
				"o|o|o|x|o|o|o\n" +
				"o|o|o|x|o|o|o\n" +
				"x|x|x|R|x|x|x\n" +
				"o|o|o|x|o|o|o\n" +
				"o|o|o|x|o|o|o\n" +
				"o|o|o|x|o|o|o";

		board.placePiece(rookPiece, 3, 3);
		assertEquals(boardString, board.toString());
		board.removePiece(3, 3);
		assertEquals(blankBoardString, board.toString());
	}
	
	@Test
	public void toStringBishopTest() {
		String boardString = 
				"x|o|o|o|o|o|x\n" +
				"o|x|o|o|o|x|o\n" +
				"o|o|x|o|x|o|o\n" +
				"o|o|o|B|o|o|o\n" +
				"o|o|x|o|x|o|o\n" +
				"o|x|o|o|o|x|o\n" +
				"x|o|o|o|o|o|x";

		board.placePiece(bishopPiece, 3, 3);
		assertEquals(boardString, board.toString());
		board.removePiece(3, 3);
		assertEquals(blankBoardString, board.toString());
	}
	
	@Test
	public void toStringKnightTest() {
		String boardString = 
				"o|o|o|o|o|o|o\n" +
				"o|o|x|o|x|o|o\n" +
				"o|x|o|o|o|x|o\n" +
				"o|o|o|N|o|o|o\n" +
				"o|x|o|o|o|x|o\n" +
				"o|o|x|o|x|o|o\n" +
				"o|o|o|o|o|o|o";

		board.placePiece(knightPiece, 3, 3);
		assertEquals(boardString, board.toString());
		board.removePiece(3, 3);
		assertEquals(blankBoardString, board.toString());
	}
	
	@Test
	public void toStringPawnTest() {
		String boardString = 
				"o|o|o|o|o|o|o\n" +
				"o|o|o|o|o|o|o\n" +
				"o|o|x|o|x|o|o\n" +
				"o|o|o|P|o|o|o\n" +
				"o|o|x|o|x|o|o\n" +
				"o|o|o|o|o|o|o\n" +
				"o|o|o|o|o|o|o";

		board.placePiece(pawnPiece, 3, 3);
		assertEquals(boardString, board.toString());
		board.removePiece(3, 3);
		assertEquals(blankBoardString, board.toString());
	}
	
	@Test
	public void toStringWallTest() {
		String boardString = 
				"o|o|o|o|o|o|o\n" +
				"o|o|o|o|o|o|o\n" +
				"o|o|o|o|o|o|o\n" +
				"o|o|o|W|o|o|o\n" +
				"o|o|o|o|o|o|o\n" +
				"o|o|o|o|o|o|o\n" +
				"o|o|o|o|o|o|o";

		board.placeWall(3, 3);
		assertEquals(boardString, board.toString());
		board.removePiece(3, 3);
		
		boardString = 
				"o|o|o|o|o|o|o\n" +
				"o|o|o|o|o|o|o\n" +
				"o|o|W|W|W|o|o\n" +
				"o|o|W|W|W|o|o\n" +
				"o|o|W|W|W|o|o\n" +
				"o|o|o|o|o|o|o\n" +
				"o|o|o|o|o|o|o";
		
		board.placeBoxWalls(2, 2, 4, 4);
		assertEquals(boardString, board.toString());
		
		for (int x = 2; x <= 4; x++) {
			for (int y = 2; y <= 4; y++)
				board.removePiece(x, y);
		}
		
		assertEquals(blankBoardString, board.toString());
	}
	
	@Test
	public void toStringEasyHardTest() {
		String boardEasyString = 
				"x|o|o|x|o|o|x\n" +
				"o|x|o|x|o|x|o\n" +
				"o|o|x|x|x|o|o\n" +
				"x|x|x|Q|x|x|x\n" +
				"o|o|x|x|x|o|o\n" +
				"o|x|o|x|o|x|o\n" +
				"x|o|o|x|o|o|x";

		String boardHardString = 
				"o|o|o|o|o|o|o\n" +
				"o|o|o|o|o|o|o\n" +
				"o|o|o|o|o|o|o\n" +
				"o|o|o|Q|o|o|o\n" +
				"o|o|o|o|o|o|o\n" +
				"o|o|o|o|o|o|o\n" +
				"o|o|o|o|o|o|o";
		
		board.placePiece(queenPiece, 3, 3);
		assertEquals(boardEasyString, board.toString());
		
		board.setHard();
		assertEquals(boardHardString, board.toString());
		
	}
	
}
