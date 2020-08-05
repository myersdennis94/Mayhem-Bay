package group4.mayhembay.entities.ships;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import group4.mayhembay.MayhemGame;
import group4.mayhembay.handlers.B2DVars;

import static group4.mayhembay.handlers.B2DVars.PPM;

/**
 * This abstract class is a representation of a ship.
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
    protected float scoreModifier;

    // graphics & physics
    protected Sprite sprite;
    protected Body body;

    /**
     * This constructor initializes the Ship object.
     *
     * @param world
     */
    public Ship(World world) {
        this.world = world;
    }

    /**
     * This method creates and sets the ship's <b><CODE>Sprite</CODE></b>.
     */
    protected void createSprite() {
        sprite = new Sprite(MayhemGame.textureAtlas.findRegion(name));
    }

    /**
     * This method creates and sets the ship's <b><CODE>Body</CODE></b>.
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
     * This method returns the ship's <b><CODE>linearVelocity</CODE></b>.
     *
     * @return a <b><CODE>float</CODE></b> that is the magnitude of the ship's linear velocity.
     */
    public float getLinearVelocity() {
        return linearVelocity;
    }

    /**
     * This method returns the ship's <b><CODE>angularVelocity</CODE></b>.
     *
     * @return a <b><CODE>float</CODE></b> that is the magnitude of the ship's angular velocity.
     */
    public float getAngularVelocity() {
        return angularVelocity;
    }

    /**
     * This method returns the ship's <b><CODE>body</CODE></b>.
     *
     * @return a <b><CODE>Body</CODE></b> that is the ship's physical component.
     */
    public Body getBody() {
        return body;
    }

    /**
     * This method return the ship's <b><CODE>scoreModifier</CODE></b>.
     *
     * @return a <b><CODE>float</CODE></b> that is the ship's score multiplier.
     */
    public float getScoreModifier(){ return scoreModifier; }

    /**
     * This method returns the ship's <b><CODE>shapeHY</CODE></b>.
     *
     * @return a <b><CODE>float</CODE></b> that is the ship's height.
     */
    public float getShapeHY(){
        return shapeHY;
    }

    /**
     * This method returns whether the ship's <b><CODE>body</CODE></b> is out of the play area bounds.
     *
     * @return a <b><CODE>boolean</CODE></b> that is true if the ship is out of bounds; false, if the ship is in bounds.
     */
    public boolean isBodyOutOfBounds() {
        return body.getPosition().y + shapeHY / PPM < 0;
    }

    /**
     * This method renders the ship's <b><CODE>sprite</CODE></b>.
     *
     * @param spriteBatch a <b><CODE>SpriteBatch</CODE></b> that will draw the ship's <b><CODE>sprite</CODE></b>.
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
