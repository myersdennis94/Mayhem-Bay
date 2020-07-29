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

    public Ship(World world) {
        this.world = world;
    }

    public String getName() {
        return name;
    }

    protected void createSprite() {
        sprite = new Sprite(MayhemGame.textureAtlas.findRegion(name));
    }

    protected void createBody() {
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

        sprite.setPosition(body.getPosition().x * B2DVars.PPM * MayhemGame.SCALE - sprite.getWidth() / 2,
                body.getPosition().y * B2DVars.PPM * MayhemGame.SCALE - sprite.getHeight() / 2);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        sprite.draw(spriteBatch);

        spriteBatch.end();
    }
}
