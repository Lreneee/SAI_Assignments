import java.util.Vector;

public class Game {
    State b;

    public Game() {
        b = new State();
        b.read("data/board.txt");
    }

    public void test() {
        System.out.println(minimax(b, b.turn, 3, 0));

//        while (!b.isLeaf()) {
//            System.out.println(b.toString());
//            System.out.println("Legal moves for agent with turn:" + b.legalMoves());
//            b.execute(b.legalMoves().get((int) (Math.random() * b.legalMoves().size())));
//        }
    }

    public State minimax(State s, int forAgent, int maxDepth, int depth) {
        double boardVal = s.value(forAgent);
        boolean leafReached = s.isLeaf();

        if (depth == maxDepth || leafReached == true || boardVal == 1.0) {
            System.out.println("DEPTH REACHED");
            System.out.println("---------------------------------");
//            s.toString();

            return s;
        }
        s.turn = forAgent;
        Vector<String> legalMoves = s.legalMoves();
        System.out.println(legalMoves);

        if (forAgent == 0) {
            for (String move : legalMoves) {
                State stateCopy = s.copy();
                stateCopy.turn = forAgent;
                stateCopy.execute(move);
                System.out.println(stateCopy.toString());
                if (stateCopy.score[forAgent] > 0) {
//                    System.out.println("FOOD EATEN");
                }
                State currentState = minimax(stateCopy, 1, maxDepth, depth + 1);
            }
//            stateCopy.moves.add(tempBestMove);
        } else {
            for (String move : legalMoves) {
                State stateCopy = s.copy();
                stateCopy.turn = forAgent;
                System.out.println("MINIMIZING PLAYER");
                stateCopy.execute(move);
                System.out.println(stateCopy.toString());
                State currentState = minimax(stateCopy, 0, maxDepth, depth + 1);
            }
//            stateCopy.moves.add(tempBestMove);
        }
        return null;
    }
}
