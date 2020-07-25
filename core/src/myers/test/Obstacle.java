package myers.test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Obstacle {

    // obstacle characteristics
    float movementSpeed; // world units per second
    Vector2 directionVector;

    //position & dimension
    Rectangle boundingBox;

    //graphics
    TextureRegion obstacleTextureRegion;

    public Obstacle(float movementSpeed, float xCenter, float yCenter, float width, float height, TextureRegion obstacleTextureRegion) {
        this.movementSpeed = movementSpeed;
        this.directionVector = new Vector2(0, -1);
        this.boundingBox = new Rectangle(xCenter - width/2,yCenter - height/2,width,height);
        this.obstacleTextureRegion = obstacleTextureRegion;
    }

    public boolean intersects(Rectangle otherRectangle){
        return boundingBox.overlaps(otherRectangle);
    }

    public void translate(float xChange, float yChange){
        boundingBox.setPosition(boundingBox.x+xChange,boundingBox.y+yChange);
    }

    public void draw(Batch batch){
        batch.draw(obstacleTextureRegion, boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
    }
}
