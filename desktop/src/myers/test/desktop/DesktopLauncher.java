package myers.test.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import myers.test.TestGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = TestGame.TITLE;
		config.height = TestGame.VIRTUAL_HEIGHT * TestGame.SCALE;
		config.width = TestGame.VIRTUAL_WIDTH * TestGame.SCALE;
		config.forceExit = false;

		new LwjglApplication(new TestGame(), config);
	}
}
