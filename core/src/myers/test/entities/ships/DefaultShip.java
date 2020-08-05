package myers.test.entities.ships;

import com.badlogic.gdx.physics.box2d.*;

public class DefaultShip extends Ship{

    /**
     *
     * @param world
     */
    public DefaultShip(World world) {
        super(world);

        name = "tugboat";
        shapeHX = 8;
        shapeHY = 16;
        linearVelocity = 3f;
        angularVelocity = 0.5f;
        restitution = 0.15f;
        density = 0;
        scoreModifier = 1f;

        createSprite();
    }
}
