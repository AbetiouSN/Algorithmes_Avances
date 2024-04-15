public class reines_DFS {
//profondeur
    private static boolean isSafe(int[][] board, int row, int col) {
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

    private static boolean solveNQueens(int[][] board, int col) {
        if (col >= board.length) {
            return true;
        }

        for (int i = 0; i < board.length; i++) {
            if (isSafe(board, i, col)) {
                board[i][col] = 1;

                if (solveNQueens(board, col + 1)) {
                    return true;
                }

                board[i][col] = 0;
            }
        }

        return false;
    }

    private static void printSolution(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] board = new int[4][4];

        if (!solveNQueens(board, 0)) {
            System.out.println("Pas de solution");
            return;
        }

        System.out.println("Solution trouvée :");
        printSolution(board);
    }
}

