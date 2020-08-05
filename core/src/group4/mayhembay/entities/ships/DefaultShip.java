package group4.mayhembay.entities.ships;

import com.badlogic.gdx.physics.box2d.*;

public class DefaultShip extends Ship{

    /**
     * This class is a representation of a standard ship.
     *
     * @param world a <b><CODE>World</CODE></b> that will contain the ship.
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
