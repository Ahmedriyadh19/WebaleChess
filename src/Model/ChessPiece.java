package Model;

/*
 *the class ChessPiece that is moved within the board*/
public class ChessPiece {
    /*
     *a private variable of an enum type that holds the names of the chess pieces which are plus-chevron-triangle-sun-arrow*/
    private ChessPieceType chessPieceName;
    /*
     *a private variable of the type of player that holds the player, it can be red player or blue player*/
    private Player player;
    /*
     *a private variable of the type boolean that returns true if the piece reached the end of the board*/
    private boolean endOfBoard;

    /*
     *the constructor of the class ChessPiece */
    ChessPiece(ChessPieceType chessPieceName, Player player, boolean endOfBoard) {
        this.chessPieceName = chessPieceName;
        this.player = player;
        this.endOfBoard = endOfBoard;
    }

    /*
     *a setter method that sets the Name of the chess piece variable*/
    public void setChessPieceName(ChessPieceType chessPieceName) {
        this.chessPieceName = chessPieceName;

    }

    /*
     *a getter method that returns the name of the chess piece*/
    public ChessPieceType getChessPieceName() {
        return this.chessPieceName;
    }

    /*
     *a getter method that returns the player*/
    public Player getPlayer() {
        return this.player;
    }

    /*
     *a setter method that sets the end of board variable to either true or false*/
    public void setEndOfBoard(boolean endOfBoard) {
        this.endOfBoard = endOfBoard;
    }

    /*
     *a getter method that returns true or false depending on if the piece has reached the end of the board*/
    public boolean getEndOfBoard() {
        return this.endOfBoard;
    }
}
