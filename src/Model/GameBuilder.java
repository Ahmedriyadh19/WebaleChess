package Model;

import java.util.*;
import java.lang.*;
import java.util.stream.IntStream;

import static java.lang.Math.*;

/*
 *the a GameBuilder class that builds the whole game for us */
public class GameBuilder {
    public ChessBoard chessboard;
    public Player player1;
    public Player player2;
    public ArrayList<Player> playerList = new ArrayList<>();
    /*
     *makes a private ChessPiece variable to check the piece in queue */
    private static ChessPiece tempPiece = null;
    /*
     *makes a private ChessSlot variable to check the temporary slot that will be tested*/
    private static ChessSlot tempSlot = null;
    /*
     *a private variable of the type int that counts Turn number */
    private static int playerTurnNum = 0;
    /*
     *a boolean private variable that check if we have a winner or not */
    private static boolean checkForWinner;
    /*
     *a string private variable that holds the type of the ChessPiece*/
    private static ChessPieceType type;
    /*an int private variables that hold the positions of the piece, the positions that the piece are moving from and the positions that the pieces will move to*/
    private static int priorX;
    private static int priorY;
    private static int futureX;
    private static int futureY;
    private static int x;
    private static int y;

    /*
     *the constructor of the class GameBuilder class*/
    public GameBuilder() {
        chessboard = new ChessBoard(7, 8);
        player1 = new Player(ColorOfPlayer.B);
        player2 = new Player(ColorOfPlayer.R);
        playerList.add(player2);
        playerList.add(player1);
    }

    /*
     *a method that clears the chessboard and resets the ChessPieces and the player turn which results in restarting the game*/
    public void restart() {
        chessboard.clear();
        pieceSetup();
        playerTurnNum = 0;
        checkForWinner = false;
    }

    /*
     *a method to sets the pieces up in the correct order and position*/
    public void pieceSetup() {
        ChessPieceType[] chessPieceTypes = {ChessPieceType.Plus, ChessPieceType.Triangle, ChessPieceType.Chevron, ChessPieceType.Sun, ChessPieceType.Chevron, ChessPieceType.Triangle, ChessPieceType.Plus};
        for (int x = 0; x < chessboard.getHeight(); x++) {
            for (int y = 0; y < chessboard.getWidth(); y++) {
                switch (x) {
                    case 0:
                        addPieceName(x, y, chessPieceTypes[y], player1, true);
                        break;
                    case 1:
                        addPieceName(x, y, ChessPieceType.Arrow, player1, false);
                        y++;
                        break;
                    case 6:
                        addPieceName(x, y, ChessPieceType.Arrow, player2, false);
                        y++;
                        break;
                    case 7:
                        addPieceName(x, y, chessPieceTypes[y], player2, true);
                        break;
                }
            }
        }
    }

    /*
     * a method to add a piece name*/
    public void addPieceName(int x, int y, ChessPieceType name, Player player, boolean atEnd) {
        chessboard.addChessPiece(x, y, new ChessPiece(name, player, atEnd));
    }

    /*This method check where the piece is stepping from to get the piece
     *it also checks the piece on the place that it's stepping to or clicked on, then return if the step  is allowed or not*/
    public boolean makeStep(ChessSlot chessSlot) {
        boolean canMove = false;
        if ((chessSlot.getChessPiece() != null) && canStep(chessSlot)) {
            if (tempPiece == null) {
                setTempVariables(chessSlot);
            } else {
                futureX = chessSlot.getX();
                futureY = chessSlot.getY();
                canMove = validStep(type, priorX, priorY, futureX, futureY);
                if (!Objects.equals(tempPiece.getPlayer(), chessSlot.getChessPiece().getPlayer()))
                    if (canMove) {
                        resetTempVariables(chessSlot);
                        return true;
                    }
                resetTemps();
            }
        } else {
            if (tempSlot != null) {
                futureX = chessSlot.getX();
                futureY = chessSlot.getY();
                canMove = validStep(type, priorX, priorY, futureX, futureY);
                if (canMove) {
                    resetTempVariables(chessSlot);
                    return true;
                }
            }
            resetTemps();
        }
        return false;
    }

    /*
     * resets the temporary*/
    public void resetTemps() {
        tempPiece = null;
        tempSlot = null;
    }

    /*
     * resets the variables and the chessSlot*/
    public void resetTempVariables(ChessSlot chessSlot) {
        tempSlot.setChessPiece(null);
        chessSlot.setChessPiece(tempPiece);
        tempPiece = null;
        tempSlot = null;
        playerTurnNum++;

    }

    /*
     * sets the variables and the chessSlot*/
    public void setTempVariables(ChessSlot chessSlot) {
        type = chessSlot.getChessPiece().getChessPieceName();
        priorX = chessSlot.getX();
        priorY = chessSlot.getY();
        tempPiece = chessSlot.getChessPiece();
        tempSlot = chessSlot;
    }

    /*
     *This method check if the color of the piece and the color of the player are the same*/
    public boolean canStep(ChessSlot chessSlot) {
        return chessSlot.getChessPiece().getPlayer().equals(getPlayerTurn());
    }

    /*
     *This method  checks if the piece can move in the manner that the user chose to move in*/
    public boolean validStep(ChessPieceType type, int fromX, int fromY, int toX, int toY) {
        x = fromX - toX;
        y = fromY - toY;
        switch (type) {
            case Arrow:
                return checkValidStepForArrow();
            case Plus:
                return checkValidStepForPlus();
            case Triangle:
                return checkValidStepForTriangle();
            case Chevron:
                return checkValidStepForChevron();
            case Sun:
                return checkValidStepForSun();
        }
        return false;
    }

