package myers.test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.GdxNativesLoader;
import myers.test.entities.Background;
import myers.test.handlers.GameDataManager;
import myers.test.handlers.GameStateManager;

import java.util.Random;

/**
 *
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
	 *
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
	 *
	 */
	@Override
	public void render() {
		accum += Gdx.graphics.getDeltaTime();
		while (accum >= STEP) {
			accum -= STEP;
			gameStateManager.update(STEP);
			gameStateManager.render();
		}
	}

	/**
	 *
	 */
	@Override
	public void dispose() {
	}

	/**
	 *
	 * @param width
	 * @param height
	 */
	@Override
	public void resize(int width, int height) {
	}

	/**
	 *
	 */
	@Override
	public void pause() {

	}

	/**
	 *
	 */
	@Override
	public void resume() {

	}

	/**
	 *
	 * @return
	 */
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	/**
	 *
	 * @return
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}

	/**
	 *
	 * @return
	 */
	public OrthographicCamera getHudCamera() {
		return hudCamera;
	}

}
