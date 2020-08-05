package group4.mayhembay.entities.ships;

import com.badlogic.gdx.physics.box2d.World;

public class AlternateShip extends Ship {

    /**
     * This class is a representation of a alternate ship.
     *
     * @param world a <b><CODE>World</CODE></b> that will contain the ship.
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
        scoreModifier = 0.8f;

        createSprite();
    }
}
