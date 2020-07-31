package myers.test.entities.obstacles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

import static myers.test.handlers.B2DVars.PPM;

public class LandObstacle extends Obstacle {

    private int sideLength;

    public LandObstacle(World world) {
        super(world);
        sideLength = MathUtils.random(2,3);
        spawnFreq = 3;
        spawnPeriod = 0000000000;
        spawnMax = 2;
        velocityX = 0;
        velocityY = -1.5f/sideLength;
        shapeHX = 8*sideLength*1.7f;
        shapeHY = 8*sideLength*1.7f;
        switch (sideLength){
            case 2:
                name = "land22";
                break;
            case 3:
                name = "land33";
                break;
            case 4:
                name = "land44";
                break;
        }
    }

    public float getShapeHY(){
        return shapeHY;
    }
}
