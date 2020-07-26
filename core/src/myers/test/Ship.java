package myers.test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Ship {

    // ship characteristics
    float movementSpeed; // world units per second
    float knockbackAmount;
    int shield;

    //position & dimension
    Rectangle boundingBox;

    //laser information
    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float timeBetweenShots;
    float timeSinceLastShot = 0;

    //graphics
    TextureRegion shieldTextureRegion, laserTextureRegion;
    Sprite shipTextureSprite;

    public Ship(float movementSpeed, float knockbackAmount, int shield, float xCenter, float yCenter, float width, float height, float laserWidth,float laserHeight, float laserMovementSpeed, float timeBetweenShots, Sprite shipTextureSprite, TextureRegion shieldTextureRegion, TextureRegion laserTextureRegion) {
        this.movementSpeed = movementSpeed;
        this.knockbackAmount = knockbackAmount;
        this.shield = shield;
        this.boundingBox = new Rectangle(xCenter - width/2,yCenter - height/2,width,height);
        this.laserWidth = laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this.timeBetweenShots = timeBetweenShots;
        this.shipTextureSprite = shipTextureSprite;
        this.shieldTextureRegion = shieldTextureRegion;
        this.laserTextureRegion = laserTextureRegion;
    }

    public void update(float deltaTime){
        timeSinceLastShot += deltaTime;
    }

    public boolean canFireLaser(){
        return (timeSinceLastShot - timeBetweenShots >= 0);
    }

    public abstract Laser[] fireLasers();

    public boolean intersects(Rectangle otherRectangle){
        return boundingBox.overlaps(otherRectangle);
    }

    // if player movement is changed to velocity this will reverse it for a 'bouncing' effect
    public void knockback(){
        translate(0, -knockbackAmount);
    }

    public void translate(float xChange, float yChange){
        boundingBox.setPosition(boundingBox.x+xChange,boundingBox.y+yChange);
    }

    public void rotate(float degree){
        shipTextureSprite.rotate(degree);
    }

    public void draw(Batch batch){
        batch.draw(shipTextureSprite, boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        /*if(shield > 0){
            batch.draw(shieldTextureRegion, boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
        }*/
    }
}
