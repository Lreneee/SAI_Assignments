import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class State {
    char[][] board;
    int[] agentX, agentY;
    int[] score;
    int turn;
    int food;
    int boardLine;

    Vector<String> moves;

    public State(){
        moves = new Vector<String>();
        agentY = new int[2];
        agentX = new int[2];
        score = new int[2];
        turn = 0;
        boardLine = 0;
    }

    public void read(String filePath) {
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                defineStaticVariables(data, boardLine);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    int boardWith, boardHeight;

    public void defineStaticVariables(String data, int boardLine) {
        if (data.startsWith("#")) {
            char[] charCharacters = data.toCharArray();
            if (boardLine < boardHeight) {
                for (int col = 0; col < boardWith; col++) {
                    this.board[boardLine][col] = charCharacters[col];

                    //Initialize agent positions
                    if(charCharacters[col] == 'A'){
                        this.agentX[0] = boardLine;
                        this.agentY[0] = col;
                    } else if(charCharacters[col] == 'B'){
                        this.agentX[1] = boardLine;
                        this.agentY[1] = col;
                    } else if(charCharacters[col] == '*'){
                        this.food++;
                    }
                    //System.out.print(this.board[boardLine][col]);
                }
            }
            this.boardLine = this.boardLine + 1;
        } else {
            //Read board width and height
            String[] dataObject = data.split(" ", 0);
            boardWith = Integer.parseInt(dataObject[0]);
            boardHeight = Integer.parseInt(dataObject[1]);
            this.board = new char[boardHeight][boardWith]; // 2D integer array with boardHeight rows and boardWith columns

        }
    }

    public String toString() {
        for (int row = 0; row < boardHeight; row++) {
            for (int col = 0; col < boardWith; col++) {
                System.out.print(this.board[row][col]);
            }
            System.out.println();
        }
        String positionString = "";
        positionString += "Positie agent A: ";
        positionString += this.agentX[0] + ", ";
        positionString += this.agentY[0] +"\n";
        positionString +="Positie agent B: ";
        positionString +=this.agentX[1]+ ", ";
        positionString +=this.agentY[1];

        return positionString;
    }

    public State copy() {
        char[][] newBoard = new char[boardHeight][boardWith];
        int[] newAgentX, newAgentY, score;
        Vector<String> newMoves  = new Vector<String>();

        for (int row = 0; row < boardHeight; row++){
            for (int col = 0; col < boardWith; col++){
                newBoard[row][col] = this.board[row][col];
            }
        }
        newAgentX = new int[]{this.agentX[0], this.agentX[1]};
        newAgentY = new int[]{this.agentY[0], this.agentY[1]};
        score = new int[]{this.score[0], this.score[1]};
        for(int i=0; i<this.moves.size(); i++){
            newMoves.add(this.moves.get(i));
        }

        State newState = new State();
        newState.board = newBoard;
        newState.boardWith = this.boardWith;
        newState.boardHeight = this.boardHeight;
        newState.agentX = newAgentX;
        newState.agentY = newAgentY;
        newState.score = score;
        newState.food = this.food;
        newState.moves = newMoves;
        return newState;
    }

    public Vector<String> legalMoves(int agent) {
        Vector<String> possibleActions = new Vector<String>();
        char row = (char)this.agentX[agent];
        char column = (char)this.agentY[agent];
        if (board[row][column-1] != '#') {
            possibleActions.add("LEFT");
        }if (board[row][column] == '*') {
            possibleActions.add("EAT");
        }if (board[row][column+1] != '#') {
            possibleActions.add("RIGHT");
        }if (board[row - 1][column] != '#') {
            possibleActions.add("UP");
        }if (board[row + 1][column] != '#') {
            possibleActions.add("DOWN");
        }if (board[row][column] == ' ' ||board[row][column] == 'A' || board[row][column] =='B') {
            possibleActions.add("BLOCK");
        }
        return possibleActions;
    }

    public Vector<String> legalMoves() {

        return legalMoves(turn);
    }

    public void execute(String action) {
        System.out.println("The turn is at agent: "+turn);
        System.out.println("Agent chose to do action: " + action);
        switch (action){
            case "LEFT":
                this.agentY[turn] = this.agentY[turn]-1;
                this.moves.add("LEFT");
                break;
            case "RIGHT":
                this.agentY[turn] = this.agentY[turn]+1;
                this.moves.add("RIGHT");
                break;
            case "UP":
                this.agentX[turn] = this.agentX[turn]-1;
                this.moves.add("UP");
                break;
            case "DOWN":
                this.agentX[turn] = this.agentX[turn]+1;
                this.moves.add("DOWN");
                break;
            case "EAT":
                this.board[(char)this.agentX[turn]][(char)this.agentY[turn]] = ' ';
                this.moves.add("EAT");
                this.score[turn] +=1;
                this.food--;
                break;
            case "BLOCK":
                this.board[(char)this.agentX[turn]][(char)this.agentY[turn]] = '#';
                this.moves.add("BLOCK");
                break;
            default:
                break;
        }
//        this.turn = turn==0 ? 1: 0;
    }

    public boolean isLeaf() {
        Vector<String> legalMoves = legalMoves(turn);
        if(legalMoves.isEmpty()){
            return true;
        }else if(food == 0){
            return true;
        }
        return false;
    }
    public double value(int agent){
        //Define int of other agent
        int otherAgent = agent==0 ? 1: 0;
        if(food==0){
            if(score[agent]>score[otherAgent]){
                return 1.0;
            }else if(score[otherAgent]>score[agent]){
                return -1.0;
            } else{
                return 0;
            }
        }
        if(legalMoves(agent).isEmpty()){
            return -1.0;
        } else if(legalMoves(otherAgent).isEmpty()){
            return 1.0;
        } else {
            return 0;
        }
    }
}
