package Model;

/*
 *the ChessSlot class which is the class that holds the the pieces in the board */
public class ChessSlot {
    /*
     *a private variable with the type chessPiece that holds a chessPiece*/
    private ChessPiece chessPiece;
    /*
     *a private variable with the type of int that hold the (x,*) in the location of the chessPiece on the board*/
    private int x;
    /*
     *a private variable with the type of int that hold the (*,y) in the location of the chessPiece on the board*/
    private int y;

    /*
     *the constructor of the ChessSlot class*/
    ChessSlot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ChessSlot(ChessPiece chessPiece, int x, int y) {
        this.chessPiece = chessPiece;
        this.x = x;
        this.y = y;
    }

    /*
     *the setter method for the chessPiece variable*/
    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    /*
     *the setter method for the x variable*/
    public void setX(int x) {
        this.x = x;
    }

    /*
     *the setter method for the y variable*/
    public void setY(int y) {
        this.y = y;
    }

    /*
     *the getter method for the chessPiece variable which returns the chessPiece*/
    public ChessPiece getChessPiece() {
        return this.chessPiece;
    }

    /*
     *the getter method for the x variable which returns the x value*/
    public int getX() {
        return this.x;
    }

    /*
     *the getter method for the y variable which returns the y value*/
    public int getY() {
        return this.y;
    }
}
