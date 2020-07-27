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


    private Texture gameTitle, startButtonActive, startButtonInactive, exitButtonActive, exitButtonInactive;

    private final int SCREEN_HEIGHT = 640;
    private final int SCREEN_WIDTH = 360;
    private  final int START_BUTTON_WIDTH = SCREEN_WIDTH / 2;
    private final int START_BUTTON_HEIGHT = 50;
    private  final int END_BUTTON_WIDTH = SCREEN_WIDTH / 2;
    private final int END_BUTTON_HEIGHT = 50;
    private  final float TITLE_BUTTON_WIDTH = (float) (SCREEN_WIDTH / 1.5);
    private final int TITLE_BUTTON_HEIGHT = 75;


    MainMenuScreen(TestGame game){

        this.game = game;
        startButtonInactive = new Texture("yellow_button00.png");
        startButtonActive = new Texture("yellow_button05.png");
        exitButtonActive = new Texture("exit_yellow_button00.png");
        exitButtonInactive = new Texture("exit_yellow_button05.png");
        gameTitle = new Texture("game_title.png");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255 , 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        int title_x = (SCREEN_WIDTH/2) - (int)(TITLE_BUTTON_WIDTH/2);
        float title_y = (float) (SCREEN_HEIGHT / 1.2);

        game.batch.draw(gameTitle, title_x,  title_y, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);

        int start_x = (SCREEN_WIDTH/2) - (START_BUTTON_WIDTH/2);
        float start_y = (float) (SCREEN_HEIGHT / 1.5);

        if(Gdx.input.getX() < start_x + START_BUTTON_WIDTH && Gdx.input.getX() > start_x && SCREEN_HEIGHT-Gdx.input.getY() < start_y + START_BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > start_y){
            game.batch.draw(startButtonActive, start_x,  start_y, START_BUTTON_WIDTH, START_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new GameScreen());
            }
        }
        else{
            game.batch.draw(startButtonInactive, start_x,  start_y, START_BUTTON_WIDTH, START_BUTTON_HEIGHT);
        }


        int end_x = (SCREEN_WIDTH/2) - (END_BUTTON_WIDTH/2);
        float end_y = (float) (SCREEN_HEIGHT / 2);

        if(Gdx.input.getX() < end_x + END_BUTTON_WIDTH && Gdx.input.getX() > end_x && SCREEN_HEIGHT-Gdx.input.getY() < end_y + END_BUTTON_HEIGHT && SCREEN_HEIGHT - Gdx.input.getY() > end_y){
            game.batch.draw(exitButtonInactive, end_x,  end_y, END_BUTTON_WIDTH, END_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }
        else{
            game.batch.draw(exitButtonActive, end_x,  end_y, END_BUTTON_WIDTH, END_BUTTON_HEIGHT);
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
