package View;

import Controller.Controller;
import Model.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.*;
import java.io.*;

public class GUI implements ActionListener {
    /*
     *these create panels that are added into the Frame, each panel holds important functionality to the program
     *the menuPanel holds the menuBar,gamePanel holds the chess board,actionPanel shows the bar that holds the turns*/
    private static final String version = " v6.4";
    boolean dark = false;
    boolean light = false;
    private static final JFrame frame = new JFrame("Webale Game " + version);
    private final JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final JPanel gamePanel = new JPanel(new GridLayout(8, 7));
    private final JPanel actionPanel = new JPanel(new FlowLayout());
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu file = new JMenu("File");
    private final JMenu optionMenu = new JMenu("Option");
    private final JMenu helpMenu = new JMenu("Help ?!");
    private final JMenuItem menuItemSave = new JMenuItem("Save Game");
    private final JMenuItem menuItemLoad = new JMenuItem("Load Game");
    private final JMenuItem menuItemRestart = new JMenuItem("Restart");
    private final JMenuItem darkMode = new JMenuItem("Dark Mode");
    private final JMenuItem lightMode = new JMenuItem("Light Mode");
    private final JMenuItem help = new JMenuItem("Help");
    private final JMenuItem aboutUs = new JMenuItem("About Us!");
    private static final Vector<JButton> buttons = new Vector<>();
    private static final JLabel action = new JLabel("Game start! Red team first");
    /*
     * the only instance of the class GUI*/
    private static GUI instance = null;

    /*
     * a getter method for the instance of the class GUI*/
    public static GUI getInstance() {
        synchronized (GUI.class) {
            if (instance == null) {
                instance = new GUI();
            }
        }
        return instance;
    }

    /*
     * the constructor of the GUI class*/
    private GUI() {
        setup();
        placeTheButtonsInTheBoard();
        createGui();
    }

