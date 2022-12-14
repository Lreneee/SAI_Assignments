import java.util.Vector;

public class Game {
    State b;

    public Game() {
        b = new State();
        b.read("data/board.txt");
//        b.read("Assignment1/data/board.txt");

    }

    public void test() {
//        State minimax = minimax(b, b.turn, 11, 0);
//        System.out.println("Best move to do for " + b.turn + " is " + minimax.moves);

        State alfabeta = alfabeta(b, b.turn, 13, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println("Best move to do for " + b.turn + " is " + alfabeta.moves);

//        while (!b.isLeaf()) {
//            System.out.println(b.toString());
//            System.out.println("Legal moves for agent with turn:" + b.legalMoves());
//            b.execute(b.legalMoves().get((int) (Math.random() * b.legalMoves().size())));
//        }
    }

    public State minimax(State s, int forAgent, int maxDepth, int depth) {
        double boardVal = s.value(forAgent);
        boolean leafReached = s.isLeaf();

        if (depth == maxDepth || leafReached == true || boardVal == 1.0 || boardVal == -1) {
            return s;
        }
        s.turn = forAgent;

        if (forAgent == 0) {
            int max = Integer.MIN_VALUE;

            State bestState = new State();
            Vector<String> legalMoves = s.legalMoves();
            State currentStateMax = new State();

            for (String move : legalMoves) {
                State stateCopy = s.copy();
                stateCopy.turn = forAgent;

                //For debugging
                System.out.println("-------------------------------");
                System.out.println("Legal moves to do: " + legalMoves);

                stateCopy.execute(move);
                stateCopy.toString();

                currentStateMax = minimax(stateCopy, 1, maxDepth, depth + 1);
                double currentStateValue = currentStateMax.value(forAgent);
                max = Math.max(max, (int) currentStateMax.value(forAgent));
                if (currentStateValue >= max) {
                    bestState = currentStateMax.copy();
                }
            }
            return bestState;
        } else {
            int min = Integer.MAX_VALUE;
            State bestState = new State();
            Vector<String> legalMoves = s.legalMoves();
            State currentStateMin = new State();

            for (String move : legalMoves) {
                State stateCopy = s.copy();
                stateCopy.turn = forAgent;

                //For debugging
                System.out.println("-------------------------------");
                System.out.println("Legal moves to do: " + legalMoves);

                stateCopy.execute(move);
                stateCopy.toString();

                currentStateMin = minimax(stateCopy, 0, maxDepth, depth + 1);
                double currentStateValue = currentStateMin.value(forAgent);
                min = Math.min(min, (int) currentStateMin.value(forAgent));
                if (currentStateValue <= min) {
                    bestState = currentStateMin.copy();
                }
            }
            return bestState;
        }
    }

    public State alfabeta(State s, int forAgent, int maxDepth, int depth, double alfa, double beta) {
        double boardVal = s.value(forAgent);
        boolean leafReached = s.isLeaf();

        if (depth == maxDepth || leafReached == true) {
            return s;
        }
        s.turn = forAgent;
        Vector<String> legalMoves = s.legalMoves();


        if (forAgent == 0) {
            int max = Integer.MIN_VALUE;
            State currentStateMax = new State();
            State newState = new State();

            for (String move : legalMoves) {
                State stateCopy = s.copy();
                stateCopy.turn = forAgent;

                stateCopy.execute(move);
                currentStateMax = alfabeta(stateCopy, 1, maxDepth, depth + 1, alfa, beta);
                double currentStateValue = currentStateMax.value(forAgent);
                max = Math.max(max, (int) currentStateMax.value(forAgent));
                if (currentStateValue >= max) {
                    newState = currentStateMax.copy();
                }
                alfa = Math.max(alfa, currentStateMax.value(forAgent));
                if (alfa >= beta) {
                    return newState;
                }
            }
            return newState;
        } else {
            State currentStateMin = new State();
            State newState = new State();

            for (String move : legalMoves) {
                State stateCopy = s.copy();
                stateCopy.turn = forAgent;
                int min = Integer.MAX_VALUE;

                stateCopy.execute(move);
                currentStateMin = alfabeta(stateCopy, 0, maxDepth, depth + 1, alfa, beta);
                double currentStateValue = currentStateMin.value(forAgent);
                min = Math.min(min, (int) currentStateMin.value(forAgent));
                if (currentStateValue <= min) {
                    newState = currentStateMin.copy();
                }
                beta = Math.min(beta, currentStateMin.value(forAgent));
                if (beta <= alfa) {
                    return newState;
                }
            }
            return newState;
        }
    }
}
