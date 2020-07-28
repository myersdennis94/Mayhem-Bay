package myers.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class TestGame extends Game{

	GameScreen gameScreen;
	MainMenuScreen menuScreen;
	public SpriteBatch batch;

	public static Random random = new Random();

	@Override
	public void create() {
		batch = new SpriteBatch();
		menuScreen = new MainMenuScreen(this);
		this.setScreen(menuScreen);
		/*gameScreen = new GameScreen();
		setScreen(gameScreen);*/
	}

	@Override
	public void resize(int width, int height) {
		//gameScreen.resize(width,height);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		gameScreen.dispose();
	}
}
