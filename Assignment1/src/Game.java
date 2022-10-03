import java.util.Vector;

public class Game {
    State b;

    public Game() {
        b = new State();
        b.read("data/board.txt");
    }

    public void test() {
        State minimax = minimax(b, b.turn, 13, 0);
        System.out.println("Best move to do for "+ b.turn+ " is "+ minimax.moves);

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
//            System.out.println("DEPTH REACHED");
//            System.out.println("---------------------------------");

            return s;
        }
        s.turn = forAgent;
        Vector<String> legalMoves = s.legalMoves();
//        System.out.println(legalMoves);

        if (forAgent == 0) {
            State currentStateMax = new State();
            for (String move : legalMoves) {
                State stateCopy = s.copy();
                stateCopy.turn = forAgent;
                stateCopy.execute(move);
//                System.out.println(stateCopy.toString());
                currentStateMax = minimax(stateCopy, 1, maxDepth, depth + 1);
            }
            return currentStateMax;
        } else {
            State currentStateMin = new State();
            for (String move : legalMoves) {
                State stateCopy = s.copy();
                stateCopy.turn = forAgent;
//                System.out.println("MINIMIZING PLAYER");
                stateCopy.execute(move);
//                System.out.println(stateCopy.toString());
                currentStateMin = minimax(stateCopy, 0, maxDepth, depth + 1);
            }
            return currentStateMin;
        }
    }
    public State alfabeta(State s, int forAgent, int maxDepth, int depth, double alfa, double beta){
        double boardVal = s.value(forAgent);
        boolean leafReached = s.isLeaf();

        if (depth == maxDepth || leafReached == true || boardVal == 1.0) {
            return s;
        }
        s.turn = forAgent;
        Vector<String> legalMoves = s.legalMoves();


        if (forAgent == 0) {
            State currentStateMax = new State();
            for (String move : legalMoves) {
                State stateCopy = s.copy();
                stateCopy.turn = forAgent;
                stateCopy.execute(move);
                currentStateMax = minimax(stateCopy, 1, maxDepth, depth + 1);
                if (alfa >= beta) {
                    return currentStateMax;
                }
            }
            return currentStateMax;
        } else {
            State currentStateMin = new State();
            for (String move : legalMoves) {
                State stateCopy = s.copy();
                stateCopy.turn = forAgent;
                stateCopy.execute(move);
                currentStateMin = minimax(stateCopy, 0, maxDepth, depth + 1);
                if (alfa <= beta) {
                    return currentStateMin;
                }
            }
            return currentStateMin;
        }
    }
}
