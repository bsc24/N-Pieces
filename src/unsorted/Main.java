package unsorted;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Board board = new Board(9, 9);
		System.out.println(board);
		String separator = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
		
		Piece kingPiece = new Piece(PieceType.KING);
		Piece queenPiece = new Piece(PieceType.QUEEN);
		Piece rookPiece = new Piece(PieceType.ROOK);
		Piece bishopPiece = new Piece(PieceType.BISHOP);
		Piece knightPiece = new Piece(PieceType.KNIGHT);
		Piece pawnPiece = new Piece(PieceType.PAWN);
		Piece wallPiece = new Piece(PieceType.WALL);
		
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		pieces.add(kingPiece);
		pieces.add(queenPiece);
		pieces.add(rookPiece);
		pieces.add(bishopPiece);
		pieces.add(knightPiece);
		pieces.add(pawnPiece);
		pieces.add(wallPiece);
		
		for (Piece piece: pieces) {
			if (piece.getType() == PieceType.WALL)
				board.placeWall(4, 4);
			else
				board.placePiece(piece, 4, 4);
			System.out.println(board + "\n");
			board.removePiece(4, 4);
			System.out.println(board);
			System.out.println(separator);
		}

		board = new Board(7, 7);
		
		board.placePiece(new Piece(PieceType.QUEEN), 1, 1);
		board.placePiece(new Piece(PieceType.QUEEN), 2, 4);
		board.placePiece(new Piece(PieceType.QUEEN), 3, 2);
		board.placePiece(new Piece(PieceType.QUEEN), 4, 5);
		board.placePiece(new Piece(PieceType.QUEEN), 5, 3);
		System.out.println(board);
		System.out.println(board.isValidSolution());
	}

}
