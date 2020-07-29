package myers.test.entities.ships;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import myers.test.MayhemGame;
import myers.test.handlers.B2DVars;

public abstract class Ship{

    // spawn location - same for all ships
    protected final static int SPAWNPOSX = 144;
    protected final static int SPAWNPOSY = 128;

    // ship attributes
    protected String name;
    protected float shapeHX;
    protected float shapeHY;
    protected float linearVelocity; // world units per second
    protected float angularVelocity;
    protected float restitution;
    protected float density;

    // graphics & physics
    protected Sprite sprite;
    protected Body body;

    public String getName() {
        return name;
    }

    public float getShapeHX() {
        return shapeHX;
    }

    public float getShapeHY() {
        return shapeHY;
    }

    public float getLinearVelocity() {
        return linearVelocity;
    }

    public float getAngularVelocity() {
        return angularVelocity;
    }

    public float getRestitution() {
        return restitution;
    }

    public float getDensity() {
        return density;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();

        sprite.rotate((float) (body.getAngle() * 180 / Math.PI));
        spriteBatch.draw(sprite, body.getPosition().x * B2DVars.PPM - shapeHX, body.getPosition().y * B2DVars.PPM - shapeHY);

        spriteBatch.end();
    }
}
