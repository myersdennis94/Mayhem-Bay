package myers.test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Ship {

    // ship characteristics
    float movementSpeed; // world units per second
    int shield;

    //position & dimension
    float xPosition, yPosition; //lower-left corner
    float width, height;

    //laser information
    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float timeBetweenShots;
    float timeSinceLastShot = 0;

    //graphics
    TextureRegion shipTextureRegion, shieldTextureRegion, laserTextureRegion;

    public Ship(float movementSpeed, int shield, float xCenter, float yCenter, float width, float height, float laserWidth,float laserHeight, float laserMovementSpeed, float timeBetweenShots, TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion, TextureRegion laserTextureRegion) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.xPosition = xCenter - width/2;
        this.yPosition = yCenter - height/2;
        this.width = width;
        this.height = height;
        this.laserWidth = laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this.timeBetweenShots = timeBetweenShots;
        this.shipTextureRegion = shipTextureRegion;
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

    public void draw(Batch batch){
        batch.draw(shipTextureRegion, xPosition, yPosition, width, height);
        if(shield > 0){
            batch.draw(shieldTextureRegion, xPosition, yPosition, width, height);
        }
    }
}