    /*
     * a method that holds the creation of the frame and it's action listeners */
    public void createGui() {
        Image icon = Toolkit.getDefaultToolkit().getImage("src/Icon/icon.png");
        frame.setIconImage(icon);
        frame.setSize(550, 700);
        frame.setIconImage(icon);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                String windowName = "Close Game";
                String windowInfo = "Are you sure you want to CLOSE the Game? All the moves will be lost";
                int result = JOptionPane.showConfirmDialog(frame, windowInfo, windowName, 2);
                if (result == JOptionPane.YES_OPTION)
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });

        menuItemSave.addActionListener(e -> {
            Controller.getSaveHandler().write(Controller.getChessboard());
            JOptionPane.showMessageDialog(frame, "Saved Successfully!", "Save", 1);
        });
        menuItemLoad.addActionListener(e -> {
            Controller.getSaveHandler().read();
            JOptionPane.showMessageDialog(frame, "Under Devolpment ", "ERROR -> 404", 1);
        });
        darkMode.addActionListener(e -> {
            buttons.forEach(button -> button.setBackground(Color.black));
            dark = true;
            actionPanel.setBackground(Color.pink);

        });
        lightMode.addActionListener(e -> {
            buttons.forEach(button -> button.setBackground(Color.WHITE));
            light = true;
            dark = false;
            actionPanel.setBackground(Color.orange);

        });
        help.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, " \tThe Sun can only move one step in any direction. The game ends when the Sun is captured by the other side.\n" +
                    " \tThe Chevron moves in an L shape: 2 steps in one direction then 1 step perpendicular to it. (Similar to the Knight in the normal chess.) It is the only piece that can skip over the other pieces.\n" +
                    " \tThe Triangle can move any number of steps diagonally.\n" +
                    " \tThe Plus can move any number of steps up and down, or left and right.\n" +
                    " \tThe Arrow can only move 1 or 2 steps forward each time, but when it reaches the other edge of the board, it turns around and heads back in the opposite direction. (The icon should also turn around when that happens.)\n" +
                    "\n" +
                    "After blue has moved 2 times, all the blue Triangles will turn into Pluses and vice versa. Similarly, after red has moved 2 times, all the red Triangles will turn into Plusses and vice versa." +
                    "\nThen they will change again after the 4th move, 6th move, etc.\n" +
                    "(This makes Webale chess different from normal chess games, because the pieces will transform like that.)\n", "How To Play", 1);
        });
        aboutUs.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "This Project was created by ^^!  \n\n\n" +
                    "Ahmed, Ahmed Riyadh Mohammed\n=>ID: 1181302169 \n\n " +
                    "Yusuf Mahmoud Mohamed\n=>ID: 1181302215\n\n\n\n" +
                    "THANK YOU FOR YOUR SUPPORTING @^! ", "About Us", 1);
        });
        file.add(menuItemRestart);
        optionMenu.add(menuItemSave);
        optionMenu.add(menuItemLoad);
        optionMenu.add(darkMode);
        optionMenu.add(lightMode);
        helpMenu.add(help);
        helpMenu.add(aboutUs);
        menuBar.add(file);
        menuBar.add(optionMenu);
        menuBar.add(helpMenu);
        menuPanel.add(menuBar);
        frame.add(menuPanel);
        frame.setVisible(true);
    }

    /*
     * a method that sets up the whole game*/
    private void gameSetup() {
        theMenuSetup();
        actionPanel.add(action);
        frame.setLayout(new BorderLayout());
        thePanelSetup();
        menuItemRestart.addActionListener(this::restart);
    }

    /*
     * a method to restart the game*/
    public void restart(ActionEvent e) {
        Controller.getGame().restart();
        resetBoard(false);
        dark = false;
        light = true;
        buttons.forEach(button -> button.setBackground(Color.WHITE));
        actionPanel.setBackground(Color.orange);
        action.setText("Game restarted . . . Red team first");
    }

    /*
     * a method to setup the panels of the game*/
    public void thePanelSetup() {
        String[] place = new String[]{BorderLayout.NORTH, BorderLayout.CENTER, BorderLayout.SOUTH};
        JPanel[] panelItems = new JPanel[]{menuPanel, gamePanel, actionPanel};
        for (int i = 0; i < panelItems.length; i++) {
            frame.add(panelItems[i], place[i]);
        }
    }

    /*
     * a method to setup the menu items and add them to the menu bar*/
    public void theMenuSetup() {
        JMenuItem[] menuItems = new JMenuItem[]{menuItemSave, menuItemLoad, menuItemRestart};
        for (JMenuItem menuItem : menuItems) {
            file.add(menuItem);
        }
        menuBar.add(file);
        menuPanel.add(menuBar);
    }

    /*
     * a method to setup the icon's name and the color of the piece*/
    public void pieceIconSetup() {
        String[] chessPieceName = new String[]{"Assets/Plus", "Assets/Triangle", "Assets/Chevron", "Assets/Sun", "Assets/Arrow"};
        for (String s : chessPieceName) {
            Controller.getChessboard().addIcon(s + "B.png");
            Controller.getChessboard().addIcon(s + "R.png");
        }

    }

    /*
     * a method that calls the gameSetup and the iconsetup and the pieces setup*/
    public void setup() {
        gameSetup();
        pieceIconSetup();
        Controller.getGame().pieceSetup();
    }

    /*
     * a method to place the buttons*/
    public void placeButton(int i) {
        ChessPiece chessPiece = Controller.getChessboard().getSlotIndex(i).getChessPiece();
        Image icon = null;
        if (checkPiece(chessPiece)) {
            if (checkPlayer(chessPiece)) {
                icon = setImage(chessPiece);
            } else {
                icon = setReverseImage(chessPiece);
            }
        }
        makeButton(icon);

    }

    /*
     * a method to create the buttons*/
    public void makeButton(Image icon) {
        JButton btn = new JButton();
        if (icon != null) {
            btn.setIcon(new ImageIcon(icon));
        }
        btn.addActionListener(this);
        if (dark == true) {
            btn.setBackground(Color.BLACK);
            actionPanel.setBackground(Color.pink);

        } else {
            btn.setBackground(Color.white);
            actionPanel.setBackground(Color.orange);
        }

        buttons.add(btn);
        gamePanel.add(btn);
    }

    /*
     * a method to return if the current player equals the current player turn*/
    public boolean checkPlayer(ChessPiece chessPiece) {
        Player currentPlayer = chessPiece.getPlayer();
        Player currentPlayerTurn = Controller.getGame().getPlayerTurn();
        return currentPlayer.equals(currentPlayerTurn);
    }

    /*
     * checks if the piece is at the end of the board and returns the result*/
    public boolean checkEndOfBoard(ChessPiece chessPiece) {
        String nameOfPiece = chessPiece.getChessPieceName().toString();
        boolean endOfBoard = chessPiece.getEndOfBoard();
        return nameOfPiece.equals("Arrow") && endOfBoard;
    }

    /*
     * checks if the piece exists and returns the result*/
    public boolean checkPiece(ChessPiece chessPiece) {
        return !(chessPiece == null);
    }

    /*
     * a method to set the images*/
    public Image setImage(ChessPiece chessPiece) {
        Image image;
        String chessPieceName = chessPiece.getChessPieceName().toString();
        String playerColor = chessPiece.getPlayer().getPlayerColor().toString();
        image = loadImage(Controller.getChessboard().getIcon(chessPieceName + playerColor), checkEndOfBoard(chessPiece));
        return image;
    }

    /*
     * a method to set the images in a reversed manner*/
    public Image setReverseImage(ChessPiece chessPiece) {
        Image image;
        String chessPieceName = chessPiece.getChessPieceName().toString();
        String playerColor = chessPiece.getPlayer().getPlayerColor().toString();
        image = loadImage(Controller.getChessboard().getIcon(chessPieceName + playerColor), !checkEndOfBoard(chessPiece));
        return image;
    }

    /*a method to place all of the the buttons */
    public void placeTheButtonsInTheBoard() {
        for (int i = 0; i < Controller.getChessboard().getBoardSize(); i++) {
            placeButton(i);
        }
    }

    /*
     * a method to generally reset the board*/
    public void resetBoard(boolean endgame) {
        gamePanel.removeAll();
        buttons.clear();
        placeTheButtonsInTheBoard();
        frame.revalidate();
        frame.repaint();
        if (endgame) for (JButton button : buttons) {
            button.removeActionListener(this);
        }
    }

    /*
     * the actionPerformed in the buttons*/
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        ChessSlot chessSlot = Controller.getChessboard().getSlotIndex(buttons.indexOf(button));
        if (madeMovement(chessSlot)) {
            String winner = Controller.getGame().getWinner();
            if (!(winner == null)) {
                gotWinner(winner);
            } else {
                correctMove();
            }
        }

    }

    /*
     * checks if there has been a movement in the chess slots*/
    public boolean madeMovement(ChessSlot chessSlot) {
        return Controller.getGame().makeStep(chessSlot);
    }

    /*
     * gets the winner and sets the the winners name resets the board*/
    public void gotWinner(String winner) {
        String teamName;
        if (winner.equals("R")) {
            teamName = "Red";
        } else {
            teamName = "Blue";
        }
        action.setText(teamName + " Team " + "wins in " + Controller.getGame().getPlayerTurnNum() + " turns !");
        resetBoard(true);
    }

    /*
     * gets the player that made the move and exchanges the pieces of plus and triangle every 4 turns */
    public void correctMove() {

        String teamColor = Controller.getGame().getPlayerTurn().getPlayerColor().toString();
        String teamName;
        if (teamColor.equals("R")) {
            teamName = "Red";
        } else {
            teamName = "Blue";
        }
        action.setText("[Turn number: " + Controller.getGame().getPlayerTurnNum() + "]       " + "it's " + teamName + " team's " + "turn" + mode());
        if (Controller.getGame().getPlayerTurnNum() % 4 == 0) {
            Controller.getGame().exchangePiece();
        }
        Controller.getChessboard().reverse();
        resetBoard(false);
    }

    /*
     * a method that returns if the dark mode is on or if the light mode is on */
    public String mode() {
        if (dark) {
            return "       Dark mode : On ";
        } else {
            return "       Light mode: on ";
        }
    }

    /*
     * a method to buffer an image and checks if the it needs to be flipped and then return an image*/
    private Image loadImage(String path, boolean checkFlip) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (checkFlip) {
            image = flip(image);
        }
        image = resize(image, 40, 40);

        return image;
    }

    /*
     * a method that flips a buffered images*/
    public BufferedImage flip(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -image.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = op.filter(image, null);
        return image;
    }

    /*
     * a method to reset the size the image*/
    public static BufferedImage resize(BufferedImage image, int Width, int height) {
        Image tmp;
        tmp = image.getScaledInstance(Width, height, Image.SCALE_SMOOTH);
        BufferedImage dimg;
        dimg = new BufferedImage(Width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d;
        g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }
}

