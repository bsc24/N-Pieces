package unsorted;

import java.util.Random;

public class Piece {
	private final PieceType type;
	
	public Piece(PieceType type) {
		this.type = type;
	}
	
	public Piece(int num) {
		num = num % 6;
		switch(num) {
		case 0:
			this.type = PieceType.PAWN;
			break;
		case 1:
			this.type = PieceType.KNIGHT;
			break;
		case 2:
			this.type = PieceType.BISHOP;
			break;
		case 3:
			this.type = PieceType.ROOK;
			break;
		case 4:
			this.type = PieceType.QUEEN;
			break;
		case 5:
			this.type = PieceType.KING;
			break;
		default:
			this.type = null;
			break;
		}
	}
	
	public static Piece randomPiece() {
		Random r = new Random();
		return new Piece(r.nextInt(6));
	}
	
	public PieceType getType() {
		return type;
	}
	
	
	public String toString() {
		
		switch(type) {
		case KING:
			return "K";
			
		case QUEEN:
			return "Q";
			
		case ROOK:
			return "R";
			
		case BISHOP:
			return "B";
			
		case KNIGHT:
			return "N";
			
		case PAWN:
			return "P";
			
		case WALL:
			return "W";
			
		default:
			return "No type";
		}
	}
}
