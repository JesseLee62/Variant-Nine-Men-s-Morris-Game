import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MiniMaxOpeningBlack {

	public static int leaf = 0;
	public static char[] finalBoard;

	public static int minMax(char[] board, int depth, int goalDepth) {
		int v = 0;
		if (depth == goalDepth) {
			leaf++;
			return staticEstimationForOpening(board);
		}

		if (depth % 2 == 0) {
			ArrayList<char[]> childList = generateAddB(board);
			v = 10000;
			for (int i = 0; i < childList.size(); i++) {
				board = childList.get(i);
				int temp = minMax(board, depth + 1, goalDepth);
				if (v > temp) {
					v = temp;
					if (depth == 0) {
						finalBoard = board;
					}
				}
			}
		} else {
			ArrayList<char[]> childList = generateAdd(board);
			v = -10000;
			for (int i = 0; i < childList.size(); i++) {
				board = childList.get(i);
				v = Math.max(v, minMax(board, depth + 1, goalDepth));
			}
		}
		return v;
	}

	public static ArrayList<char[]> generateAdd(char[] board) {
		ArrayList<char[]> list = new ArrayList<>();
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 'x') {
				char[] b = board.clone();
				b[i] = 'W';
				if (closeMill(i, b)) {
					list = generateRemove(b, list);
				} else {
					list.add(b);
				}
			}
		}
		return list;
	}

	public static ArrayList<char[]> generateAddB(char[] board) {
		ArrayList<char[]> list = new ArrayList<>();
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 'x') {
				char[] b = board.clone();
				b[i] = 'B';
				if (closeMill(i, b)) {
					list = generateRemoveB(b, list);
				} else {
					list.add(b);
				}
			}
		}
		return list;
	}

	public static ArrayList<char[]> generateRemove(char[] board, ArrayList<char[]> list) {
		int n = list.size();

		for (int i = 0; i < board.length; i++) {
			char[] b = new char[board.length];
			if (board[i] == 'B') {
				if (!closeMill(i, board)) {
					b = board.clone();
					b[i] = 'x';
					list.add(b);
				}
			}
		}
		if (n == list.size()) {
			list.add(board);
		}
		return list;
	}

	public static ArrayList<char[]> generateRemoveB(char[] board, ArrayList<char[]> list) {
		int n = list.size();

		for (int i = 0; i < board.length; i++) {
			char[] b = new char[board.length];
			if (board[i] == 'W') {
				if (!closeMill(i, board)) {
					b = board.clone();
					b[i] = 'x';
					list.add(b);
				}
			}
		}
		if (n == list.size()) {
			list.add(board);
		}
		return list;
	}

	public static boolean closeMill(int j, char[] board) {
		char C = board[j];
		if (C == 'x') {
			return false;
		}
		switch (j) {
		case 0:
			if ((board[1] == C && board[2] == C) || (board[3] == C && board[6] == C)) {
				return true;
			} else {
				return false;
			}
		case 1:
			if (board[0] == C && board[2] == C) {
				return true;
			} else {
				return false;
			}
		case 2:
			if ((board[0] == C && board[1] == C) || (board[5] == C && board[7] == C)
					|| (board[12] == C && board[21] == C)) {
				return true;
			} else {
				return false;
			}
		case 3:
			if ((board[0] == C && board[6] == C) || (board[4] == C && board[5] == C)
					|| (board[8] == C && board[16] == C)) {
				return true;
			} else {
				return false;
			}
		case 4:
			if (board[3] == C && board[5] == C) {
				return true;
			} else {
				return false;
			}
		case 5:
			if ((board[3] == C && board[4] == C) || (board[11] == C && board[18] == C)
					|| (board[2] == C && board[7] == C)) {
				return true;
			} else {
				return false;
			}
		case 6:
			if ((board[0] == C && board[3] == C) || (board[9] == C && board[13] == C)) {
				return true;
			} else {
				return false;
			}
		case 7:
			if ((board[2] == C && board[5] == C) || (board[10] == C && board[15] == C)) {
				return true;
			} else {
				return false;
			}
		case 8:
			if (board[3] == C && board[16] == C) {
				return true;
			} else {
				return false;
			}
		case 9:
			if (board[6] == C && board[13] == C) {
				return true;
			} else {
				return false;
			}
		case 10:
			if ((board[7] == C && board[15] == C) || (board[11] == C && board[12] == C)) {
				return true;
			} else {
				return false;
			}
		case 11:
			if ((board[10] == C && board[12] == C) || (board[5] == C && board[18] == C)) {
				return true;
			} else {
				return false;
			}
		case 12:
			if ((board[10] == C && board[11] == C) || (board[2] == C && board[21] == C)) {
				return true;
			} else {
				return false;
			}
		case 13:
			if ((board[6] == C && board[9] == C) || (board[16] == C && board[19] == C)
					|| (board[14] == C && board[15] == C)) {
				return true;
			} else {
				return false;
			}
		case 14:
			if ((board[13] == C && board[15] == C) || (board[17] == C && board[20] == C)) {
				return true;
			} else {
				return false;
			}
		case 15:
			if ((board[13] == C && board[14] == C) || (board[7] == C && board[10] == C)
					|| (board[18] == C && board[21] == C)) {
				return true;
			} else {
				return false;
			}
		case 16:
			if ((board[13] == C && board[19] == C) || (board[3] == C && board[8] == C)
					|| (board[17] == C && board[18] == C)) {
				return true;
			} else {
				return false;
			}
		case 17:
			if ((board[16] == C && board[18] == C) || (board[14] == C && board[20] == C)) {
				return true;
			} else {
				return false;
			}
		case 18:
			if ((board[16] == C && board[17] == C) || (board[15] == C && board[21] == C)
					|| (board[5] == C && board[11] == C)) {
				return true;
			} else {
				return false;
			}
		case 19:
			if ((board[16] == C && board[13] == C) || (board[20] == C && board[21] == C)) {
				return true;
			} else {
				return false;
			}
		case 20:
			if ((board[19] == C && board[21] == C) || (board[17] == C && board[14] == C)) {
				return true;
			} else {
				return false;
			}
		case 21:
			if ((board[19] == C && board[20] == C) || (board[18] == C && board[15] == C)
					|| (board[12] == C && board[2] == C)) {
				return true;
			} else {
				return false;
			}
		default:
			return false;
		}
	}

	public static int getNumWhitePieces(char[] board) {
		int numWhitePieces = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 'W') {
				numWhitePieces++;
			}
		}
		return numWhitePieces;
	}

	public static int getNumBlackPieces(char[] board) {
		int numBlackPieces = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 'B') {
				numBlackPieces++;
			}
		}
		return numBlackPieces;
	}

	public static int staticEstimationForOpening(char[] board) {
		int numWhitePieces = getNumWhitePieces(board);
		int numBlackPieces = getNumBlackPieces(board);
		return (numWhitePieces - numBlackPieces);

	}

	public static void main(String[] args) {

		String str = args[0];
		String str2 = args[1];
		int depth = Integer.parseInt(args[2]);

		try {
			BufferedReader reader = new BufferedReader(new FileReader(str));
			str = reader.readLine();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		char[] board = str.toCharArray();

		int v = minMax(board, 0, depth);
		System.out.println();

		System.out.print("Board Position: ");
		for (int i = 0; i < finalBoard.length; i++) {
			System.out.print(finalBoard[i]);
		}
		System.out.println();
		System.out.println("Positions evaluated by static estimation: " + leaf + ".");
		System.out.println("MINIMAX estimate: " + v + ".");

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(str2));
			for (int i = 0; i < finalBoard.length; i++) {
				writer.write(finalBoard[i]);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

