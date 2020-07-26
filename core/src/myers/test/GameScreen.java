package myers.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.LinkedList;
import java.util.ListIterator;

public class GameScreen implements Screen {

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;

//    private Texture background;
    private TextureRegion[] backgrounds;

    private TextureRegion playerShipTextureRegion, playerShieldTextureRegion,
        enemyShipTextureRegion, enemyShieldTextureRegion, playerLaserTextureRegion,
        enemyLaserTextureRegion, rockObstacleTextureRegion;
    private Sprite playerShipSprite;

    //timing
    private float[] backgroundOffsets = {0,0,0,0};
    private float backgroundMaxScrollingSpeed;
    private float timeBetweenRockSpawns = 1f;
    private float rockSpawnTimer = 0;

    //world parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;
    private final float TOUCH_MOVEMENT_THRESHOLD = 0.5f;

    //game objects
    private PlayerShip playerShip;
    //zprivate EnemyShip enemyShip;
    private LinkedList<RockObstacle> rockObstacleList;
    private LinkedList<Laser> playerLaserList;
    private LinkedList<Laser> enemyLaserList;

    GameScreen(){
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH,WORLD_HEIGHT,camera);

        //set up texture atlas
        textureAtlas = new TextureAtlas("images.atlas");
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("tex_Water");
        backgrounds[1] = textureAtlas.findRegion("water2");
        backgrounds[2] = textureAtlas.findRegion("water3");
        backgrounds[3] = textureAtlas.findRegion("water4");

        backgroundMaxScrollingSpeed = (float)WORLD_HEIGHT / 4;

        //initialize texture regions
        //playerShipTextureRegion = textureAtlas.findRegion("tugboat");
        enemyShipTextureRegion = textureAtlas.findRegion("speedboat");
        //playerShieldTextureRegion = textureAtlas.findRegion("shield1");
        //enemyShieldTextureRegion = textureAtlas.findRegion("shield2");
        //enemyShieldTextureRegion.flip(false,true);
        playerLaserTextureRegion = textureAtlas.findRegion("laserBlue02");
        enemyLaserTextureRegion = textureAtlas.findRegion("laserRed02");
        rockObstacleTextureRegion = textureAtlas.findRegion("rock");
        playerShipSprite = new Sprite(textureAtlas.findRegion("tugboat"));

        //set up game objects
        playerShip = new PlayerShip(36, 5,3, (float)WORLD_WIDTH/2, (float)WORLD_HEIGHT/4,10,10,0.4f,4,45,0.5f,playerShipSprite,playerShieldTextureRegion,playerLaserTextureRegion);
        //zenemyShip = new EnemyShip(2,1,(float)WORLD_WIDTH/2,(float)WORLD_HEIGHT*3/4,10,10,0.3f,5,50,0.8f,enemyShipTextureRegion,enemyShieldTextureRegion,enemyLaserTextureRegion);
        rockObstacleList = new LinkedList<>();
        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();

