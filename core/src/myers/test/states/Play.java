package myers.test.states;

import static myers.test.handlers.B2DVars.PPM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import myers.test.MayhemGame;
import myers.test.handlers.GameStateManager;

public class Play extends GameState{

    private World world;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera b2dCamera;

    public Play(GameStateManager gameStateManager) {
        super(gameStateManager);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        // player
        BodyDef bdef = new BodyDef();
        bdef.position.set(144 / PPM, 128 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8 / PPM,16 / PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        //fdef.restitution = 0 - 1;
        body.createFixture(fdef);

        // set up box2d camera
        b2dCamera = new OrthographicCamera();
        b2dCamera.setToOrtho(false, MayhemGame.VIRTUAL_WIDTH / PPM, MayhemGame.VIRTUAL_HEIGHT / PPM);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float deltaTime) {
        world.step(deltaTime, 6, 2);
    }

    @Override
    public void render() {
        // clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw box2d world
        b2dr.render(world, b2dCamera.combined);
    }

    @Override
    public void dispose() {

    }
}
