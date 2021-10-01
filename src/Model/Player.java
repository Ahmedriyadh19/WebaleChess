package Model;

/*
 *a class of player which is the user of the program*/
public class Player {

    /*
     *the only variable in the player class which holds the color of the player, its made in an enum to be one of two players*/
    private ColorOfPlayer playerColor;

    /*
     *the player class constructor*/
    Player(ColorOfPlayer playerColor) {
        setPlayerColor(playerColor);
    }

    /*
     *a setter method of the player class which will set the color of the player*/
    public void setPlayerColor(ColorOfPlayer playerColor) {
        this.playerColor = playerColor;
    }

    /*
     *a getter method of the player class which gets the color of the player*/
    public ColorOfPlayer getPlayerColor() {
        return this.playerColor;
    }
}