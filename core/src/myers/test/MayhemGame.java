package myers.test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import myers.test.handlers.GameStateManager;

import java.util.Random;

public class MayhemGame implements ApplicationListener {

	public static final String TITLE = "Mayhem Bay";
	public static final int VIRTUAL_HEIGHT = 512;
	public static final int VIRTUAL_WIDTH = 288;
	public static final int SCALE = 2;

	public static final float STEP = 1 / 60f;
	private float accum;

	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private OrthographicCamera hudCamera;
	private Viewport viewport;


	private GameStateManager gameStateManager;

	public static Random random = new Random();

	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT,camera);

		hudCamera = new OrthographicCamera();
		hudCamera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

		gameStateManager = new GameStateManager(this);
	}

	@Override
	public void render() {
		accum += Gdx.graphics.getDeltaTime();
		while (accum >= STEP) {
			accum -= STEP;
			gameStateManager.update(STEP);
			gameStateManager.render();
		}
	}

	@Override
	public void dispose() {
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

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public OrthographicCamera getHudCamera() {
		return hudCamera;
	}

	public Viewport getViewport(){return viewport;}
}
