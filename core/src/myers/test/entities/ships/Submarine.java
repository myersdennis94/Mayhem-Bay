package myers.test.entities.ships;

import com.badlogic.gdx.physics.box2d.World;

public class Submarine extends Ship {
    /**
     * @param world
     */
    public Submarine(World world) {
        super(world);

        name = "submarine";
        shapeHX = 8;
        shapeHY = 16;
        linearVelocity = 2.2f;
        angularVelocity = 0.6f;
        restitution = 0.3f;
        density = 0f;
        scoreModifier = 1.5f;

        createSprite();
    }
}
