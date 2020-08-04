package myers.test.entities.ships;

import com.badlogic.gdx.physics.box2d.World;

public class AlternateShip extends Ship {

    /**
     *
     * @param world
     */
    public AlternateShip(World world) {
        super(world);

        name = "";
        shapeHX = 12;
        shapeHY = 20;
        linearVelocity = 2.5f;
        angularVelocity = 0.55f;
        restitution = 0.1f;
        density = 0;

        createSprite();
        createBody();
    }
}
