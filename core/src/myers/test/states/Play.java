package myers.test.states;

import static myers.test.handlers.B2DVars.PPM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import myers.test.MayhemGame;
import myers.test.entities.PlayerShip;
import myers.test.handlers.GameStateManager;

public class Play extends GameState{

    // box2d stuff
    private World world;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera b2dCamera;

    // playerShip
    private PlayerShip playerShip;

    // spawn stuff
    private float rockSpawnTimer = 0;
    private float timeBetweenRockSpawns = 1f;

    private final float FRICTION_COEF = 0.2f;

    public Play(GameStateManager gameStateManager) {
        super(gameStateManager);

        // set up box2d stuff
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        // create player
        createPlayer();

        // set up box2d camera
        b2dCamera = new OrthographicCamera();
        b2dCamera.setToOrtho(false, MayhemGame.VIRTUAL_WIDTH / PPM, MayhemGame.VIRTUAL_HEIGHT / PPM);
    }

    @Override
    public void handleInput() {
        Body body = playerShip.getBody();
        float velocity = 0.5f; // move literal to class attribute
        float angle = body.getAngle();
        System.out.println(angle);

        float velocityX;
        float velocityY;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            body.setAngularVelocity(-0.5f); // move literal to class attribute
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            body.setAngularVelocity(0.5f); // move literal to class attribute
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            velocityX = velocity * MathUtils.sin(-angle);
            velocityY = velocity * MathUtils.cos(-angle);

            body.applyForceToCenter(velocityX, velocityY, true);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            velocityX = -velocity * MathUtils.sin(-angle);
            velocityY = -velocity * MathUtils.cos(-angle);;

            body.applyForceToCenter(velocityX, velocityY, true);
        }
    }

    @Override
    public void update(float deltaTime) {
        world.step(deltaTime, 6, 6);

        handleInput();

        applyRotationalFriction(deltaTime);

        applyDirectionalFriction(deltaTime);

        spawnRockObstacles(deltaTime);
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

    private void applyRotationalFriction(float deltaTime){
        Body body = playerShip.getBody();
        if(body.getAngularVelocity() != 0f){
            float change = FRICTION_COEF * deltaTime;
            if(body.getAngularVelocity() > 0f){
                if(body.getAngularVelocity() - change < 0f){
                    body.setAngularVelocity(0f);
                }else{
                    body.setAngularVelocity(body.getAngularVelocity()-change);
                }
            }else{
                if(body.getAngularVelocity() + change > 0f){
                    body.setAngularVelocity(0f);
                }else{
                    body.setAngularVelocity(body.getAngularVelocity()+change);
                }
            }
        }
    }

    private void applyDirectionalFriction(float deltaTime){
        Body body = playerShip.getBody();
        Vector2 linearVelocity = body.getLinearVelocity();
        if(linearVelocity.x != 0f){
            float xChange = FRICTION_COEF * deltaTime;
            if(linearVelocity.x > 0f){
                if(linearVelocity.x - xChange < 0f){
                    body.setLinearVelocity(0f,linearVelocity.y);
                }else{
                    body.setLinearVelocity(linearVelocity.x - xChange,linearVelocity.y);
                }
            }else{
                if(linearVelocity.x + xChange > 0f){
                    body.setLinearVelocity(0f,linearVelocity.y);
                }else{
                    body.setLinearVelocity(linearVelocity.x + xChange,linearVelocity.y);
                }
            }
        }
        linearVelocity = body.getLinearVelocity();
        if(linearVelocity.y != 0f){
            float yChange = FRICTION_COEF * deltaTime;
            if(linearVelocity.y > 0f){
                if(linearVelocity.y - yChange < 0f){
                    body.setLinearVelocity(linearVelocity.x,0f);
                }else{
                    body.setLinearVelocity(linearVelocity.x,linearVelocity.y - yChange);
                }
            }else{
                if(linearVelocity.y + yChange > 0f){
                    body.setLinearVelocity(linearVelocity.x, 0f);
                }else{
                    body.setLinearVelocity(linearVelocity.x, linearVelocity.y + yChange);
                }
            }
        }
    }

    private void createPlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(144 / PPM, 128 / PPM); // move literal to class attribute
        bdef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8 / PPM,16 / PPM); // move literals to class attribute
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.restitution = 0.1f; // move literal to class attribute
        body.createFixture(fdef);
        shape.dispose();

        playerShip = new PlayerShip(body);
    }

    private void spawnRockObstacles(float deltaTime) {
        rockSpawnTimer += deltaTime;
        if (rockSpawnTimer > timeBetweenRockSpawns) {
            BodyDef bdef = new BodyDef();
            bdef.position.set((MayhemGame.random.nextFloat() * MayhemGame.VIRTUAL_WIDTH + 8) / PPM, (MayhemGame.VIRTUAL_HEIGHT + 8) / PPM); // move literals to class attribute
            bdef.type = BodyDef.BodyType.KinematicBody;
            bdef.linearVelocity.set(0, -1.5f); // move literals to class attribute
            Body body = world.createBody(bdef);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(8 / PPM,8 / PPM); // move literals to class attribute
            FixtureDef fdef = new FixtureDef();
            fdef.shape = shape;
            body.createFixture(fdef);
            shape.dispose();

            rockSpawnTimer -= timeBetweenRockSpawns;
        }
    }
}
