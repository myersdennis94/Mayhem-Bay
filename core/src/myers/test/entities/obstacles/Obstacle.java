package myers.test.entities.obstacles;

import static myers.test.handlers.B2DVars.PPM;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import myers.test.MayhemGame;
import myers.test.handlers.B2DVars;

/**
 *
 */
public abstract class Obstacle {

    World world;

    // spawn location
    protected final float SPAWNPOSX = MayhemGame.random.nextFloat() * MayhemGame.VIRTUAL_WIDTH;
    protected final float SPAWNPOSY = MayhemGame.VIRTUAL_HEIGHT;

    // obstacle attributes
    protected String name;
    protected float spawnFreq;
    protected float spawnPeriod;
    protected float spawnMax;
    protected float velocityX;
    protected float velocityY;
    protected float shapeHX;
    protected float shapeHY;

    // graphics & physics
    protected Sprite sprite;
    protected Body body;

    /**
     *
     * @param world
     */
    public Obstacle(World world){
        this.world = world;
    }

    /**
     *
     */
    public void createSprite() {
        sprite = new Sprite(MayhemGame.textureAtlas.findRegion(name));
    }

    /**
     *
     */
    public void createBody() {
        BodyDef bdef = new BodyDef();
        bdef.position.set((SPAWNPOSX + shapeHX) / PPM, (SPAWNPOSY + shapeHY) / PPM);
        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.linearVelocity.set(velocityX, velocityY);
        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(shapeHX / PPM,shapeHY / PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        body.createFixture(fdef);
        shape.dispose();
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
    public float getShapeHY() {
        return shapeHY;
    }

    /**
     *
     * @return
     */
    public float getSpawnFreq() {
        return spawnFreq;
    }

    /**
     *
     * @return
     */
    public float getSpawnPeriod() {
        return spawnPeriod;
    }

    /**
     *
     * @return
     */
    public float getSpawnMax() {
        return spawnMax;
    }

    /**
     *
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch){
        spriteBatch.begin();

        spriteBatch.draw(sprite,body.getPosition().x * B2DVars.PPM*MayhemGame.SCALE - sprite.getWidth() / 2,
                body.getPosition().y * B2DVars.PPM * MayhemGame.SCALE - sprite.getWidth() / 2);

        spriteBatch.end();
    }
}
