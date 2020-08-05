package group4.mayhembay.states;

import static group4.mayhembay.handlers.B2DVars.PPM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import group4.mayhembay.MayhemGame;
import group4.mayhembay.entities.Player;
import group4.mayhembay.entities.obstacles.LandObstacle;
import group4.mayhembay.entities.obstacles.RockObstacle;
import group4.mayhembay.entities.ships.*;
import group4.mayhembay.handlers.GameStateManager;

import java.util.LinkedList;
import java.util.Locale;

/**
 * This class is responsible for displaying and managing the Mayhem Bay gameplay environment.
 */
public class Play extends GameState{

    // box2d stuff
    private World world;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera b2dCamera;

    // player
    private Player player;

    // spawn stuff
    private float rockSpawnTimer = 0;
    private float landSpawnTimer = 0;
    RockObstacle rockAttributes;
    LandObstacle landAttributes;

    private LinkedList<RockObstacle> rockManager;
    private LinkedList<LandObstacle> landManager;

    // play constants
    private final float FRICTION_COEF = 0.5f;
    private final float WATER_VELOCITY = -1.5f;

    // HUD
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCenterX, hudRow1Y, hudRow2Y, hudSectionWidth;

    // boundary settings
    private boolean lockTop = true;
    private boolean lockSides = true;

    // timing
    private long startTime;


    /**
     * This constructor creates a Play object and initializes its fields.
     *
     * @param gameStateManager a <b><CODE>GameStateManager</CODE></b> that holds crucial gameplay information.
     */
    public Play(GameStateManager gameStateManager) {
        super(gameStateManager);

        // set up box2d stuff
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        // load player
        loadPlayer();

        // spawn stuff
        rockAttributes = new RockObstacle(null);
        rockManager = new LinkedList<>();

        landAttributes = new LandObstacle(null);
        landManager = new LinkedList<>();

        prepareHUD();
        // set up box2d camera
        b2dCamera = new OrthographicCamera();
        b2dCamera.setToOrtho(false, MayhemGame.VIRTUAL_WIDTH / PPM, MayhemGame.VIRTUAL_HEIGHT / PPM);

        setUpBorders();

        startTime = TimeUtils.millis();
    }

    /**
     * This method is responsible for handling all user input during gameplay.
     */
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

    /**
     * This method is responsible for updating the gameplay environment.
     *
     * @param deltaTime a <b><CODE>float</CODE></b> that corresponds to the time passed since last update.
     */
    @Override
    public void update(float deltaTime) {
        world.step(deltaTime, 6, 6);

        handleInput();

        applyCurrent();
        applyRotationalFriction(deltaTime);
        applyDirectionalFriction(deltaTime);

        spawnRockObstacles(deltaTime);
        spawnLandObstacles(deltaTime);

        updateScore(deltaTime);

        checkLoss();
    }

    /**
     * This method is responsible for rendering the gameplay environment.
     *
     * @param deltaTime a <b><CODE>float</CODE></b> that corresponds to the amount of time passed since last update.
     */
    @Override
    public void render(float deltaTime) {
        // clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        MayhemGame.background.render(deltaTime);

        // draw box2d world
        //b2dr.render(world, b2dCamera.combined);

        renderRocks();
        renderLand();

        player.getShip().render(spriteBatch);

        updateAndRenderHUD(deltaTime);
    }

    /**
     * This method is responsible for disposal.
     */
    @Override
    public void dispose() {

    }

    /**
     * This method is responsible for checking the loss conditions and distributing final stats to a GameDataManager object.
     */
    private void checkLoss(){
        if(player.getShip().isBodyOutOfBounds()){
            MayhemGame.gameDataManager.gameData.setLastTime(TimeUtils.timeSinceMillis(startTime)/1000f);
            MayhemGame.gameDataManager.gameData.setLastScore((int)player.getScore());
            gameStateManager.setState(GameStateManager.SCORE);
        }
    }

    /**
     * This method is responsible for setting up physical boundaries to contain players ship within rendered area.
     */
    private void setUpBorders(){
        Body body;
        BodyDef bodyDef;
        PolygonShape polygonShape;
        if(lockTop){
            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(0,MayhemGame.VIRTUAL_HEIGHT/PPM);
            body = world.createBody(bodyDef);

            polygonShape = new PolygonShape();
            polygonShape.set(new float[]{0f,0f,MayhemGame.VIRTUAL_WIDTH/PPM,0f,MayhemGame.VIRTUAL_WIDTH/PPM,1f,0f,1f});

            body.createFixture(polygonShape,0f);
            polygonShape.dispose();
        }
        if(lockSides){
            // left invisible wall
            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(0f,0f);
            body = world.createBody(bodyDef);

            polygonShape = new PolygonShape();
            polygonShape.set(new float[]{-1f,0f,-1f,MayhemGame.VIRTUAL_HEIGHT/PPM,0f,MayhemGame.VIRTUAL_HEIGHT/PPM,0f,0f});

            body.createFixture(polygonShape,0f);
            polygonShape.dispose();

            // right invisible wall
            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(MayhemGame.VIRTUAL_WIDTH/PPM,0f);
            body = world.createBody(bodyDef);

            polygonShape = new PolygonShape();
            polygonShape.set(new float[]{0f,0f,0f,MayhemGame.VIRTUAL_HEIGHT/PPM,1f,MayhemGame.VIRTUAL_HEIGHT/PPM,1f,0f});

            body.createFixture(polygonShape,0f);
            polygonShape.dispose();
        }
    }

