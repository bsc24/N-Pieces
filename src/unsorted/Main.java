package unsorted;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
	
	static Scanner input;
	static Board board;
	static HashSet<Piece> pieces;

	public static void main(String[] args) {
		input = new Scanner(System.in);
		System.out.println("N-Pieces\nBy Brandon Chin\n");
		
		System.out.print("Enter width of board: ");
		int width = input.nextInt();
		
		System.out.print("Enter height of board: ");
		int height = input.nextInt();
		
		System.out.print("Enter percent chance for wall per tile (0-1 decimal): ");
		double wallPercent = input.nextDouble();
		
		input.nextLine();
		board = Board.generateRandomLevel(width, height, wallPercent);
		pieces = board.getSolutionPieces();
		
		while (true) {
			System.out.println(board);
			
			System.out.println("What action would you like to take?");
			String action = input.nextLine();
			
			switch(action) {
			case "check":
			case "c":
				if (board.isValidSolution() && pieces.isEmpty()) {
					System.out.println("woo");
					input.nextLine();
					System.exit(1);
				} else {
					System.out.println("Still needs a bit more work to complete.");
				}
				break;
			case "forfeit":
			case "ff":
				//TODO: Forfeit the game
				break;
			case "help":
			case "h":
				help();
				break;
			case "move":
			case "m":
				move();
				break;
			case "place":
			case "p":
				place();
				break;
			case "remove":
			case "rm":
				remove();
				break;
			case "reset":
			case "r":
				board.resetBoard();
				pieces = board.getSolutionPieces();
				break;
			default:
				System.out.println("Unknown command. \"h\" or \"help\" for commands.");
			}
			
			System.out.println();
		}
		
	}
	
	public static Piece findPiece(PieceType type, HashSet<Piece> piecesList) {
		for(Piece piece: piecesList) {
			if (piece.getType() == type) {
				return piece;
			}
		}
		return null;
	}
	
	/*	// Might not need this one
	public static boolean check() {
		return board.isValidSolution();
	}
	*/
	
	public static int[] promptPlayerCoords() {		//TODO: Check that the provided coordinates are valid?
		int[] coords = new int[2];
		System.out.print("Enter an x-coordinate: ");
		coords[0] = input.nextInt() - 1;
		
		System.out.print("Enter a y-coordinate: ");
		coords[1] = input.nextInt() - 1;
		
		input.nextLine();
		return coords;
		
	}
	
	
	public static void help() {
		System.out.println(
				"Commands:\n"
				+ "\tc, check\tCheck whether the game has been completed.\n"
				+ "\tff, forfeit\tGive up on the current puzzle.\n"
				+ "\th, help\t\tDisplay commands.\n"
				+ "\tm, move\t\tSelect a piece on the board and move it to a new position.\n"
				+ "\tp, place\tSelect a not yet placed piece onto the board.\n"
				+ "\trm, remove\tSelect a piece on the board to remove.\n"
				+ "\tr, reset\tReset the current puzzle."
		);
	}
	
	public static void move() {		// Needs to be tested
		System.out.println("Select a piece to move.");
		int[] origin = promptPlayerCoords();
		Piece piece = board.getPieceAtPosition(origin[0], origin[1]);
		
		if (piece == null) {
			System.out.println("No piece at provided position.");
			return;
		} else if (piece.getType() == PieceType.WALL) {
			System.out.println("Cannot move wall pieces.");
			return;
		}

		System.out.println("Select a destination.");
		int[] destination = promptPlayerCoords();
		
		if (board.placePiece(piece, destination[0], destination[1])) {
			board.removePiece(origin[0], origin[1]);
		}
	}
	
	public static void place() {		// Needs to be tested
		System.out.print("Select a piece to place: ");
		for (Piece piece: pieces) {
			System.out.print(piece + " ");
		}
		System.out.println();
		String wantedPiece = input.nextLine();
		Piece selectedPiece = null;
		
		switch(wantedPiece.toLowerCase()) {
		case "p":
			selectedPiece = findPiece(PieceType.PAWN, pieces);
			break;
		case "b":
			selectedPiece = findPiece(PieceType.BISHOP, pieces);
			break;
		case "n":
			selectedPiece = findPiece(PieceType.KNIGHT, pieces);
			break;
		case "r":
			selectedPiece = findPiece(PieceType.ROOK, pieces);
			break;
		case "q":
			selectedPiece = findPiece(PieceType.QUEEN, pieces);
			break;
		case "k":
			selectedPiece = findPiece(PieceType.KING, pieces);
			break;
		default:
			System.out.println("Invalid piece provided.");
			return;
		}
		
		if (selectedPiece == null) {
			System.out.println("No pieces remaining of that type.");
			return;
		}
		
		int[] coords = promptPlayerCoords();
		
		if (board.placePiece(selectedPiece, coords[0], coords[1])) {
			pieces.remove(selectedPiece);
		}
	}
	
	public static void remove() {		// Needs to be tested
		System.out.println("Select a piece to remove.");
		int[] coords = promptPlayerCoords();
		Piece piece = board.getPieceAtPosition(coords[0], coords[1]);
		
		if (piece == null) {
			System.out.println("No piece at provided position.");
			return;
		} else if (piece.getType() == PieceType.WALL) {
			System.out.println("Cannot remove wall pieces.");
			return;
		}
		
		board.removePiece(coords[0], coords[1]);
		pieces.add(piece);
	}
	

}
