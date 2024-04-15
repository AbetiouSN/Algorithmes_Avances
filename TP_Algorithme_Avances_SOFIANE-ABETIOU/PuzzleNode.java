import java.util.*;

class PuzzleNode implements Comparable<PuzzleNode> {
    int[][] puzzle;
    PuzzleNode parent;
    int[] move;
    int depth;
    int heuristic;
    int priority;

    PuzzleNode(int[][] puzzle, PuzzleNode parent, int[] move, int depth) {
        this.puzzle = puzzle;
        this.parent = parent;
        this.move = move;
        this.depth = depth;
        this.heuristic = calculateHeuristic();
        this.priority = this.depth + this.heuristic;
    }

    @Override
    public int compareTo(PuzzleNode other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PuzzleNode other = (PuzzleNode) obj;
        return Arrays.deepEquals(this.puzzle, other.puzzle);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.puzzle);
    }

    private int calculateHeuristic() {
        int n = this.puzzle.length;
        int totalDistance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.puzzle[i][j] != 0) {
                    int value = this.puzzle[i][j];
                    int goalRow = (value - 1) / n;
                    int goalCol = (value - 1) % n;
                    totalDistance += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return totalDistance;
    }

    private int[] getBlankPosition() {
        int n = this.puzzle.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.puzzle[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public List<int[]> getPossibleMoves() {
        List<int[]> moves = new ArrayList<>();
        int n = this.puzzle.length;
        int[] blankPosition = getBlankPosition();
        int i = blankPosition[0];
        int j = blankPosition[1];
        if (i > 0) {
            moves.add(new int[]{-1, 0}); // Move up
        }
        if (i < n - 1) {
            moves.add(new int[]{1, 0}); // Move down
        }
        if (j > 0) {
            moves.add(new int[]{0, -1}); // Move left
        }
        if (j < n - 1) {
            moves.add(new int[]{0, 1}); // Move right
        }
        return moves;
    }

    PuzzleNode makeMove(int[] move) {
        int[] blankPosition = getBlankPosition();
        int i = blankPosition[0];
        int j = blankPosition[1];
        int[][] newPuzzle = Arrays.stream(this.puzzle).map(int[]::clone).toArray(int[][]::new);
        int di = move[0];
        int dj = move[1];
        int newI = i + di;
        int newJ = j + dj;
        int temp = newPuzzle[i][j];
        newPuzzle[i][j] = newPuzzle[newI][newJ];
        newPuzzle[newI][newJ] = temp;
        return new PuzzleNode(newPuzzle, this, move, this.depth + 1);
    }
}