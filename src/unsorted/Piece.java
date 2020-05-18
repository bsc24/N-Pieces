package unsorted;

public class Piece {
	private final PieceType type;
	
	public Piece(PieceType type) {
		this.type = type;
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
