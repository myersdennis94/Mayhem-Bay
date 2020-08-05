package myers.test.entities.ships;

import com.badlogic.gdx.physics.box2d.World;

public class CargoShip extends Ship {

    /**
     * @param world
     */
    public CargoShip(World world) {
        super(world);

        name = "cargoship";
        shapeHX = 32;
        shapeHY = 50;
        linearVelocity = 2.4f;
        angularVelocity = 0.2f;
        restitution = 0.05f;
        density = 0;

        createSprite();
    }
}