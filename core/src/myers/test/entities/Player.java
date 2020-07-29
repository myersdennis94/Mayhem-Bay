package myers.test.entities;

import com.badlogic.gdx.physics.box2d.Body;
import myers.test.entities.ships.Ship;

public class Player {

    protected Ship ship;

    public Player() {
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
