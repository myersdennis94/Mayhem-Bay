package myers.test.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import myers.test.MayhemGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = MayhemGame.TITLE;
		config.height = MayhemGame.VIRTUAL_HEIGHT * MayhemGame.SCALE;
		config.width = MayhemGame.VIRTUAL_WIDTH * MayhemGame.SCALE;
		config.forceExit = false;

		new LwjglApplication(new MayhemGame(), config);
	}
}