    /*
     * checks if the arrow can step is valid or  not*/
    public boolean checkValidStepForArrow() {
        if (y == 0) {
            if (tempPiece.getEndOfBoard()) {
                if (x == -1 || (x == -2 && chessboard.getSlotIndex(priorX + 1, priorY).getChessPiece() == null)) {
                    if (futureX == 7) {
                        tempPiece.setEndOfBoard(false);
                    }
                    return true;
                }
            } else {
                if (x == 1 || (x == 2 && chessboard.getSlotIndex(priorX - 1, priorY).getChessPiece() == null)) {
                    if (futureX == 0) {
                        tempPiece.setEndOfBoard(true);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * checks if the plus can step is valid or not */
    public boolean checkValidStepForPlus() {
        x = abs(priorX - futureX);
        y = abs(priorY - futureY);
        //move left or forward
        if ((priorX - futureX) > 0 || (priorY - futureY) > 0) {
            //move left
            if ((x == 0 && y >= 1)) {

                for (int i = 1; i <= y - 1; i++) {
                    if (chessboard.getSlotIndex(priorX, priorY - i).getChessPiece() != null) {
                        return false;
                    }
                }
                return true;
            }

            //move forward
            else if (x >= 1 && y == 0) {
                for (int i = 1; i <= x - 1; i++) {
                    if (chessboard.getSlotIndex(priorX - i, priorY).getChessPiece() != null) {
                        return false;
                    }
                }
                return true;
            }
        }

        //move right or backward
        else if ((priorX - futureX) < 0 || (priorY - futureY) < 0) {
            //move right
            if (x == 0 && (priorY - futureY) <= -1) {
                for (int i = y - 1; i > 0; i--) {
                    if (chessboard.getSlotIndex(priorX, priorY + i).getChessPiece() != null) {
                        return false;
                    }
                }
                return true;
            }

            //move backward
            else if ((priorX - futureX) <= -1 && y == 0) {
                for (int i = x - 1; i > 0; i--) {
                    if (chessboard.getSlotIndex(futureX - i, futureY).getChessPiece() != null) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /*
     * checks if the triangle can step is valid or not*/
    public boolean checkValidStepForTriangle() {
        if (abs(x) == abs(y)) {
            //obstruction checking
            //upper left
            if (!(futureX >= priorX || futureY <= priorY)) {
                return IntStream.range(1, x).noneMatch(i -> chessboard.getSlotIndex(priorX - i, priorY + i).getChessPiece() != null);
            }
            // upper right
            else if (!(futureX <= priorX || futureY <= priorY)) {
                return IntStream.range(1, x).noneMatch(i -> chessboard.getSlotIndex(priorX + i, priorY + i).getChessPiece() != null);
            }
            //lower left
            else if (!(futureX >= priorX || futureY >= priorY)) {
                return IntStream.range(1, x).noneMatch(i -> chessboard.getSlotIndex(priorX - i, priorY - i).getChessPiece() != null);
            }
            //lower right
            else if (!(futureX <= priorX || futureY >= priorY)) {
                return IntStream.range(1, x).noneMatch(i -> chessboard.getSlotIndex(priorX + i, priorY - i).getChessPiece() != null);
            }
        }
        return false;
    }

    /*
     * checks if the chevron can step is valid or not*/
    public boolean checkValidStepForChevron() {
        return ((x == -2 || x == 2) && (y == 1 || y == -1)) || ((x == -1 || x == 1) && (y == -2 || y == 2));
    }

    /*
     * checks if the sun can step is valid or not*/
    public boolean checkValidStepForSun() {
        return (IntStream.of(-1, 0, 1).anyMatch(i -> x == i)) && ((y == -1) || (y == 0) || (y == 1));
    }

    /*
    *This is the method used to change the state of the triangle and the plus every two turns to exchange places*/
    public void exchangePiece() {
        int index = 0;

        while (index < chessboard.getBoardSize()) {
            if (chessboard.getSlotIndex(index).getChessPiece() != null) {
                switch (chessboard.getSlotIndex(index).getChessPiece().getChessPieceName()) {
                    case Triangle:
                        exchangePieceType(ChessPieceType.Plus, index);
                        break;
                    case Plus:
                        exchangePieceType(ChessPieceType.Triangle, index);
                        break;
                }
            }
            index = incrementExact(index);
        }
    }
    /*
    * exchanges the types of pieces */
    public void exchangePieceType(ChessPieceType chessPieceName, int index) {
        chessboard.getSlotIndex(index).getChessPiece().setChessPieceName(chessPieceName);
    }


    /*
    *This method check if we have a winner by checking if there is a sun missing and then we can say that the opposite color of the missing sun has won*/
    public String getWinner() {
        int numOfSun = 0;
        String winner = null;
        for (int i = 0; i < chessboard.getBoardSize(); i++) {
            ChessSlot chessSlot = chessboard.getSlotIndex(i);
            ChessPiece chessPiece = chessSlot.getChessPiece();
            if (chessPiece != null) {
                if (chessPiece.getChessPieceName() == ChessPieceType.Sun) {
                    winner = chessPiece.getPlayer().getPlayerColor().toString();
                    checkForWinner = true;
                    numOfSun++;
                }
            }
        }
        if (numOfSun == 2) {
            winner = null;
            checkForWinner = false;
        }
        return winner;
    }

    /*This method returns the player that has the turn to play ,
     * it does that after checking if we have a winner or not because there shouldn't be anymore turns if we have a winner*/
    public Player getPlayerTurn() {
        return playerList.get((checkForWinner ? playerTurnNum - 1 : playerTurnNum) % 2);
    }

    /*
    *11a method that returns the number of the player turn*/
    public int getPlayerTurnNum() {
        return playerTurnNum;
    }

}
