package group4.mayhembay;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.GdxNativesLoader;
import group4.mayhembay.entities.Background;
import group4.mayhembay.handlers.GameDataManager;
import group4.mayhembay.handlers.GameStateManager;

import java.util.Random;

/**
 * This class contains attributes and methods common across the application.
 */
public class MayhemGame implements ApplicationListener {

	public static final String TITLE = "Mayhem Bay";
	public static final int VIRTUAL_HEIGHT = 512;
	public static final int VIRTUAL_WIDTH = 288;
	public static final int SCALE = 2;
	public static final float STEP = 1 / 60f;
	private float accum;

	public static TextureAtlas textureAtlas;
	protected SpriteBatch spriteBatch;
	private OrthographicCamera camera;
	private OrthographicCamera hudCamera;

	private GameStateManager gameStateManager;
	private Preferences preferences;
	public static Random random = new Random();

	public static GameDataManager gameDataManager;
	public static Background background;

	/**
	 * This method creates objects to be used throughout the application.
	 */
	@Override
	public void create() {

		GdxNativesLoader.load();
		textureAtlas= new TextureAtlas("textures.atlas");
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		hudCamera = new OrthographicCamera();
		hudCamera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		gameStateManager = new GameStateManager(this);
		gameDataManager = new GameDataManager();
		background = new Background(spriteBatch);
	}

	/**
	 * This method controls the rendering of game frames.
	 */
	@Override
	public void render() {
		Gdx.graphics.setTitle((TITLE + " -- FPS: " + Gdx.graphics.getFramesPerSecond()));

		accum += Gdx.graphics.getDeltaTime();
		while (accum >= STEP) {
			accum -= STEP;
			gameStateManager.update(STEP);
			gameStateManager.render();
		}
	}

	/**
	 * This method disposes of objects that are no longer needed.
	 */
	@Override
	public void dispose() {
	}

	/**
	 * This method controls how the application window is resized.
	 *
	 * @param width an <b><CODE>int</CODE></b> that is to be the window's new width.
	 * @param height an <b><CODE>int</CODE></b> that is to be the window's new height.
	 */
	@Override
	public void resize(int width, int height) {
	}

	/**
	 * This method controls how the application is paused.
	 *
	 */
	@Override
	public void pause() {

	}

	/**
	 * This method controls how the application is resumed after pause.
	 *
	 */
	@Override
	public void resume() {

	}

	/**
	 * This method returns the application's <b><CODE>spriteBatch</CODE></b>.
	 *
	 * @return a <b><CODE>SpriteBatch</CODE></b> that will draw the application's entities.
	 */
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	/**
	 * This method returns the application' <b><CODE>camera</CODE></b>.
	 *
	 * @return n <b><CODE>OrthographicCamera</CODE></b> that is the viewport of the application.a
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}

	/**
	 * This method returns the application' <b><CODE>hudCamera</CODE></b>.
	 *
	 * @return n <b><CODE>OrthographicCamera</CODE></b> that is the viewport of the application's HUD.
	 */
	public OrthographicCamera getHudCamera() {
		return hudCamera;
	}

}
