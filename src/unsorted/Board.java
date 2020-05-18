package unsorted;
import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	
	private ArrayList<ArrayList<Space>> board;		// The "outer layer" of ArrayList is the y coordinate (the rows of the board) while the "inner layer" is for the actual pieces on that piece of the board 
	private static Piece wallPiece = new Piece(PieceType.WALL);
	private final int xDimension, yDimension;
	private HashMap<Piece, int[]> placedPieces;		// Maps pieces to a coordinate pair of (xCoordinate, yCoordinate)
	
	/* Constructors */
	public Board(int xDimension, int yDimension) {
		this.xDimension = xDimension;
		this.yDimension = yDimension;
		board = new ArrayList<ArrayList<Space>>();
		placedPieces = new HashMap<Piece, int[]>();
		
		for (int y = 0; y < yDimension; y++) {
			board.add(new ArrayList<Space>());
			ArrayList<Space> currentRow = board.get(y);
			for (int x = 0; x < xDimension; x++) {
				if (y == 0 || y == yDimension - 1 || x == 0 || x == yDimension - 1)
					currentRow.add(new Space(wallPiece));
				else
					currentRow.add(new Space());
			}
		}
	}
	
	
	/* Board manipulation methods */
	public void placeWall(int xCoordinate, int yCoordinate) {
		board.get(yCoordinate).get(xCoordinate).placePiece(wallPiece);
	}
	
	public void placePiece(Piece piece, int xCoordinate, int yCoordinate) {
		
		if (yCoordinate <= 0 || yCoordinate >= yDimension || xCoordinate <= 0 || xCoordinate >= xDimension) {
			System.out.println("Coordinate provided to placePiece is out of bounds");
			return;
		}
		else if (board.get(yCoordinate).get(xCoordinate).hasPiece()) {
			System.out.println("Coordinate provided already has piece placed on it");
			return;
		}
		
		switch(piece.getType()) {
		case KING:
			guardAdjacent(piece, xCoordinate, yCoordinate);
			break;
			
		case QUEEN:
			guardCardinal(piece, xCoordinate, yCoordinate);
			guardIntercardinal(piece, xCoordinate, yCoordinate);
			break;
			
		case ROOK:
			guardCardinal(piece, xCoordinate, yCoordinate);
			break;
			
		case BISHOP:
			guardIntercardinal(piece, xCoordinate, yCoordinate);
			break;
			
		case KNIGHT:
			guardKnight(piece, xCoordinate, yCoordinate);
			break;
			
		case PAWN:
			guardAdjacentDiagonal(piece, xCoordinate, yCoordinate);
			break;
			
		case WALL:
			System.out.println("Cannot place wall using placePiece, use placeWall instead.");
			return;
			
		default:
			System.out.println("Problem in Board.placePiece(Piece piece, int xCoordinate, int yCoordinate)");
		}
		
		board.get(yCoordinate).get(xCoordinate).placePiece(piece);
		int[] coordinates = {xCoordinate, yCoordinate};
		placedPieces.put(piece, coordinates);
	}
	
	
	private void guardCardinal(Piece piece, int xCoordinate, int yCoordinate) {
		ArrayList<Space> row = board.get(yCoordinate);
		for (int x = xCoordinate + 1; x < xDimension; x++) {		// Guard across in the +x direction
			if (row.get(x).hasPiece() && row.get(x).getPiece().getType() == PieceType.WALL)		// Stop upon encountering a wall
				break;
			row.get(x).addGuardingPiece(piece);
		}
		
		for (int x = xCoordinate - 1; x > 0; x--) {					// Guard across in the -x direction
			if (row.get(x).hasPiece() && row.get(x).getPiece().getType() == PieceType.WALL)
				break;
			row.get(x).addGuardingPiece(piece);
		}
		
		for (int y = yCoordinate + 1; y < yDimension; y++) {		// Guard across in the +y direction
			if (board.get(y).get(xCoordinate).hasPiece() && board.get(y).get(xCoordinate).getPiece().getType() == PieceType.WALL)
				break;
			board.get(y).get(xCoordinate).addGuardingPiece(piece);
		}
		
		for (int y = yCoordinate - 1; y > 0; y--) {					// Guard across in the -y direction
			if (board.get(y).get(xCoordinate).hasPiece() && board.get(y).get(xCoordinate).getPiece().getType() == PieceType.WALL)
				break;
			board.get(y).get(xCoordinate).addGuardingPiece(piece);
		}
	}
	
	private void guardIntercardinal(Piece piece, int xCoordinate, int yCoordinate) {
		int offset = 1;
		while(true) {
			int nextX = xCoordinate + offset;
			int nextY = yCoordinate + offset;
			if (board.get(nextY).get(nextX).hasPiece() && board.get(nextY).get(nextX).getPiece().getType() == PieceType.WALL)
				break;
			board.get(nextY).get(nextX).addGuardingPiece(piece);
			offset++;
		}
		
		offset = 1;
		while(true) {
			int nextX = xCoordinate + offset;
			int nextY = yCoordinate - offset;
			if (board.get(nextY).get(nextX).hasPiece() && board.get(nextY).get(nextX).getPiece().getType() == PieceType.WALL)
				break;
			board.get(nextY).get(nextX).addGuardingPiece(piece);
			offset++;
		}

		offset = 1;
		while(true) {
			int nextX = xCoordinate - offset;
			int nextY = yCoordinate + offset;
			if (board.get(nextY).get(nextX).hasPiece() && board.get(nextY).get(nextX).getPiece().getType() == PieceType.WALL)
				break;
			board.get(nextY).get(nextX).addGuardingPiece(piece);
			offset++;
		}

		offset = 1;
		while(true) {
			int nextX = xCoordinate - offset;
			int nextY = yCoordinate - offset;
			if (board.get(nextY).get(nextX).hasPiece() && board.get(nextY).get(nextX).getPiece().getType() == PieceType.WALL)
				break;
			board.get(nextY).get(nextX).addGuardingPiece(piece);
			offset++;
		}
	}
	
	
	private void guardKnight(Piece piece, int xCoordinate, int yCoordinate) {
		int[][] possibleKnightMoves = {
				{xCoordinate + 2, yCoordinate + 1},
				{xCoordinate + 2, yCoordinate - 1},
				{xCoordinate + 1, yCoordinate + 2},
				{xCoordinate + 1, yCoordinate - 2},
				{xCoordinate - 1, yCoordinate + 2},
				{xCoordinate - 1, yCoordinate - 2},
				{xCoordinate - 2, yCoordinate + 1},
				{xCoordinate - 2, yCoordinate - 1}
				};
		
		setPossibleGuards(piece, possibleKnightMoves);
	}
	
	private void guardAdjacent(Piece piece, int xCoordinate, int yCoordinate) {
		int [][] possibleAdjacentMoves = {
				{xCoordinate + 1, yCoordinate + 1},
				{xCoordinate + 1, yCoordinate},
				{xCoordinate + 1, yCoordinate - 1},
				{xCoordinate, yCoordinate + 1},
				{xCoordinate, yCoordinate - 1},
				{xCoordinate - 1, yCoordinate + 1},
				{xCoordinate - 1, yCoordinate},
				{xCoordinate - 1, yCoordinate - 1}
				};
		
		setPossibleGuards(piece, possibleAdjacentMoves);
	}
	
	private void guardAdjacentDiagonal(Piece piece, int xCoordinate, int yCoordinate) {
		int [][] possibleAdjacentDiagonalMoves = {
				{xCoordinate + 1, yCoordinate + 1},
				{xCoordinate + 1, yCoordinate - 1},
				{xCoordinate - 1, yCoordinate + 1},
				{xCoordinate - 1, yCoordinate - 1},
		};
		
		setPossibleGuards(piece, possibleAdjacentDiagonalMoves);
	}
	
	private void setPossibleGuards(Piece piece, int[][] possibleMoves) {		// Filters through possibleMoves and sets them as guard if they are a valid coordinate
		for (int i = 0; i < possibleMoves.length; i++) {
			int[] nextCoordinate = possibleMoves[i];
			if (nextCoordinate[0] > 0 && nextCoordinate[0] < xDimension && nextCoordinate[1] > 0 && nextCoordinate[1] < yDimension) {
				board.get(nextCoordinate[1]).get(nextCoordinate[0]).addGuardingPiece(piece);
			}
		}
	}
	
	
	/*
	public void removeWall(int xCoordinate, int yCoordinate) {
		board.get(yCoordinate).get(xCoordinate).placePiece(wallPiece);
	}
	*/
	
	public void removePiece(int xCoordinate, int yCoordinate) {
		
		if (yCoordinate <= 0 || yCoordinate >= yDimension || xCoordinate <= 0 || xCoordinate >= xDimension) {
			System.out.println("Coordinate provided to removePiece is out of bounds");
			return;
		}
		
		Piece piece = board.get(yCoordinate).get(xCoordinate).getPiece();
		if (piece == null)
			return;
		
		switch(piece.getType()) {
		case KING:
			removeGuardAdjacent(piece, xCoordinate, yCoordinate);
			break;
			
		case QUEEN:
			removeGuardCardinal(piece, xCoordinate, yCoordinate);
			removeGuardIntercardinal(piece, xCoordinate, yCoordinate);
			break;
			
		case ROOK:
			removeGuardCardinal(piece, xCoordinate, yCoordinate);
			break;
			
		case BISHOP:
			removeGuardIntercardinal(piece, xCoordinate, yCoordinate);
			break;
			
		case KNIGHT:
			removeGuardKnight(piece, xCoordinate, yCoordinate);
			break;
			
		case PAWN:
			removeGuardAdjacentDiagonal(piece, xCoordinate, yCoordinate);
			break;
			
		case WALL:
			break;
			
		default:
			System.out.println("Problem in Board.removePiece(int xCoordinate, int yCoordinate)");
		}
		
		board.get(yCoordinate).get(xCoordinate).removePiece();
		int[] coordinates = {xCoordinate, yCoordinate};
		placedPieces.remove(piece, coordinates);
	}
	
	
	private void removeGuardCardinal(Piece piece, int xCoordinate, int yCoordinate) {
		ArrayList<Space> row = board.get(yCoordinate);
		for (int x = xCoordinate + 1; x < xDimension; x++) {		// Guard across in the +x direction
			if (row.get(x).hasPiece() && row.get(x).getPiece().getType() == PieceType.WALL)		// Stop upon encountering a wall
				break;
			row.get(x).removeGuardingPiece(piece);
		}
		
		for (int x = xCoordinate - 1; x > 0; x--) {					// Guard across in the -x direction
			if (row.get(x).hasPiece() && row.get(x).getPiece().getType() == PieceType.WALL)
				break;
			row.get(x).removeGuardingPiece(piece);
		}
		
		for (int y = yCoordinate + 1; y < yDimension; y++) {		// Guard across in the +y direction
			if (board.get(y).get(xCoordinate).hasPiece() && board.get(y).get(xCoordinate).getPiece().getType() == PieceType.WALL)
				break;
			board.get(y).get(xCoordinate).removeGuardingPiece(piece);
		}
		
		for (int y = yCoordinate - 1; y > 0; y--) {					// Guard across in the -y direction
			if (board.get(y).get(xCoordinate).hasPiece() && board.get(y).get(xCoordinate).getPiece().getType() == PieceType.WALL)
				break;
			board.get(y).get(xCoordinate).removeGuardingPiece(piece);
		}
	}
	
	private void removeGuardIntercardinal(Piece piece, int xCoordinate, int yCoordinate) {
		int offset = 1;
		while(true) {
			int nextX = xCoordinate + offset;
			int nextY = yCoordinate + offset;
			if (board.get(nextY).get(nextX).hasPiece() && board.get(nextY).get(nextX).getPiece().getType() == PieceType.WALL)
				break;
			board.get(nextY).get(nextX).removeGuardingPiece(piece);
			offset++;
		}

		offset = 1;
		while(true) {
			int nextX = xCoordinate + offset;
			int nextY = yCoordinate - offset;
			if (board.get(nextY).get(nextX).hasPiece() && board.get(nextY).get(nextX).getPiece().getType() == PieceType.WALL)
				break;
			board.get(nextY).get(nextX).removeGuardingPiece(piece);
			offset++;
		}

		offset = 1;
		while(true) {
			int nextX = xCoordinate - offset;
			int nextY = yCoordinate + offset;
			if (board.get(nextY).get(nextX).hasPiece() && board.get(nextY).get(nextX).getPiece().getType() == PieceType.WALL)
				break;
			board.get(nextY).get(nextX).removeGuardingPiece(piece);
			offset++;
		}

		offset = 1;
		while(true) {
			int nextX = xCoordinate - offset;
			int nextY = yCoordinate - offset;
			if (board.get(nextY).get(nextX).hasPiece() && board.get(nextY).get(nextX).getPiece().getType() == PieceType.WALL)
				break;
			board.get(nextY).get(nextX).removeGuardingPiece(piece);
			offset++;
		}
	}
	
	
	private void removeGuardKnight(Piece piece, int xCoordinate, int yCoordinate) {
		int[][] possibleKnightMoves = {
				{xCoordinate + 2, yCoordinate + 1},
				{xCoordinate + 2, yCoordinate - 1},
				{xCoordinate + 1, yCoordinate + 2},
				{xCoordinate + 1, yCoordinate - 2},
				{xCoordinate - 1, yCoordinate + 2},
				{xCoordinate - 1, yCoordinate - 2},
				{xCoordinate - 2, yCoordinate + 1},
				{xCoordinate - 2, yCoordinate - 1}
				};
		
		removePossibleGuards(piece, possibleKnightMoves);
	}
	
	private void removeGuardAdjacent(Piece piece, int xCoordinate, int yCoordinate) {
		int [][] possibleAdjacentMoves = {
				{xCoordinate + 1, yCoordinate + 1},
				{xCoordinate + 1, yCoordinate},
				{xCoordinate + 1, yCoordinate - 1},
				{xCoordinate, yCoordinate + 1},
				{xCoordinate, yCoordinate - 1},
				{xCoordinate - 1, yCoordinate + 1},
				{xCoordinate - 1, yCoordinate},
				{xCoordinate - 1, yCoordinate - 1}
				};
		
		removePossibleGuards(piece, possibleAdjacentMoves);
	}
	
	private void removeGuardAdjacentDiagonal(Piece piece, int xCoordinate, int yCoordinate) {
		int [][] possibleAdjacentDiagonalMoves = {
				{xCoordinate + 1, yCoordinate + 1},
				{xCoordinate + 1, yCoordinate - 1},
				{xCoordinate - 1, yCoordinate + 1},
				{xCoordinate - 1, yCoordinate - 1},
		};
		
		removePossibleGuards(piece, possibleAdjacentDiagonalMoves);
	}
	
	private void removePossibleGuards(Piece piece, int[][] possibleMoves) {		// Filters through possibleMoves and sets them as guard if they are a valid coordinate
		for (int i = 0; i < possibleMoves.length; i++) {
			int[] nextCoordinate = possibleMoves[i];
			if (nextCoordinate[0] > 0 && nextCoordinate[0] < xDimension && nextCoordinate[1] > 0 && nextCoordinate[1] < yDimension) {
				board.get(nextCoordinate[1]).get(nextCoordinate[0]).removeGuardingPiece(piece);
			}
		}
	}
	
	
	/* "is" methods */
	public boolean spaceIsGuarded(int xCoordinate, int yCoordinate) {
		return board.get(yCoordinate).get(xCoordinate).isGuarded();
	}
	
	public boolean spaceIsEmpty(int xCoordinate, int yCoordinate) {
		return !board.get(yCoordinate).get(xCoordinate).hasPiece();
	}
	
	public boolean isValidSolution() {
		for (Piece piece: placedPieces.keySet()) {
			
			if (spaceIsGuarded(placedPieces.get(piece)[0], placedPieces.get(piece)[1]))
				return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		String retString = "";
		
		for (int y = board.size() - 1; y >= 0 ; y--) {
			ArrayList<Space> currentRow = board.get(y);
			
			retString += currentRow.get(0).toString();
			for (int x = 1; x < currentRow.size(); x++) {
				retString += "|" + currentRow.get(x);
			}
			
			if (y != 0)
				retString += "\n";
		}
		
		
		return retString;
	}
}
