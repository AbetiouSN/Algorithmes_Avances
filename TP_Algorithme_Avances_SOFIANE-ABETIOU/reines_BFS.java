import java.util.*;

public class reines_BFS {
//largeur
    static class State {
        int[][] board;
        int queenRow;
        int queenCol;

        State(int[][] board, int queenRow, int queenCol) {
            this.board = board;
            this.queenRow = queenRow;
            this.queenCol = queenCol;
        }
    }

    public static boolean isSafe(int[][] board, int row, int col) {
        // Vérifie la rangée horizontale à gauche
        for (int i = 0; i < col; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }

        // Vérifie la diagonale supérieure gauche
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        // Vérifie la diagonale inférieure gauche
        for (int i = row, j = col; i < board.length && j >= 0; i++, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    public static boolean solveNQueens(int n) {
        Queue<State> queue = new LinkedList<>();
        int[][] board = new int[n][n];
        queue.add(new State(board, 0, 0));

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            int[][] currentBoard = currentState.board;
            int currentRow = currentState.queenRow;
            int currentCol = currentState.queenCol;

            if (currentCol >= n) {
                // Solution trouvée
                printSolution(currentBoard);
                return true;
            }

            for (int i = 0; i < n; i++) {
                if (isSafe(currentBoard, i, currentCol)) {
                    int[][] newBoard = new int[n][n];
                    for (int k = 0; k < n; k++) {
                        System.arraycopy(currentBoard[k], 0, newBoard[k], 0, n);
                    }
                    newBoard[i][currentCol] = 1;
                    queue.add(new State(newBoard, i, currentCol + 1));
                }
            }
        }

        // Pas de solution
        return false;
    }

    public static void printSolution(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 4; // Taille de l'échiquier
        if (!solveNQueens(n)) {
            System.out.println("Pas de solution trouvée pour " + n + " reines.");
        }
    }
}

