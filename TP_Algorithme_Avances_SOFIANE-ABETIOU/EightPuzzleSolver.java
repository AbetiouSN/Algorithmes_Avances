import java.util.*;

public class EightPuzzleSolver {
    private static Map<String, String> moveMeanings = new HashMap<>();
    static {
        moveMeanings.put("-1,0", "Move up");
        moveMeanings.put("1,0", "Move down");
        moveMeanings.put("0,-1", "Move left");
        moveMeanings.put("0,1", "Move right");
    }

    public static void main(String[] args) {
        int[][] initial_state = {
                {2, 8, 3},
                {1, 6, 4},
                {7, 0, 5}
        };

        int[][] goal_state = {
                {1, 2, 3},
                {8, 0, 4},
                {7, 6, 5}
        };

        PuzzleNode solution = solvePuzzle(initial_state, goal_state);
        if (solution != null) {
            printSolution(solution);
        } else {
            System.out.println("No solution found.");
        }
    }

    private static PuzzleNode solvePuzzle(int[][] initial_state, int[][] goal_state) {
        Set<PuzzleNode> visited = new HashSet<>();
        PriorityQueue<PuzzleNode> queue = new PriorityQueue<>();
        PuzzleNode startNode = new PuzzleNode(initial_state, null, null, 0);
        queue.offer(startNode);

        while (!queue.isEmpty()) {
            PuzzleNode currentNode = queue.poll();
            visited.add(currentNode);

            if (Arrays.deepEquals(currentNode.puzzle, goal_state)) {
                return currentNode;
            }

            for (int[] move : currentNode.getPossibleMoves()) {
                PuzzleNode newNode = currentNode.makeMove(move);
                if (!visited.contains(newNode)) {
                    queue.offer(newNode);
                }
            }
        }

        return null;
    }

    private static void printSolution(PuzzleNode solutionNode) {
        List<int[]> moves = new ArrayList<>();
        PuzzleNode currentNode = solutionNode;
        while (currentNode.parent != null) {
            moves.add(currentNode.move);
            currentNode = currentNode.parent;
        }
        Collections.reverse(moves);
        System.out.println("Solution:");
        for (int[] move : moves) {
            String moveKey = move[0] + "," + move[1];
            System.out.println("Move: " + moveKey + " - " + moveMeanings.get(moveKey));
        }
    }
}