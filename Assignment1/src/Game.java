import java.util.Vector;

public class Game {
    State b;

    public Game() {
        b = new State();
        b.read("data/board.txt");
    }

    public void test() {
        System.out.println("----------------------");
        System.out.println("First state of board");
        System.out.println(b.toString());
        System.out.println(minimax(b, b.turn, 3, 0));

//        while (!b.isLeaf()) {
//            System.out.println(b.toString());
//            System.out.println("Legal moves for agent with turn:" + b.legalMoves());
//            b.execute(b.legalMoves().get((int) (Math.random() * b.legalMoves().size())));
//        }
    }

    public State minimax(State s, int forAgent, int maxDepth, int depth) {
        State stateCopy = new State();
        stateCopy = s.copy();
        stateCopy.turn = forAgent;
        double boardVal = stateCopy.value(forAgent);
        boolean leafReached = stateCopy.isLeaf();
        
          if (depth == maxDepth || leafReached==true || boardVal==1.0) {
            System.out.println("DEPTH REACHED");
            System.out.println("---------------------------------");
//            s.toString();

            return stateCopy;
        }
        Vector<String> legalMoves = stateCopy.legalMoves();
        System.out.println(legalMoves);
        String tempBestMove = null;

        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

        if(forAgent==0){
            for(String move: legalMoves){
                stateCopy.turn = forAgent;
                System.out.println("MAXIMIZING PLAYER");
                int movePosition = legalMoves.indexOf(move);
//                String lastMove = legalMoves.get(movePosition);
                stateCopy.execute(move);
                System.out.println(stateCopy.toString());
                if(stateCopy.score[forAgent]>0){
                    System.out.println("FOOD EATEN");
                }

                State currentState = minimax(stateCopy, 1, maxDepth, depth + 1);
                System.out.println("LAST MOVE: "+legalMoves.get(movePosition));
                stateCopy.oppositeExecute(move);

                //If new score is higher than other score
                //And the size, the moves it takes, is lower than the last one
//                if(currentState.score[forAgent]>stateCopy.score[s.turn] && currentState.moves.size()<stateCopy.moves.size()){
//                    return currentState;
//                }
            }
            stateCopy.moves.add(tempBestMove);
        } else{
            for(String move: legalMoves){
                stateCopy.turn = forAgent;
                System.out.println("MINIMIZING PLAYER");
                int movePosition = legalMoves.indexOf(move);
//                String lastMove = legalMoves.get(movePosition);
                stateCopy.execute(move);
                System.out.println(stateCopy.toString());
                State currentState = minimax(stateCopy, 0, maxDepth, depth + 1);
                System.out.println("LAST MOVE: "+legalMoves.get(movePosition));
                stateCopy.oppositeExecute(move);
            }
            stateCopy.moves.add(tempBestMove);
        }
        return null;
    }
    public State evaluateBoard(State s){
        return null;
    }
}
