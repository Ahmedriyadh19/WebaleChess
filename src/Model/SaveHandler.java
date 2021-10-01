package Model;


import Controller.Controller;

import java.io.*;

/**
 * this class interacts with the file
 */
public final class SaveHandler {

    GameBuilder builder;

    private String[][] data;
    /*
    * savehandler's constructor */
    public SaveHandler(GameBuilder builder) {
        data = new String[8][7];
        this.builder = builder;

    }
    /*
    * */
    private ChessSlot getPiece(int x, int y, String data) {
        ChessPiece piece = null;
        boolean isEnd = false;
        ColorOfPlayer color = ColorOfPlayer.R;
        Player player = new Player(ColorOfPlayer.R);

        if (!data.contains("NULL")) {
            if (data.contains("B")) {
                color = ColorOfPlayer.B;
                player.setPlayerColor(ColorOfPlayer.B);
            }
            if (data.contains("end"))
                isEnd = true;

            if (data.contains("Plus")) {
                piece = new ChessPiece(ChessPieceType.Plus, player, isEnd);
            } else if (data.contains("Sun")) {
                piece = new ChessPiece(ChessPieceType.Sun, player, isEnd);
            } else if (data.contains("Chevron"))
                piece = new ChessPiece(ChessPieceType.Chevron, player, isEnd);
            else if (data.contains("Triangle"))
                piece = new ChessPiece(ChessPieceType.Triangle, player, isEnd);
            else if (data.contains("Arrow"))
                piece = new ChessPiece(ChessPieceType.Arrow, player, isEnd);


        }
        return new ChessSlot(piece,x, y );

    }

    /**
     * This method is still under Development
     *
     */
    public boolean read() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("savegame.txt"));
            String line = reader.readLine();
            line = reader.readLine();
            Player currPlayer;
            if (line.equals("R"))
                currPlayer = Controller.getGame().player1;

            else
                currPlayer = Controller.getGame().player2;

            int role = Integer.parseInt(line);

            line = reader.readLine();
            ChessBoard temp = new ChessBoard(7, 8);
            for (int i = 0; i < 8; i++) {
                String[] types = line.split("/");
                for (int j = 0; j < 7; j++) {
                    ChessSlot PData = getPiece(j, i, types[j]);
                    temp.addChessPiece(PData.getX(), PData.getY(), PData.getChessPiece());
                }
                line = reader.readLine();
            }

            reader.close();

            Controller.setChessboard(temp);


            return true;
        } catch (IOException e) {
            return false;
        }
    }


    /**
     * gets data from board and convert to strings
     */
    private void getDataFromBoard(ChessBoard board) {
        for (int Y = 0; Y < 8; Y++) {
            for (int X = 0; X < 7; X++) {
                ChessSlot piece = board.getSlotIndex(Y, X);
                if (piece.getChessPiece() != null) {
                    data[Y][X] = piece.getChessPiece().toString();
//                    if (piece.getChessPiece().getChessPieceName() == ChessPieceType.Triangle
//                    || piece.getChessPiece().getChessPieceName() == ChessPieceType.Chevron
//                    || piece.getChessPiece().getChessPieceName() == ChessPieceType.Arrow)
//                        data[Y][X] += piece.getChessPiece().;
                } else
                    data[Y][X] = "NULL";
            }

        }
    }


    /**
     * writes all information to the file
     *
     * @throws IOException
     */
    public void write(ChessBoard board) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("savegame.txt"));
            getDataFromBoard(board);

            writer.write(builder.getPlayerTurn().toString() + '\n');
            writer.write(String.valueOf(builder.getPlayerTurnNum()) + '\n');
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 7; j++) {
                    writer.write(data[i][j]);
                    writer.write('/');
                }
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not create file");
        }
    }


}
