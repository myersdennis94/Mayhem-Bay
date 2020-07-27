package myers.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {
    TestGame game;

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;


    private Texture startButtonActive, startButtonInactive;

    private final int SCREEN_HEIGHT = 640;
    private final int SCREEN_WIDTH = 360;
    private  final int START_BUTTON_WIDTH = 100;
    private final int START_BUTTON_HEIGHT = 50;

    MainMenuScreen(TestGame game){

        this.game = game;
        startButtonInactive = new Texture("yellow_button00.png");
        startButtonActive = new Texture("yellow_button05.png");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0 , 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        int x = (SCREEN_WIDTH/2) - (START_BUTTON_WIDTH/2);
        float y = (float) (SCREEN_HEIGHT / 1.5);

        if(Gdx.input.getX() < x + START_BUTTON_WIDTH && Gdx.input.getX() > x && SCREEN_HEIGHT-Gdx.input.getY() < y + START_BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > y){
            game.batch.draw(startButtonActive, x,  y, START_BUTTON_WIDTH, START_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new GameScreen());
            }
        }
        else{
            game.batch.draw(startButtonInactive, x,  y, START_BUTTON_WIDTH, START_BUTTON_HEIGHT);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
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
    public void dispose() {

    }
}
