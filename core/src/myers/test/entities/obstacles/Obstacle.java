package myers.test.entities.obstacles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import myers.test.MayhemGame;
import myers.test.handlers.B2DVars;

public abstract class Obstacle {

//    // obstacle characteristics
//    float movementSpeed; // world units per second
//    Vector2 directionVector;
//
//    //position & dimension
//    Rectangle boundingBox;
//    Rectangle knockbackBox;
//
//    //graphics
//    TextureRegion obstacleTextureRegion;

//    public Obstacle(float movementSpeed, float xCenter, float yCenter, float width, float height, TextureRegion obstacleTextureRegion) {
//        this.movementSpeed = movementSpeed;
//        this.directionVector = new Vector2(0, -1);
//        this.boundingBox = new Rectangle(xCenter - width/2,yCenter - height/2,width,height);
//        // needed to differentiate between bottom and rest of obstacle - unnecessary if player movement redone with velocity
//        this.knockbackBox = new Rectangle(xCenter - width/8, (yCenter - height/2)-0.1f, width/4, 0.1f);
//        this.obstacleTextureRegion = obstacleTextureRegion;
//    }

    private Body body;
    protected TextureRegion textureRegion;
    private PolygonShape polygonShape;

    public Obstacle(Body body, PolygonShape polygonShape){
        this.body = body;
        this.polygonShape = polygonShape;
    }

    public Body getBody(){
        return body;
    }

    public TextureRegion getTextureRegion(){
        return textureRegion;
    }

    public void draw(Batch batch){
        batch.begin();
        batch.draw(textureRegion,body.getPosition().x* B2DVars.PPM*MayhemGame.SCALE-textureRegion.getRegionWidth()/2,
                body.getPosition().y*B2DVars.PPM* MayhemGame.SCALE-textureRegion.getRegionHeight()/2);
        batch.end();
    }
}
