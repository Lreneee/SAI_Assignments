import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class State {
    char[][] board;
    int[] agentX, agentY;
    int[] score;
    int turn = 0;
    int food;

    Vector<String> moves;

    int boardLine = 0;

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
                        this.agentY = new int[2];
                        this.agentX = new int[2];
                        this.agentX[0] = boardLine;
                        this.agentY[0] = col;
                    } else if(charCharacters[col] == 'B'){
                        this.agentX[1] = boardLine;
                        this.agentY[1] = col;
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
        System.out.print(this.agentX[0]);
        System.out.println(this.agentY[0]);
        System.out.print(this.agentX[1]);
        System.out.println(this.agentY[1]);

        return null;
    }

    public State copy() {
        State state = new State();
        state.board = this.board;
        state.agentX = this.agentX;

        return state;
    }

    public Vector<String> legalMoves(int agent) {
        Vector<String> possibleActions = new Vector<String>();
        char row = (char)this.agentX[agent];
        char column = (char)this.agentY[agent];
//        char row = (char)1;
//        char column = (char)3;
        System.out.println(this.agentX[agent]);
        System.out.println(this.agentY[agent]);
        if (board[row][column-1] != '#') {
            possibleActions.add("LEFT");
        }if (board[row][column+1] != '#') {
            possibleActions.add("RIGHT");
        }if (board[row - 1][column] != '#') {
            possibleActions.add("UP");
        }if (board[row + 1][column] != '#') {
            possibleActions.add("DOWN");
        }if (board[row][column] == '*') {
            possibleActions.add("EAT");
        }if (board[row][column] == ' ' ||board[row][column] == 'A' || board[row][column] =='B') {
            possibleActions.add("BLOCK");
        }
        return possibleActions;
    }

    public Vector<String> legalMoves() {
        return legalMoves(turn);
    }

    public void execute(String action) {
        switch (action){
            case "LEFT":
                this.agentY[turn] = this.agentY[turn]-1;
                break;
            case "RIGHT":
                this.agentY[turn] = this.agentY[turn]+1;
                break;
            case "UP":
                this.agentX[turn] = this.agentX[turn]-1;
                break;
            case "DOWN":
                this.agentX[turn] = this.agentX[turn]+1;
                break;
            case "EAT":
                this.board[(char)this.agentX[turn]][(char)this.agentY[turn]] = ' ';
                break;
            case "BLOCK":
                this.board[(char)this.agentX[turn]][(char)this.agentY[turn]] = '#';
                break;
            default:
                break;
        }
    }

    public boolean isLeaf() {
        return true;
    }
}
