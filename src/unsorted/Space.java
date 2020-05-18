package unsorted;
import java.util.HashSet;

public class Space {
	private Piece piece;
	private HashSet<Piece> guardingPieces;		// Guarding pieces are pieces with valid moves to this position
	
	public Space() {
		piece = null;
		guardingPieces = new HashSet<Piece>();
	}
	
	public Space(PieceType type) {
		piece = new Piece(type);
		guardingPieces = new HashSet<Piece>();
	}
	
	public Space(Piece piece) {
		this.piece = piece;
		guardingPieces = new HashSet<Piece>();
	}
	
	public void addGuardingPiece(Piece attacker) {
		guardingPieces.add(attacker);
	}
	
	public void removeGuardingPiece(Piece attacker) {
		guardingPieces.remove(attacker);
	}
	
	public boolean isGuarded() {
		return !guardingPieces.isEmpty();
	}
	
	public void placePiece(Piece piece) {
		this.piece = piece;
	}
	
	public void removePiece() {
		this.piece = null;
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public boolean hasPiece() {
		return this.piece != null;
	}
	
	@Override
	public String toString() {
		if (piece == null) {
			if (this.isGuarded())
				return "x";
			return "o";
		}
		
		return piece.toString();
	}
}
