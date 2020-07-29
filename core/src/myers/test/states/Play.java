package myers.test.states;

import static myers.test.handlers.B2DVars.PPM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import myers.test.MayhemGame;
import myers.test.entities.Player;
import myers.test.entities.ships.AlternateShip;
import myers.test.entities.ships.DefaultShip;
import myers.test.entities.ships.Ship;
import myers.test.handlers.GameStateManager;

public class Play extends GameState{

    // box2d stuff
    private World world;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera b2dCamera;

    // player
    private Player player;

    // spawn stuff
    private float rockSpawnTimer = 0;
    private float timeBetweenRockSpawns = 1f;

    // play constants
    private final float FRICTION_COEF = 0.5f;
    private final float WATER_VELOCITY = -1.5f;

    private TextureAtlas textureAtlas;

    private Sprite playerShip;

    private PhysicsShapeCache physicsBodies;

    public Play(GameStateManager gameStateManager) {
        super(gameStateManager);

        // set up box2d stuff
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        physicsBodies = new PhysicsShapeCache("physics.xml");

        // create player
        loadPlayer();

        // set up box2d camera
        b2dCamera = new OrthographicCamera();
        b2dCamera.setToOrtho(false, MayhemGame.VIRTUAL_WIDTH / PPM, MayhemGame.VIRTUAL_HEIGHT / PPM);

        textureAtlas = new TextureAtlas("images.atlas");
        playerShip = new Sprite(textureAtlas.findRegion("tugboat"));
    }

    @Override
    public void handleInput() {
        Body body = player.getBody();
        Ship ship = player.getShip();

        float velocity = ship.getLinearVelocity(); // move literal to class attribute
        float angle = -body.getAngle();

        float velocityX;
        float velocityY;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            body.setAngularVelocity(-ship.getAngularVelocity()); // move literal to class attribute
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            body.setAngularVelocity(ship.getAngularVelocity()); // move literal to class attribute
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            velocityX = velocity * MathUtils.sin(angle);
            velocityY = velocity * MathUtils.cos(angle);

            body.applyForceToCenter(velocityX, velocityY, true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            velocityX = -velocity * MathUtils.sin(angle);
            velocityY = -velocity * MathUtils.cos(angle);;

            body.applyForceToCenter(velocityX, velocityY, true);
        }
    }

    @Override
    public void update(float deltaTime) {
        world.step(deltaTime, 6, 6);

        handleInput();

        applyCurrent();
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

        spriteBatch.begin();

        Vector2 playerShipPosition = player.getBody().getPosition();
        float degrees = (float)Math.toDegrees(player.getBody().getAngle());
        playerShip.setPosition(playerShipPosition.x,playerShipPosition.y);
        playerShip.setRotation(degrees);

        playerShip.draw(spriteBatch);
        
        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }

    private void applyRotationalFriction(float deltaTime){
        Body body = player.getBody();
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
        Body body = player.getBody();
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

    private void applyCurrent() {
        player.getBody().applyForceToCenter(0, WATER_VELOCITY,false);
    }

    private void loadPlayer() {
        player = new Player();

        // ship - this will have logic to read JSON database and load selected ship
        // the logic could also be moved to the player class, idk
        player.setShip(new AlternateShip());
        Ship ship = player.getShip();

//        // body
//        BodyDef bdef = new BodyDef();
//        bdef.position.set(player.getStartPosX() / PPM, player.getStartPosY() / PPM); // move literal to class attribute
//        bdef.type = BodyDef.BodyType.DynamicBody;
//        Body body = world.createBody(bdef);
//
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(ship.getShapeHX() / PPM,ship.getShapeHY() / PPM); // move literals to class attribute
//        FixtureDef fdef = new FixtureDef();
//        fdef.shape = shape;
//        fdef.restitution = ship.getRestitution(); // move literal to class attribute
//        body.createFixture(fdef);
//        shape.dispose();

        Body body = physicsBodies.createBody("tugboat",world,ship.getScaleX(),ship.getScaleY());

        player.setBody(body);
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
