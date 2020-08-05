package group4.mayhembay.entities.obstacles;

import com.badlogic.gdx.physics.box2d.World;

/**
 * This class is a representation of a rock.
 */
public class RockObstacle extends Obstacle{

    /**
     * This constructor initializes the RockObstacle object.
     *
     * @param world a <b><CODE>World</CODE></b> that will contain the obstacle.
     */
    public RockObstacle(World world) {
        super(world);

        name = "rock";
        spawnFreq = 1;
        spawnPeriod = 0000000000;
        spawnMax = 2;
        velocityX = 0;
        velocityY = -1.5f;
        shapeHX = 8;
        shapeHY = 8;
    }
}
