package group4.mayhembay.entities.ships;

import com.badlogic.gdx.physics.box2d.World;

public class CargoShip extends Ship {

    /**
     * This class is a representation of a cargo ship.
     *
     * @param world a <b><CODE>World</CODE></b> that will contain the ship.
     */
    public CargoShip(World world) {
        super(world);

        name = "cargoship";
        shapeHX = 40;
        shapeHY = 50;
        linearVelocity = 2.4f;
        angularVelocity = 0.2f;
        restitution = 0.05f;
        density = 0;
        scoreModifier = 10f;

        createSprite();
    }
}
