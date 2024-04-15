import java.util.Random;

public class TicTacToeAlphaBeta {
    private static final int SIZE = 3;
    private static final char EMPTY = ' ';
    private static final char PLAYER = 'X';
    private static final char COMPUTER = 'O';
    private static final Random random = new Random();

    public static void main(String[] args) {
        char[][] board = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        // Appel de la fonction minimax avec élagage alpha-beta pour le joueur COMPUTER
        int[] bestMove = minimax(board, COMPUTER, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println("Computer's Move: Row = " + bestMove[0] + ", Column = " + bestMove[1]);

        // Mise à jour du plateau avec le coup optimal du joueur COMPUTER
        board[bestMove[0]][bestMove[1]] = COMPUTER;
    }

    private static int[] minimax(char[][] board, char player, int alpha, int beta) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = (player == COMPUTER) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Boucle à travers chaque cellule vide sur le plateau
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    // Tentative de placer le symbole du joueur dans cette cellule
                    board[i][j] = player;

                    // Appel récursif de minimax avec élagage alpha-beta pour évaluer ce coup
                    int score = minimaxHelper(board, player, alpha, beta);

                    // Annulation du coup pour le prochain essai
                    board[i][j] = EMPTY;

                    // Mise à jour de l'alpha ou beta et du meilleur score et du meilleur coup si nécessaire
                    if (player == COMPUTER) {
                        if (score > bestScore || (score == bestScore && random.nextBoolean())) {
                            bestScore = score;
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                        alpha = Math.max(alpha, bestScore);
                    } else {
                        if (score < bestScore || (score == bestScore && random.nextBoolean())) {
                            bestScore = score;
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                        beta = Math.min(beta, bestScore);
                    }

                    // Élagage
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }
        return bestMove;
    }

    private static int minimaxHelper(char[][] board, char player, int alpha, int beta) {
        char opponent = (player == COMPUTER) ? PLAYER : COMPUTER;

        // Vérification de l'état du jeu
        char winner = determineWinner(board);
        if (winner != EMPTY) {
            if (winner == COMPUTER) {
                return 1;
            } else if (winner == PLAYER) {
                return -1;
            } else {
                return 0; // Match nul
            }
        }

        // Si le jeu n'est pas terminé, évaluer les coups possibles
        if (player == COMPUTER) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = player;
                        int score = minimaxHelper(board, opponent, alpha, beta);
                        bestScore = Math.max(bestScore, score);
                        alpha = Math.max(alpha, bestScore);
                        board[i][j] = EMPTY;

                        // Élagage
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = player;
                        int score = minimaxHelper(board, opponent, alpha, beta);
                        bestScore = Math.min(bestScore, score);
                        beta = Math.min(beta, bestScore);
                        board[i][j] = EMPTY;

                        // Élagage
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return bestScore;
        }
    }

    private static char determineWinner(char[][] board) {
        // Vérification des lignes
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] != EMPTY && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return board[i][0];
            }
        }
        // Vérification des colonnes
        for (int j = 0; j < SIZE; j++) {
            if (board[0][j] != EMPTY && board[0][j] == board[1][j] && board[0][j] == board[2][j]) {
                return board[0][j];
            }
        }
        // Vérification des diagonales
        if (board[0][0] != EMPTY && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] != EMPTY && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return board[0][2];
        }

        // Aucun gagnant
        return EMPTY;
    }
}
