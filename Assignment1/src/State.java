import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class State {
    char[][] board;
    int[] agentX, agentY;
    int[] score;
    int turn;
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
        for(int row=0; row<boardHeight; row++) {
            for(int col = 0; col < boardWith; col++){
                System.out.print(this.board[row][col]);
            }
            System.out.println();
        }
        return null;
    }

    public State copy() {
        return null;
    }

    public Vector<String> legalMoves(int agent) {
        if(board[row][column-1]!="#"){

        }else if(board[row][column+1]!="#"){

        } else if(board[row-1][column]!="#"){

        } else if(board[row+1][column] != "#"){

        } else if(board[row][column]=="*"){

        } else if (board[row][column]==" "){
            
        }
        return null;
    }

    public Vector<String> legalMoves() {
        return null;
    }

    public void execute(String action) {

    }

    public boolean isLeaf() {
        return true;
    }

    public double value(int agent) {
        return 34;
    }
}
