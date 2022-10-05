import java.util.Vector;

public class Game {
    State b;

    public Game() {
        b = new State();
//        b.read("data/board.txt");
        b.read("Assignment1/data/board.txt");

    }

    public void test() {
        State minimax = minimax(b, b.turn, 3, 0);
        System.out.println("Best move to do for " + b.turn + " is " + minimax.moves);

//        State alfabeta = alfabeta(b, b.turn, 13, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
//        System.out.println("Best move to do for "+ b.turn+ " is "+ alfabeta.moves);

//        while (!b.isLeaf()) {
//            System.out.println(b.toString());
//            System.out.println("Legal moves for agent with turn:" + b.legalMoves());
//            b.execute(b.legalMoves().get((int) (Math.random() * b.legalMoves().size())));
//        }
    }

    public State minimax(State s, int forAgent, int maxDepth, int depth) {
        double boardVal = s.value(forAgent);
        boolean leafReached = s.isLeaf();

        if (s.moves.size() < 1){
            for(int i = 0;i < maxDepth; i++){
                s.moves.add(null);
            }
        }

        if (depth == maxDepth || leafReached == true || boardVal == 1.0 || boardVal==-1) {
            return s;
        }
        s.turn = forAgent;

        if (forAgent == 0) {
            int max = Integer.MIN_VALUE;

            State newState = new State();
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
                    System.out.println("NewState moves max: " + newState.moves);
                    if (newState.moves.isEmpty()){
                        newState = currentStateMax.copy();
                    }
//                    newState.moves.add(currentStateMax.moves.get(0));
//                    System.out.println("Depth: " + depth);
//                    System.out.println("Executed move: " + move);
                    newState.moves.set(depth, move);
                    System.out.println(newState.moves);
                }
            }
            return newState;
        } else {
            int min = Integer.MAX_VALUE;
            State newState = new State();
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
                min = Math.max(min, (int) currentStateMin.value(forAgent));
                if (currentStateValue <= min) {
                    System.out.println("NewState moves min: " + newState.moves);
                    if (newState.moves.isEmpty()){
                        newState = currentStateMin.copy();
                    }
//                    newState.moves.add(currentStateMin.moves.get(0));
//                    System.out.println(s.moves);
//                    String savingMove = currentStateMin.moves.get(0);
                    newState.moves.set(depth, move);
                    System.out.println(newState.moves);
                }
            }
            return newState;
        }
    }

    public State alfabeta(State s, int forAgent, int maxDepth, int depth, double alfa, double beta) {
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
                int max = Integer.MIN_VALUE;

                stateCopy.execute(move);
                currentStateMax = alfabeta(stateCopy, 1, maxDepth, depth + 1, alfa, beta);
                max = Math.max(max, (int) currentStateMax.value(0));
                alfa = Math.max(alfa, currentStateMax.value(forAgent));
                System.out.println("ALFA: " + alfa);
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
                int min = Integer.MAX_VALUE;

                stateCopy.execute(move);
                currentStateMin = alfabeta(stateCopy, 0, maxDepth, depth + 1, alfa, beta);
                beta = Math.min(beta, currentStateMin.value(forAgent));
                if (alfa <= beta) {
                    return currentStateMin;
                }
            }
            return currentStateMin;
        }
    }
}
