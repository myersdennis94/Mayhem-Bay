package myers.test.entities.ships;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import myers.test.MayhemGame;
import myers.test.handlers.B2DVars;

import static myers.test.handlers.B2DVars.PPM;

/**
 *
 */
public abstract class Ship{

    World world;

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

    /**
     *
     * @param world
     */
    public Ship(World world) {
        this.world = world;
    }

    /**
     *
     */
    protected void createSprite() {
        sprite = new Sprite(MayhemGame.textureAtlas.findRegion(name));
    }

    /**
     *
     */
    public void createBody() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(SPAWNPOSX / PPM, SPAWNPOSY / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(shapeHX / PPM,shapeHY / PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.restitution = restitution;
        body.createFixture(fdef);
        shape.dispose();
    }

    /**
     *
     * @return
     */
    public float getLinearVelocity() {
        return linearVelocity;
    }

    /**
     *
     * @return
     */
    public float getAngularVelocity() {
        return angularVelocity;
    }

    /**
     *
     * @return
     */
    public Body getBody() {
        return body;
    }

    /**
     *
     * @return
     */
    public float getShapeHY(){
        return shapeHY;
    }

    /**
     *
     * @return
     */
    public boolean isBodyOutOfBounds() {
        return body.getPosition().y + shapeHY / PPM < 0;
    }

    /**
     *
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();

        sprite.setPosition(body.getPosition().x * B2DVars.PPM * MayhemGame.SCALE - sprite.getWidth() / 2,
                body.getPosition().y * B2DVars.PPM * MayhemGame.SCALE - sprite.getHeight() / 2);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        sprite.draw(spriteBatch);

        spriteBatch.end();
    }
}
