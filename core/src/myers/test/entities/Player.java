package myers.test.entities;

import com.badlogic.gdx.physics.box2d.Body;
import myers.test.entities.ships.Ship;

/**
 * This class is a representation of the player.
 */
public class Player {

    protected Ship ship;
    private float score;

    public Player() {
        score = 0;
    }

    public float getScore(){
        return score;
    }

    public void updateScore(float change){
        score += change;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
