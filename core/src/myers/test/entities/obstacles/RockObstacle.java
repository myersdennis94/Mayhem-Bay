package myers.test.entities.obstacles;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static myers.test.MayhemGame.textureAtlas;

/**
 *
 */
public class RockObstacle extends Obstacle{

    /**
     *
     * @param world
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
