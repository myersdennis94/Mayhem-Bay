package myers.test.entities;

import com.badlogic.gdx.physics.box2d.Body;
import myers.test.entities.ships.Ship;

public class Player {

    private final int STARTPOSX = 144;
    private final int STARTPOSY = 128;
    protected Body body;
    protected Ship ship;

    public Player() {
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public int getStartPosX() {
        return STARTPOSX;
    }

    public int getStartPosY() {
        return STARTPOSY;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
