package myers.test.states;

import static myers.test.MayhemGame.textureAtlas;
import static myers.test.handlers.B2DVars.PPM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import myers.test.MayhemGame;
import myers.test.entities.Player;
import myers.test.entities.obstacles.RockObstacle;
import myers.test.entities.ships.AlternateShip;
import myers.test.entities.ships.DefaultShip;
import myers.test.entities.ships.Ship;
import myers.test.handlers.GameStateManager;

import java.util.LinkedList;
import java.util.ListIterator;

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
    private LinkedList<RockObstacle> rockManager;

    // play constants
    private final float FRICTION_COEF = 0.5f;
    private final float WATER_VELOCITY = -1.5f;

    // background
    private TextureRegion[] backgrounds;
    private float[] backgroundOffsets = {0,0,0,0};
    private float backgroundMaxScrollingSpeed;

    public Play(GameStateManager gameStateManager) {
        super(gameStateManager);

        // set up box2d stuff
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        // create player
        loadPlayer();

        // set up box2d camera
        b2dCamera = new OrthographicCamera();
        b2dCamera.setToOrtho(false, MayhemGame.VIRTUAL_WIDTH / PPM, MayhemGame.VIRTUAL_HEIGHT / PPM);

        //playerShip = new Sprite(textureAtlas.findRegion("tugboat"));
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("tex_Water");
        backgrounds[1] = textureAtlas.findRegion("water2");
        backgrounds[2] = textureAtlas.findRegion("water3");
        backgrounds[3] = textureAtlas.findRegion("water4");
        backgroundMaxScrollingSpeed = (float)MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE/4;

        rockManager = new LinkedList<>();
    }

    @Override
    public void handleInput() {
        Ship ship = player.getShip();
        Body body = ship.getBody();


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
    public void render(float deltaTime) {
        // clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderBackground(deltaTime);

        // draw box2d world
        //b2dr.render(world, b2dCamera.combined);

        renderRocks();

        player.getShip().render(spriteBatch);
    }

    @Override
    public void dispose() {

    }

    private void renderRocks(){
        if(rockManager.size() > 0) {
            ListIterator<RockObstacle> iterator = rockManager.listIterator();
            while (iterator.hasNext()) {
                RockObstacle rock = iterator.next();
                if (rock.getBody().getPosition().y < 0) {
                    iterator.remove();
                } else {
                    rock.draw(spriteBatch);
                }
            }
        }
    }

    private void renderBackground(float deltaTime){
        spriteBatch.begin();
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;
        for(int layer = 0; layer < backgroundOffsets.length; layer++){
            if(backgroundOffsets[layer] > MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE){
                backgroundOffsets[layer] = 0;
            }
            spriteBatch.draw(backgrounds[layer],0,-backgroundOffsets[layer],
                    MayhemGame.VIRTUAL_WIDTH*MayhemGame.SCALE,MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE);
            spriteBatch.draw(backgrounds[layer],0,-backgroundOffsets[layer]+
                    MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE,MayhemGame.VIRTUAL_WIDTH*MayhemGame.SCALE,MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE);
        }
        spriteBatch.end();
    }

    private void applyRotationalFriction(float deltaTime){
        Body body = player.getShip().getBody();
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
        Body body = player.getShip().getBody();
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
        player.getShip().getBody().applyForceToCenter(0, WATER_VELOCITY,false);
    }

    private void loadPlayer() {
        player = new Player();

        // ship - this will have logic to read the JSON database and load selected ship (Default/Alternate/etc.)
        // or the logic could also be moved to the player class, idk
        player.setShip(new DefaultShip(world));
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

            rockManager.push(new RockObstacle(body,shape));

            rockSpawnTimer -= timeBetweenRockSpawns;
        }
    }
}
