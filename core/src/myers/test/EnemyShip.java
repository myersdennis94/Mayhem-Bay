package myers.test;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyShip extends Ship {
    public EnemyShip(float movementSpeed, int shield, float xCenter, float yCenter, float width, float height, float laserWidth, float laserHeight, float laserMovementSpeed, float timeBetweenShots, TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion, TextureRegion laserTextureRegion) {
        super(movementSpeed, shield, xCenter, yCenter, width, height, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, shipTextureRegion, shieldTextureRegion, laserTextureRegion);
    }

    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];
        laser[0] = new Laser(xPosition + width*0.22f,yPosition + height * 0.23f,laserWidth,laserHeight,laserMovementSpeed,laserTextureRegion);
        laser[1] = new Laser(xPosition + width*0.72f,yPosition+height*0.23f,laserWidth,laserHeight,laserMovementSpeed,laserTextureRegion);

        timeSinceLastShot = 0;

        return laser;
    }
}
