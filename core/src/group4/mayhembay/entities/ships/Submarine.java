package group4.mayhembay.entities.ships;

import com.badlogic.gdx.physics.box2d.World;

public class Submarine extends Ship {
    /**
     * This class is a representation of a submarine.
     *
     * @param world a <b><CODE>World</CODE></b> that will contain the ship.
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
