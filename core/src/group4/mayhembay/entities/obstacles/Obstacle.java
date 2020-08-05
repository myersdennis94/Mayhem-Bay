package group4.mayhembay.entities.obstacles;

import static group4.mayhembay.handlers.B2DVars.PPM;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import group4.mayhembay.MayhemGame;
import group4.mayhembay.handlers.B2DVars;

/**
 * This abstract class is a representation of an obstacle.
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
     * This constructor initializes the Obstacle object.
     *
     * @param world a <b><CODE>World</CODE></b> that will contain the obstacle.
     */
    public Obstacle(World world){
        this.world = world;
    }

    /**
     * This method creates and sets the obstacle's <b><CODE>Sprite</CODE></b>.
     */
    public void createSprite() {
        sprite = new Sprite(MayhemGame.textureAtlas.findRegion(name));
    }

    /**
     * This method creates and sets the obstacle's <b><CODE>Body</CODE></b>.
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
     * This method returns the obstacle's <b><CODE>Body</CODE></b>.
     *
     * @return a <b><CODE>Body</CODE></b> that is the obstacle's physical/box2d component.
     */
    public Body getBody() {
        return body;
    }

    /**
     * This method return the obstacles <b><CODE>shapeHY</CODE></b>.
     *
     * @return a <b><CODE>float</CODE></b> that is the obstacle's height.
     */
    public float getShapeHY() {
        return shapeHY;
    }

    /**
     * This method returns the obstacle's <b><CODE>spawnFreq</CODE></b>.
     *
     * @return a <b><CODE>float</CODE></b> that is how often the obstacle spawns in seconds.
     */
    public float getSpawnFreq() {
        return spawnFreq;
    }

    /**
     * This method returns the obstacle's <b><CODE>spawnPeriod</CODE></b>
     *
     * @return a <b><CODE>float</CODE></b> that is the period(s) of time the obstacle can spawn.
     */
    public float getSpawnPeriod() {
        return spawnPeriod;
    }

    /**
     * This method returns the obstacle's <b><CODE>spawnMax</CODE></b>
     *
     * @return a <b><CODE>float</CODE></b> that is the maximum amount of obstacle's that can spawn at a time.
     */
    public float getSpawnMax() {
        return spawnMax;
    }

    /**
     * This method renders the obstacle's <b><CODE>sprite</CODE></b>.
     *
     * @param spriteBatch a <b><CODE>SpriteBatch</CODE></b> that will draw the obstacle's <b><CODE>sprite</CODE></b>.
     */
    public void render(SpriteBatch spriteBatch){
        spriteBatch.begin();

        spriteBatch.draw(sprite,body.getPosition().x * B2DVars.PPM*MayhemGame.SCALE - sprite.getWidth() / 2,
                body.getPosition().y * B2DVars.PPM * MayhemGame.SCALE - sprite.getWidth() / 2);

        spriteBatch.end();
    }
}