        batch = new SpriteBatch();
    }

    // might need to switch delta to deltaTime
    @Override
    public void render(float deltaTime) {
        batch.begin();

        shipCurrent(deltaTime);

        detectInput(deltaTime);

        playerShip.update(deltaTime);
        //zenemyShip.update(deltaTime);

        //scrolling background
        renderBackground(deltaTime);

        //enemy ships
        //zenemyShip.draw(batch);

        //player ship
        playerShip.draw(batch);

        //obstacles
        spawnRockObstacles(deltaTime);

        for (RockObstacle rockObstacle : rockObstacleList) {
            moveObstacle(rockObstacle, deltaTime);
            rockObstacle.draw(batch);
        }


        //lasers
        //zrenderLasers(deltaTime);

        //detect collections between lasers and ships
        detectCollisions(deltaTime);

        //explosions

        batch.end();
    }

    private void shipCurrent(float deltaTime){
        playerShip.translate(0,-15f*deltaTime);
    }

    private void spawnRockObstacles(float deltaTime) {
        rockSpawnTimer += deltaTime;
        if (rockSpawnTimer > timeBetweenRockSpawns) {
            rockObstacleList.add(new RockObstacle(backgroundMaxScrollingSpeed / 2, TestGame.random.nextFloat() * (WORLD_WIDTH - 4), WORLD_HEIGHT, 8, 8, rockObstacleTextureRegion));
            rockSpawnTimer -= timeBetweenRockSpawns;
        }
    }

    private void detectInput(float deltaTime){
        //keyboard

        //strategy: determine maximum distance the ship can move in each direction, check each possible keystroke that
        // matters, then move accordingly

        // not locations limits, change in location limits
        float leftLimit,rightLimit,upLimit,downLimit;
        leftLimit = -playerShip.boundingBox.x;
        downLimit = -playerShip.boundingBox.y;
        rightLimit = WORLD_WIDTH - playerShip.boundingBox.x - playerShip.boundingBox.width;
        upLimit = WORLD_HEIGHT - playerShip.boundingBox.y - playerShip.boundingBox.height;

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightLimit > 0){
            playerShip.translate(Math.min(playerShip.movementSpeed*deltaTime,rightLimit),0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftLimit < 0){
            playerShip.translate(Math.max(-playerShip.movementSpeed*deltaTime,leftLimit),0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && upLimit > 0){
            playerShip.translate(0f,Math.min(playerShip.movementSpeed*deltaTime,upLimit));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && downLimit < 0){
            playerShip.translate(0f,Math.max(-playerShip.movementSpeed*deltaTime,downLimit));
        }

        //touch (also mouse)
        if(Gdx.input.isTouched()){
            //get the screen position
            float xTouchPixels = Gdx.input.getX();
            float yTouchPixels = Gdx.input.getY();

            //convert to world position
            Vector2 touchPoint = new Vector2(xTouchPixels,yTouchPixels);
            touchPoint = viewport.unproject(touchPoint);

            //calculate x and y differences
            Vector2 playerShipCentre = new Vector2(playerShip.boundingBox.x + playerShip.boundingBox.width/2, playerShip.boundingBox.y + playerShip.boundingBox.height/2);

            float touchDistance = touchPoint.dst(playerShipCentre);

            if(touchDistance > TOUCH_MOVEMENT_THRESHOLD){
                float xTouchDifference = touchPoint.x - playerShipCentre.x;
                float yTouchDifference = touchPoint.y - playerShipCentre.y;

                //scale to the maximum speed of the ship
                float xMove = xTouchDifference / touchDistance * playerShip.movementSpeed * deltaTime;
                float yMove = yTouchDifference / touchDistance * playerShip.movementSpeed * deltaTime;

                if(xMove > 0){
                    xMove = Math.min(xMove,rightLimit);
                }else{
                    xMove = Math.max(xMove,leftLimit);
                }

                if(yMove > 0){
                    yMove = Math.min(yMove,upLimit);
                }else{
                    yMove = Math.max(yMove,downLimit);
                }

                playerShip.translate(xMove,yMove);
            }
        }
    }

    private void moveObstacle(RockObstacle rockObstacle, float deltaTime) {
        // not locations limits, change in location limits
        float upLimit,downLimit;
        downLimit = WORLD_HEIGHT;
        upLimit = 0;

        float xMove = 0;
        float yMove = rockObstacle.directionVector.y * rockObstacle.movementSpeed * deltaTime;

        rockObstacle.translate(xMove, yMove);
    }

    private void detectCollisions(float deltaTime){
        for (RockObstacle rockObstacle : rockObstacleList) {
            if (playerShip.intersects(rockObstacle.knockbackBox)) {
                playerShip.knockback();
            }
            if (playerShip.intersects(rockObstacle.boundingBox)) {
                // unnecessary if player movement redone with velocity
            }
        }

        /*z//for each player laser, check whether it intersects an enemy ship
        ListIterator<Laser> iterator = playerLaserList.listIterator();
        while(iterator.hasNext()){
            Laser laser = iterator.next();
            if (enemyShip.intersects(laser.boundingBox)){
                //contact with enemy ship
                enemyShip.hit(laser);
                iterator.remove();
            }
        }

        //for each enemy laser, check whether it intersects the player ship
        iterator = enemyLaserList.listIterator();
        while(iterator.hasNext()){
            Laser laser = iterator.next();
            if (playerShip.intersects(laser.boundingBox)){
                //contact with enemy ship
                playerShip.hit(laser);
                iterator.remove();
            }
        }*/
    }

    private void renderExplosions(){

    }

    /*zprivate void renderLasers(float deltaTime){
        //create new lasers
        //player lasers
        if(playerShip.canFireLaser()){
            Laser[] lasers = playerShip.fireLasers();
            for(Laser laser : lasers){
                playerLaserList.add(laser);
            }
        }
        //enemy lasers
        if(enemyShip.canFireLaser()){
            Laser[] lasers = enemyShip.fireLasers();
            for(Laser laser : lasers){
                enemyLaserList.add(laser);
            }
        }
        //draw lasers
        //remove old lasers
        ListIterator<Laser> iterator = playerLaserList.listIterator();
        while(iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y += laser.movementSpeed * deltaTime;
            if(laser.boundingBox.y > WORLD_HEIGHT){
                iterator.remove();
            }
        }
        iterator = enemyLaserList.listIterator();
        while(iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y -= laser.movementSpeed * deltaTime;
            if(laser.boundingBox.y + laser.boundingBox.height < 0){
                iterator.remove();
            }
        }
    }*/

    private void renderBackground(float deltaTime){

        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        for(int layer = 0; layer < backgroundOffsets.length; layer++){
            if(backgroundOffsets[layer] > WORLD_HEIGHT){
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer],0,-backgroundOffsets[layer],
                    WORLD_WIDTH,WORLD_HEIGHT);
            batch.draw(backgrounds[layer],0,-backgroundOffsets[layer]+
                            WORLD_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void dispose() {

    }
}
