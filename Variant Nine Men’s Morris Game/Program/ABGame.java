import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ABGame {

	public static ArrayList<char[]> generateMovesMidgameEndgame(char[] board) {
		if (getNumWhitePieces(board) == 3) {
			return generateHopping(board);
		} else {
			return generateMove(board);
		}
	}

	public static ArrayList<char[]> generateMovesMidgameEndgameB(char[] board) {
		if (getNumBlackPieces(board) == 3) {
			return generateHoppingB(board);
		} else {
			return generateMoveB(board);
		}
	}

	public static int leaf = 0;
	public static char[] finalBoard;

	public static int alphaBeta(char[] board, int depth, int goalDepth, int a, int b) {
		int v = 0;
		if (depth == goalDepth) {
			leaf++;
			return staticEstimationForMidgameEndgame(board);
		}

		if (depth % 2 == 0) {
			ArrayList<char[]> childList = generateMovesMidgameEndgame(board);
			v = -10000;
			for (int i = 0; i < childList.size(); i++) {
				board = childList.get(i);
				int temp = alphaBeta(board, depth + 1, goalDepth, a, b);
				if (v < temp) {
					v = temp;
					if (depth == 0) {
						finalBoard = board;
					}
				}
				if (v >= b) {
					break;
				} else {
					a = Math.max(v, a);
				}
			}
		} else {
			ArrayList<char[]> childList = generateMovesMidgameEndgameB(board);
			v = 10000;
			for (int i = 0; i < childList.size(); i++) {
				board = childList.get(i);
				v = Math.min(v, alphaBeta(board, depth + 1, goalDepth, a, b));
				if (v <= a) {
					break;
				} else {
					b = Math.min(v, b);
				}
			}
		}

		return v;
	}

	public static ArrayList<char[]> generateHopping(char[] board) {
		ArrayList<char[]> list = new ArrayList<>();
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 'W') {
				for (int j = 0; j < board.length; j++) {
					if (board[j] == 'x') {
						char[] b = board.clone();
						b[i] = 'x';
						b[j] = 'W';
						if (closeMill(j, b)) {
							list = generateRemove(b, list);
						} else {
							list.add(b);
						}
					}
				}
			}
		}
		return list;
	}

	public static ArrayList<char[]> generateHoppingB(char[] board) {
		ArrayList<char[]> list = new ArrayList<>();
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 'B') {
				for (int j = 0; j < board.length; j++) {
					if (board[j] == 'x') {
						char[] b = board.clone();
						b[i] = 'x';
						b[j] = 'B';
						if (closeMill(j, b)) {
							list = generateRemove(b, list);
						} else {
							list.add(b);
						}
					}
				}
			}
		}
		return list;
	}

	public static ArrayList<char[]> generateMove(char[] board) {
		ArrayList<char[]> list = new ArrayList<>();
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 'W') {
				List<Integer> n = neighbors(i);
				for (int j = 0; j < n.size(); j++) {
					if (board[n.get(j)] == 'x') {
						char[] b = board.clone();
						b[i] = 'x';
						b[n.get(j)] = 'W';
						if (closeMill(n.get(j), b)) {
							list = generateRemove(b, list);
						} else {
							list.add(b);
						}
					}
				}
			}
		}
		return list;
	}

	public static ArrayList<char[]> generateMoveB(char[] board) {
		ArrayList<char[]> list = new ArrayList<>();
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 'B') {
				List<Integer> n = neighbors(i);
				for (int j = 0; j < n.size(); j++) {
					if (board[n.get(j)] == 'x') {
						char[] b = board.clone();
						b[i] = 'x';
						b[n.get(j)] = 'B';
						if (closeMill(n.get(j), b)) {
							list = generateRemoveB(b, list);
						} else {
							list.add(b);
						}
					}
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

	public static List<Integer> neighbors(int j) {
		List<Integer> list = List.of();
		switch (j) {
		case 0:
			list = List.of(1, 3, 19);
			return list;
		case 1:
			list = List.of(0, 2, 4);
			return list;
		case 2:
			list = List.of(1, 5, 12);
			return list;
		case 3:
			list = List.of(0, 4, 6, 8);
			return list;
		case 4:
			list = List.of(1, 3, 5);
			return list;
		case 5:
			list = List.of(2, 4, 7, 11);
			return list;
		case 6:
			list = List.of(3, 7, 9);
			return list;
		case 7:
			list = List.of(5, 6, 10);
			return list;
		case 8:
			list = List.of(3, 9, 16);
			return list;
		case 9:
			list = List.of(6, 8, 13);
			return list;
		case 10:
			list = List.of(7, 11, 15);
			return list;
		case 11:
			list = List.of(5, 10, 12, 18);
			return list;
		case 12:
			list = List.of(2, 11, 21);
			return list;
		case 13:
			list = List.of(9, 14, 16);
			return list;
		case 14:
			list = List.of(13, 15, 17);
			return list;
		case 15:
			list = List.of(10, 14, 18);
			return list;
		case 16:
			list = List.of(8, 13, 17, 19);
			return list;
		case 17:
			list = List.of(14, 16, 18, 20);
			return list;
		case 18:
			list = List.of(11, 15, 17, 21);
			return list;
		case 19:
			list = List.of(0, 16, 20);
			return list;
		case 20:
			list = List.of(17, 19, 21);
			return list;
		case 21:
			list = List.of(12, 18, 20);
			return list;
		default:
			return list;
		}
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

	public static int staticEstimationForMidgameEndgame(char[] board) {
		int numWhitePieces = getNumWhitePieces(board);
		int numBlackPieces = getNumBlackPieces(board);
		int numBlackMoves = 0;
		ArrayList<char[]> list = generateMoveB(board);
		for (int i = 0; i < list.size(); i++) {
			numBlackMoves++;
		}
		if (numBlackPieces <= 2) {
			return 10000;
		} else if (numWhitePieces <= 2) {
			return -10000;
		} else if (numBlackMoves == 0) {
			return 10000;
		} else {
			return (1000 * (numWhitePieces - numBlackPieces) - numBlackMoves);
		}
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

		int v = alphaBeta(board, 0, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
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

