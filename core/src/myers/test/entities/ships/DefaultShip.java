package myers.test.entities.ships;

import com.badlogic.gdx.physics.box2d.*;

public class DefaultShip extends Ship{

    public DefaultShip(World world) {
        super(world);

        name = "tugboat";
        shapeHX = 8;
        shapeHY = 16;
        linearVelocity = 3;
        angularVelocity = 0.75f;
        restitution = 0.15f;
        density = 0;

        createSprite();
        createBody();
    }
}
