package Controller;

import Model.ChessBoard;
import Model.GameBuilder;
import Model.SaveHandler;
import View.GUI;

public class Controller {
    /*
    * we call the game and the chessboard of the game from this instance */
    private static GameBuilder game = new GameBuilder();
    private static ChessBoard chessboard = game.chessboard;
    private static SaveHandler saveHandler= new SaveHandler(game);
    /*
    * in the constructor of the Controller class we call get the instance of the GUI class to access it since we made the GUI class a singleton class*/
    public Controller(){
       GUI.getInstance();
    }

    public static void setChessboard(ChessBoard chessboard) {
        Controller.chessboard = chessboard;
    }

    public static SaveHandler getSaveHandler() {
        return saveHandler;
    }

    /*
    * a getter for the Game builder's instance*/
    public static GameBuilder getGame() {
        return game;
    }
    /*
    * a getter for the chessBoard of the game*/
    public static ChessBoard getChessboard() {
        return chessboard;
    }
    /*
    * the main method*/
    public static void main(String[] args){
    new Controller();
    }
}
