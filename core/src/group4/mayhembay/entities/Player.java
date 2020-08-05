package group4.mayhembay.entities;

import group4.mayhembay.entities.ships.Ship;

/**
 * This class is a representation of the player.
 */
public class Player {

    protected Ship ship;
    private float score;

    /**
     * This constructor creates a Player object.
     */
    public Player() {
        score = 0;
    }

    /**
     * This method returns private member score.
     *
     * @return score field associated with Player object.
     */
    public float getScore(){
        return score;
    }

    /**
     * This method updates player score.
     *
     * @param change a <b><CODE>float</CODE></b> that represents the change in user score.
     */
    public void updateScore(float change){
        score += change;
    }

    /**
     * This method returns a Ship object.
     *
     * @return ship object associated with Player object.
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * This method sets player ship object.
     *
     * @param ship a <b><CODE>Ship</CODE></b> that corresponds to selected ship object.
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
