package myers.test.entities.obstacles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RockObstacle extends Obstacle{
    public RockObstacle(float movementSpeed, float xCenter, float yCenter, float width, float height, TextureRegion obstacleTextureRegion) {
        super(movementSpeed, xCenter, yCenter, width, height, obstacleTextureRegion);
    }
}
