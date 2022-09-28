public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello World");

        State state = new State();
        state.read("/Users/irenedekoning/SYMBOLIC_AI/SAI_Assignments/Assignment1/data/board.txt");

        String boardString = state.toString();
        System.out.print(boardString);

        System.out.println(state.legalMoves(1));
        //Game g=new Game();
        //g.test();
    }
}
