package unsorted;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Board {
	
	private ArrayList<ArrayList<Space>> board;		// The "outer layer" of ArrayList is the y coordinate (the rows of the board) while the "inner layer" is for the actual pieces on that piece of the board 
	private static Piece wallPiece = new Piece(PieceType.WALL);
	private final int xDimension, yDimension;
	private HashMap<Piece, int[]> placedPieces;			// Maps currently placed pieces to a coordinate pair of (xCoordinate, yCoordinate)
	private HashMap<Piece, int[]> solutionPlacement;	// Maps where all pieces are meant to go for solution of this puzzle
	private HashSet<Piece> solutionPieces;			// List of all pieces part of the solution 
	private boolean easyMode;
	
	/* Constructors */
	public Board(int xDimension, int yDimension) {
		this.xDimension = xDimension;
		this.yDimension = yDimension;
		easyMode = true;
		board = new ArrayList<ArrayList<Space>>();
		placedPieces = new HashMap<Piece, int[]>();
		solutionPlacement = new HashMap<Piece, int[]>();
		solutionPieces = new HashSet<Piece>();
		
		for (int y = 0; y < yDimension; y++) {
			board.add(new ArrayList<Space>());
			ArrayList<Space> currentRow = board.get(y);
			for (int x = 0; x < xDimension; x++) {
				currentRow.add(new Space());
				/*
				if (y == 0 || y == yDimension - 1 || x == 0 || x == xDimension - 1)
					currentRow.add(new Space(wallPiece));
				else
					currentRow.add(new Space());
					*/
			}
		}
	}
	
	
	public static Board generateRandomLevel(int xDimension, int yDimension, double wallPercent) {		// Generates a fully randomized level, both walls and pieces are randomly placed
		Board level = new Board(xDimension, yDimension);
		Random r = new Random();
		int wallChance = (int) Math.round(wallPercent * 100);
		for (int y = 0; y < yDimension; y++) {
			for (int x = 0; x < xDimension; x++) {
				if (r.nextInt(100) < wallChance) {
					level.placeWall(x, y);
				}
			}
		}
		
		randomizePieces(level);
		
		return level;
	}
	
	public static void randomizePieces(Board level) {		// Generates a randomized set of pieces on the given board.
		int xDim = level.xDimension;
		int yDim = level.yDimension;

		Random r = new Random();
		
		//ArrayList<ArrayList<Space>> levelBoard = level.board;
		
		for (int y = 0; y < yDim; y++) {
			//ArrayList<Space> row = levelBoard.get(y);
			for (int x = 0; x < xDim; x++) {
				if (!level.spaceIsEmpty(x, y) || level.spaceIsGuarded(x, y))
					continue;
				
				int num = r.nextInt(6);		// Generate a random piece and try to place it, cycle through all possible pieces and see if they can be placed here
				for (int offset = 0; offset <= 5; offset++) {
					Piece nextPiece = new Piece(num + offset);
					level.placePiece(nextPiece, x, y);
					if (level.isValidSolution())
						break;
					else
						level.removePiece(x, y);
				}
			}
		}
		
		level.setSolution(level.placedPieces);
		level.resetBoard();
	}
	
	
	public void setSolution(HashMap<Piece, int[]> possibleSolution) {
		//TODO: Check that the solution works, for now it will just take it as the true solution
		for (Piece piece: possibleSolution.keySet()) {
			int[] coords = placedPieces.get(piece);
			solutionPlacement.put(piece, coords);
			solutionPieces.add(piece);
		}
	}
	
	
	public void resetBoard() {
		for (Piece piece: solutionPlacement.keySet()) {
			int[] coords = placedPieces.get(piece);
			if (coords != null) {
				removePiece(coords[0], coords[1]);
			}
		}
	}
	
	
	/* Other manipulation */
	public void setEasy() {
		easyMode = true;
	}
	
	public void setHard() {
		easyMode = false;
	}
	
	
	/* Board manipulation methods */
	public void placeWall(int xCoordinate, int yCoordinate) {
		board.get(yCoordinate).get(xCoordinate).placePiece(wallPiece);
	}
	
	public void placeBoxWalls(int xCoordinate1, int yCoordinate1, int xCoordinate2, int yCoordinate2) {
		for (int x = xCoordinate1; x <= xCoordinate2; x++) {
			for (int y = yCoordinate1; y <= yCoordinate2; y++)
				this.placeWall(x, y);
		}
	}
	
	public boolean placePiece(Piece piece, int xCoordinate, int yCoordinate) {
		/**WIP
		 * @param 
		 * @return the value returned by the method
		 * @throws what kind of exception does this method throw
		 * 
		 */
		
		if (yCoordinate < 0 || yCoordinate >= yDimension || xCoordinate < 0 || xCoordinate >= xDimension) {
			System.out.println("Coordinate provided to placePiece is out of bounds. Coordinates provided: " + xCoordinate + ", " + yCoordinate);
			return false;
		}
		else if (board.get(yCoordinate).get(xCoordinate).hasPiece()) {
			System.out.println("Coordinate provided already has piece placed on it");
			return false;
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
			return false;
			
		default:
			System.out.println("Problem in Board.placePiece(Piece piece, int xCoordinate, int yCoordinate)");
			return false;
		}
		
		board.get(yCoordinate).get(xCoordinate).placePiece(piece);
		int[] coordinates = {xCoordinate, yCoordinate};
		placedPieces.put(piece, coordinates);
		return true;
	}
	
	public void removePiece(int xCoordinate, int yCoordinate) {
		
		if (yCoordinate < 0 || yCoordinate >= yDimension || xCoordinate < 0 || xCoordinate >= xDimension) {
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
		//int[] coordinates = {xCoordinate, yCoordinate};
		//placedPieces.remove(piece, coordinates);
		placedPieces.remove(piece);
	}

	/*
	public void removeWall(int xCoordinate, int yCoordinate) {
		board.get(yCoordinate).get(xCoordinate).placePiece(wallPiece);
	}
	*/
	
	
	/* Guard setting */
	private void guardCardinal(Piece piece, int xCoordinate, int yCoordinate) {
		ArrayList<Space> row = board.get(yCoordinate);
		for (int x = xCoordinate + 1; x < xDimension; x++) {		// Guard across in the +x direction
			if (spaceIsGuardable(x, yCoordinate)) {
				row.get(x).addGuardingPiece(piece);
			} else {
				break;
			}
		}
		
		for (int x = xCoordinate - 1; x >= 0; x--) {					// Guard across in the -x direction
			if (spaceIsGuardable(x, yCoordinate)) {
				row.get(x).addGuardingPiece(piece);
			} else {
				break;
			}
		}
		
		for (int y = yCoordinate + 1; y < yDimension; y++) {		// Guard across in the +y direction
			if (spaceIsGuardable(xCoordinate, y)) {
				board.get(y).get(xCoordinate).addGuardingPiece(piece);
			} else {
				break;
			}
		}
		
		for (int y = yCoordinate - 1; y >= 0; y--) {					// Guard across in the -y direction
			if (spaceIsGuardable(xCoordinate, y)) {
				board.get(y).get(xCoordinate).addGuardingPiece(piece);
			} else {
				break;
			}
		}
	}
	
	private void guardIntercardinal(Piece piece, int xCoordinate, int yCoordinate) {
		int offset = 1;
		while(true) {		// Guard across in the +x +y diagonal
			int nextX = xCoordinate + offset;
			int nextY = yCoordinate + offset;
			if (spaceIsGuardable(nextX, nextY)) {
				board.get(nextY).get(nextX).addGuardingPiece(piece);
			} else {
				break;
			}
			offset++;
		}
		
		offset = 1;
		while(true) {		// Guard across in the +x -y diagonal
			int nextX = xCoordinate + offset;
			int nextY = yCoordinate - offset;
			if (spaceIsGuardable(nextX, nextY)) {
				board.get(nextY).get(nextX).addGuardingPiece(piece);
			} else {
				break;
			}
			offset++;
		}

		offset = 1;
		while(true) {		// Guard across in the -x +y diagonal
			int nextX = xCoordinate - offset;
			int nextY = yCoordinate + offset;
			if (spaceIsGuardable(nextX, nextY)) {
				board.get(nextY).get(nextX).addGuardingPiece(piece);
			} else {
				break;
			}
			offset++;
		}

		offset = 1;
		while(true) {		// Guard across in the -x -y diagonal
			int nextX = xCoordinate - offset;
			int nextY = yCoordinate - offset;
			if (spaceIsGuardable(nextX, nextY)) {
				board.get(nextY).get(nextX).addGuardingPiece(piece);
			} else {
				break;
			}
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
	
	private void guardAdjacent(Piece piece, int xCoordinate, int yCoordinate) {		// Guards ALL adjacent spaces
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
	
	private void guardAdjacentDiagonal(Piece piece, int xCoordinate, int yCoordinate) {		// Guards only the diagonal adjacent spaces
		int [][] possibleAdjacentDiagonalMoves = {
				{xCoordinate + 1, yCoordinate + 1},
				{xCoordinate + 1, yCoordinate - 1},
				{xCoordinate - 1, yCoordinate + 1},
				{xCoordinate - 1, yCoordinate - 1},
		};
		
		setPossibleGuards(piece, possibleAdjacentDiagonalMoves);
	}
	
	private void setPossibleGuards(Piece piece, int[][] possibleMoves) {		// Filters through possibleMoves and sets them as guarded if they are a valid coordinate
		for (int i = 0; i < possibleMoves.length; i++) {
			int[] nextCoordinate = possibleMoves[i];
			if (nextCoordinate[0] >= 0 && nextCoordinate[0] < xDimension && nextCoordinate[1] >= 0 && nextCoordinate[1] < yDimension) {
				board.get(nextCoordinate[1]).get(nextCoordinate[0]).addGuardingPiece(piece);
			}
		}
	}
	
	
	/* Remove guard setting */
	private void removeGuardCardinal(Piece piece, int xCoordinate, int yCoordinate) {
		ArrayList<Space> row = board.get(yCoordinate);
		for (int x = xCoordinate + 1; x < xDimension; x++) {		// Remove guard across in the +x direction
			if (spaceIsGuardable(x, yCoordinate)) {
				row.get(x).removeGuardingPiece(piece);
			} else {
				break;
			}
		}
		
		for (int x = xCoordinate - 1; x >= 0; x--) {					// Remove guard across in the -x direction
			if (spaceIsGuardable(x, yCoordinate)) {
				row.get(x).removeGuardingPiece(piece);
			} else {
				break;
			}
		}
		
		for (int y = yCoordinate + 1; y < yDimension; y++) {		// Remove guard across in the +y direction
			if (spaceIsGuardable(xCoordinate, y)) {
				board.get(y).get(xCoordinate).removeGuardingPiece(piece);
			} else {
				break;
			}
		}
		
		for (int y = yCoordinate - 1; y >= 0; y--) {					// Remove guard across in the -y direction
			if (spaceIsGuardable(xCoordinate, y)) {
				board.get(y).get(xCoordinate).removeGuardingPiece(piece);
			} else {
				break;
			}
		}
	}
	
	private void removeGuardIntercardinal(Piece piece, int xCoordinate, int yCoordinate) {
		int offset = 1;
		while(true) {		// Remove guard across in the +x +y diagonal
			int nextX = xCoordinate + offset;
			int nextY = yCoordinate + offset;
			if (spaceIsGuardable(nextX, nextY)) {
				board.get(nextY).get(nextX).removeGuardingPiece(piece);
			} else {
				break;
			}
			offset++;
		}

		offset = 1;
		while(true) {		// Remove guard across in the +x -y diagonal
			int nextX = xCoordinate + offset;
			int nextY = yCoordinate - offset;
			if (spaceIsGuardable(nextX, nextY)) {
				board.get(nextY).get(nextX).removeGuardingPiece(piece);
			} else {
				break;
			}
			offset++;
		}

		offset = 1;
		while(true) {		// Remove guard across in the -x +y diagonal
			int nextX = xCoordinate - offset;
			int nextY = yCoordinate + offset;
			if (spaceIsGuardable(nextX, nextY)) {
				board.get(nextY).get(nextX).removeGuardingPiece(piece);
			} else {
				break;
			}
			offset++;
		}

		offset = 1;
		while(true) {		// Remove guard across in the -x -y diagonal
			int nextX = xCoordinate - offset;
			int nextY = yCoordinate - offset;
			if (spaceIsGuardable(nextX, nextY)) {
				board.get(nextY).get(nextX).removeGuardingPiece(piece);
			} else {
				break;
			}
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
			if (nextCoordinate[0] >= 0 && nextCoordinate[0] < xDimension && nextCoordinate[1] >= 0 && nextCoordinate[1] < yDimension) {
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
	
	public boolean spaceIsGuardable(int xCoordinate, int yCoordinate) {
		if (xCoordinate < 0 || xCoordinate >= xDimension || yCoordinate < 0 || yCoordinate >= yDimension)
			return false;
		else {
			Space position = board.get(yCoordinate).get(xCoordinate);
			return !(position.hasPiece() && position.getPiece().getType() == PieceType.WALL);
		}
	}
	
	public boolean isValidSolution() {
		for (Piece piece: placedPieces.keySet()) {
			
			if (spaceIsGuarded(placedPieces.get(piece)[0], placedPieces.get(piece)[1]))
				return false;
		}
		
		return true;
	}
	
	
	/* Get Methods */
	public Piece getPieceAtPosition(int xCoordinate, int yCoordinate) {
		return board.get(yCoordinate).get(xCoordinate).getPiece();
	}
	
	public HashMap<Piece, int[]> getPlacedPieces() {
		return placedPieces;
	}
	
	public HashMap<Piece, int[]> getSolutionMap() {
		return solutionPlacement;
	}
	
	public HashSet<Piece> getSolutionPieces() {
		HashSet<Piece> holder = new HashSet<Piece>();
		for (Piece piece: solutionPieces)
			holder.add(piece);
		return holder;
	}
	
	public int getWidth() {
		return xDimension;
	}
	
	public int getLength() {
		return yDimension;
	}
	
	@Override
	public String toString() {
		String retString = "";
		
		for (int y = board.size() - 1; y >= 0 ; y--) {
			ArrayList<Space> currentRow = board.get(y);
			
			//retString += currentRow.get(0).toString();
			
			
			
			for (int x = 0; x < currentRow.size(); x++) {
				String nextSpace = currentRow.get(x).toString();
				
				if (!easyMode && nextSpace.equals("x")) {
					nextSpace = "o";
				}
				
				if (x == 0)
					retString += nextSpace;
				else
					retString += "|" + nextSpace;
			}
			
			if (y != 0)
				retString += "\n";
		}
		
		
		return retString;
	}
}
