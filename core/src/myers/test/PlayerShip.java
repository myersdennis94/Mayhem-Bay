package myers.test;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerShip extends Ship {


    public PlayerShip(float movementSpeed, int shield, float xCenter, float yCenter, float width, float height, float laserWidth, float laserHeight, float laserMovementSpeed, float timeBetweenShots, TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion, TextureRegion laserTextureRegion) {
        super(movementSpeed, shield, xCenter, yCenter, width, height, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, shipTextureRegion, shieldTextureRegion, laserTextureRegion);
    }

    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];
        laser[0] = new Laser(boundingBox.x + boundingBox.width*0.22f,boundingBox.y + boundingBox.height * 0.77f,laserWidth,laserHeight,laserMovementSpeed,laserTextureRegion);
        laser[1] = new Laser(boundingBox.x + boundingBox.width*0.72f,boundingBox.y + boundingBox.height*0.77f,laserWidth,laserHeight,laserMovementSpeed,laserTextureRegion);

        timeSinceLastShot = 0;

        return laser;
    }
}
