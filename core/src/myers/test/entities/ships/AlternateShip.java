package myers.test.entities.ships;

import com.badlogic.gdx.physics.box2d.World;

public class AlternateShip extends Ship {

    /**
     *
     * @param world
     */
    public AlternateShip(World world) {
        super(world);

        name = "speedboat";
        shapeHX = 4;
        shapeHY = 20;
        linearVelocity = 5f;
        angularVelocity = 0.35f;
        restitution = 0.1f;
        density = 0;

        createSprite();
    }
}
