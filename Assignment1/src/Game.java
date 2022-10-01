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
        if (depth == maxDepth || boardVal ==1.0) {
            System.out.println("DEPTH REACHED");
            s.toString();
            return s;
        }
        Vector<String> legalMoves = s.legalMoves();
        String tempBestMove = null;
        System.out.println(legalMoves);
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

        if(forAgent==0){
            for(String move: legalMoves){
                System.out.println("MAXIMIZING PLAYER");
                s.execute(move);
                State currentState = minimax(s, 1, maxDepth, depth + 1);
                //Iets met een score doen die dus hoger en/of lager moet zijn dan de minimale of maximale waarde

                s.board[(char) s.agentX[s.turn]][(char) s.agentY[s.turn]] = ' ';
            }
            s.moves.add(tempBestMove);
        } else{
            for(String move: legalMoves){
                System.out.println("MINIMIZING PLAYER");
                s.execute(move);
                s.moves.add(move);
                State currentState = minimax(s, 0, maxDepth, depth + 1);
                s.board[(char) s.agentX[s.turn]][(char) s.agentY[s.turn]] = ' ';
            }
            s.moves.add(tempBestMove);
        }
        return null;
    }
    public State evaluateBoard(State s){
        return null;
    }
}
