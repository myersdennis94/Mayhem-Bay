package myers.test.entities.ships;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Ship extends Sprite{

    // ship characteristics
    protected float shapeHX;
    protected float shapeHY;
    protected float linearVelocity; // world units per second
    protected float angularVelocity;
    protected float restitution;
    protected float density;
    protected Sprite sprite;

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

    public void draw(Batch batch){
        sprite.draw(batch);
    }
}
