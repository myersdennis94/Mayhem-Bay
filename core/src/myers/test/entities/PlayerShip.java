package myers.test.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public class PlayerShip extends Sprite {

    protected Body body;

    public PlayerShip(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }
}
