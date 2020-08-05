package myers.test.entities.ships;

import com.badlogic.gdx.physics.box2d.World;

public class Tube extends Ship {

    /**
     * @param world
     */
    public Tube(World world) {
        super(world);

        name = "tube";
        shapeHX = 2;
        shapeHY = 4;
        linearVelocity = 2.05f;
        angularVelocity = 2f;
        restitution = 0.8f;
        density = 0f;
        scoreModifier = 5f;

        createSprite();
    }
}