    /**
     * This method is responsible for updating the user score.
     *
     * @param deltaTime a <b><CODE>float</CODE></b> corresponding to the amount of time since last update.
     */
    private void updateScore(float deltaTime){
        if(player.getShip().getBody().getLinearVelocity().y > 0){
            player.updateScore(deltaTime*20*player.getShip().getBody().getLinearVelocity().len());
        }
        player.updateScore(deltaTime*2*player.getShip().getScoreModifier());
    }

    /**
     * This method is responsible for updating the heads up display with current information.
     *
     * @param deltaTime a <b><CODE>float</CODE></b> corresponding to the amount of time since last update.
     */
    private void updateAndRenderHUD(float deltaTime){
        spriteBatch.begin();
        //first row
        font.draw(spriteBatch, "Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(spriteBatch,"Time",hudCenterX,hudRow1Y,hudSectionWidth,Align.center,false);

        //second row
        font.draw(spriteBatch,String.format(Locale.getDefault(),"%06.0f",player.getScore()),hudLeftX,hudRow2Y,hudSectionWidth,Align.left,false);
        font.draw(spriteBatch,String.format(Locale.getDefault(),"%5.1f",TimeUtils.timeSinceMillis(startTime)/1000f)+" s",hudCenterX,hudRow2Y,hudSectionWidth,Align.left,false);

        spriteBatch.end();
    }

    /**
     * This method is responsible for preparing the heads up display and correlated fields.
     */
    private void prepareHUD(){
        // create a BitmapFont from font file
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Kalmansk-51WVB.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 48;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1,1,1,0.3f);
        fontParameter.borderColor = new Color(0,0,0,0.3f);

        font = fontGenerator.generateFont(fontParameter);

        // scale font to fit world
        font.getData().setScale(MayhemGame.SCALE);

        // calculate hud margins
        hudVerticalMargin = font.getCapHeight()/2;
        hudLeftX = hudVerticalMargin;
        hudRightX = MayhemGame.VIRTUAL_WIDTH/3 - hudLeftX;
        hudCenterX = MayhemGame.VIRTUAL_WIDTH*MayhemGame.SCALE/2+hudVerticalMargin;
        hudRow1Y = MayhemGame.VIRTUAL_HEIGHT*MayhemGame.SCALE - hudVerticalMargin;
        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = MayhemGame.VIRTUAL_WIDTH/3;
    }

    /**
     * This method is responsible for rendering and removing RockObstacle objects.
     */
    private void renderRocks(){
        for (RockObstacle rock : rockManager) {
            if (rock.getBody().getPosition().y < 0) {
                rockManager.remove(rock);
            } else {
                rock.render(spriteBatch);
            }
        }
    }

    /**
     * This method is responsible for rendering and removing LandObstacle objects.
     */
    private void renderLand(){
        for (LandObstacle land : landManager){
            if (land.getBody().getPosition().y + land.getShapeHY()/PPM < 0){
                landManager.remove(land);
            }else{
                land.render(spriteBatch);
            }
        }
    }

    /**
     * This method is responsible for applying a rotational friction force to prevent user ship from infinitely spinning.
     *
     * @param deltaTime a <b><CODE>float</CODE></b> that corresponds to the time since last update.
     */
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

    /**
     *
     * @param deltaTime a <b><CODE>float</CODE></b> that corresponds to the time since last update.
     */
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

    /**
     * This method is responsible for applying a "current" moving the user ship in the negative y direction.
     */
    private void applyCurrent() {
        player.getShip().getBody().applyForceToCenter(0, WATER_VELOCITY,false);
    }

    /**
     * This method is responsible for creating a new Player object with the correct ship choice dependent on choices made elsewhere.
     */
    private void loadPlayer() {
        player = new Player();

        // ship - this will have logic to read the JSON database and load selected ship (Default/Alternate/etc.)
        // or the logic could also be moved to the player class, idk
        switch(MayhemGame.gameDataManager.gameData.getShip()){
            case "tugboat":
                player.setShip(new DefaultShip(world));
                break;
            case "speedboat":
                player.setShip(new AlternateShip(world));
                break;
            case "submarine":
                player.setShip(new Submarine(world));
                break;
            case "tube":
                player.setShip(new Tube(world));
                break;
            case "cargoship":
                player.setShip(new CargoShip(world));
                break;
            default:
                player.setShip(new DefaultShip(world));
                break;
        }
        player.getShip().createBody();
    }

    /**
     * This method is responsible for spawning LandObstacle objects.
     *
     * @param deltaTime a <b><CODE>float</CODE></b> that corresponds to the time since last update.
     */
    private void spawnRockObstacles(float deltaTime) {
        rockSpawnTimer += deltaTime;

        if (rockSpawnTimer > rockAttributes.getSpawnFreq()) {

            RockObstacle newRock = new RockObstacle(world);
            newRock.createSprite();
            newRock.createBody();
            rockManager.push(newRock);

            rockSpawnTimer -= rockAttributes.getSpawnFreq();
        }
    }

    /**
     * This method is responsible for spawning RockObstacle objects.
     *
     * @param deltaTime a <b><CODE>float</CODE></b> that corresponds to the time since last update.
     */
    private void spawnLandObstacles(float deltaTime){
        landSpawnTimer += deltaTime;

        if(landSpawnTimer > landAttributes.getSpawnFreq()) {

            LandObstacle newLand = new LandObstacle(world);
            newLand.createSprite();
            newLand.createBody();
            landManager.push(newLand);

            landSpawnTimer -= landAttributes.getSpawnFreq();
        }
    }
}
