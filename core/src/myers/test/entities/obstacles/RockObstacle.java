package myers.test.entities.obstacles;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static myers.test.MayhemGame.textureAtlas;

public class RockObstacle extends Obstacle{

    public RockObstacle(Body body, PolygonShape polygonShape) {
        super(body, polygonShape);
        textureRegion = textureAtlas.findRegion("rock");
    }
}
