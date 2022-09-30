import java.util.Vector;

public class Game {
    State b;

    public Game() {
        b = new State();
        b.read("data/board.txt");
    }

    public void test() {

        System.out.println(minimax(b, b.turn, 11, 0));

        while (!b.isLeaf()) {
            System.out.println(b.toString());
            System.out.println("Legal moves for agent with turn:" + b.legalMoves());
            b.execute(b.legalMoves().get((int) (Math.random() * b.legalMoves().size())));
        }
    }

    public State minimax(State s, int forAgent, int maxDepth, int depth) {
//        if(depth==maxDepth || s.value(forAgent)==1.0){
        if(depth==maxDepth){
            //Leaf reached or game over
            System.out.println("Game over");
            return null;
        }
        Vector<String> legalMoves = s.legalMoves();
        System.out.println(legalMoves);

        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (String move: legalMoves) {
            s.moves.add(move);
            if (forAgent == 0) {
                System.out.println("AGENT A");
                System.out.println(s.moves);
                s.execute(move);
                State currentState = minimax(s, 1, maxDepth, depth+1);
                int currentScore = currentState.score[forAgent];
                max = Math.max(currentScore, max);
            } else if (forAgent == 1) {
                System.out.println("AGENT B");
                System.out.println(s.moves);
                s.execute(move);
                State currentState = minimax(s, 0, maxDepth, depth+1);
                int currentScore = currentState.score[forAgent];
                min = Math.min(currentScore, min);
            }
        }
        return null;
    }

}
