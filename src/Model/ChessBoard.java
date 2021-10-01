package Model;

import java.util.*;

/*
 *the chessboard class which is the class that holds the slots of the game*/
public class ChessBoard {
    /*
     *a private variable with the type int that holds the width of the board*/
    private int width;
    /*
     *a private variable with the type int that holds the height of the board*/
    private int height;
    /*
     *a private variable which is a Vector with the type ChessSlot that holds the chessSlot int the board */
    private Vector<ChessSlot> chessSlot = new Vector<>();
    /*
     *a private variable which is a vector with the type String that holds the pieces of the game*/
    private Vector<String> piecesHolder = new Vector<String>();


    /*
     *Constructor for the ChessBoard class*/
    ChessBoard(int width, int height) {
        setSize(width, height);
        addChessSlot();
    }

    /*
     *a method to clear a chess slot that then invokes the addChessSlot method to add another ChessSlot */
    public void clear() {
        chessSlot.clear();
        addChessSlot();
    }

    /*
     *a setter method for both the width and height private variables*/
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /*
     *a method to add a chessSlot into the chessboard */
    public void addChessSlot() {
        int i = 0;
        while (i < height) {

            int j = 0;
            while (j < width) {
                ChessSlot chessSlot = new ChessSlot(i, j);
                this.chessSlot.add(chessSlot);
                j++;
            }
            i++;
        }
    }

    /*
     *a method that adds a  chessPiece in the slot*/
    public void addChessPiece(int x, int y, ChessPiece chessPiece) {
        chessSlot.get(width * x + y).setChessPiece(chessPiece);
    }

    /*
     *a method to set the icon of the pieces*/
    public void addIcon(String name) {
        piecesHolder.add(name);
    }

    /*
     *a method the returns the chessSlot at the position i*/
    public ChessSlot getSlotIndex(int index) {
        return chessSlot.get(index);
    }

    /*
     *a method to return the chessSlot at the position of (x*7,y)*/
    public ChessSlot getSlotIndex(int x, int y) {
        return chessSlot.get(x * 7 + y);
    }

    /*
     *a method used to reverse the whole board*/
    public void reverse() {
        Collections.reverse(chessSlot);
        int i = 0;
        while (i < 8) {
            int j = 0;
            while (j < 7) {
                chessSlot.get(i * 7 + j).setX(i);
                chessSlot.get(i * 7 + j).setY(j);
                j++;
            }
            i++;
        }
    }

    /*
     *a method to get the type of the icon that is used*/
    public String getIcon(String icon) {
        icon = "src/Icon/" + icon + ".png";
        return icon;
    }

    /*
     *a getter method that returns the width of the board*/
    public int getWidth() {
        return this.width;
    }

    /*
     *a getter method that returns the height of the board*/
    public int getHeight() {
        return this.height;
    }

    /*
     *a method that gets the total size of the board*/
    public int getBoardSize() {
        return this.height * this.width;
    }
}
