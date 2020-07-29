package myers.test.entities.ships;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class DefaultShip extends Ship{

    public DefaultShip() {

        name = "tugboat";
        shapeHX = 8;
        shapeHY = 16;
        linearVelocity = 3;
        angularVelocity = 1.5f;
        restitution = 0.15f;
        density = 0;
    }
}
