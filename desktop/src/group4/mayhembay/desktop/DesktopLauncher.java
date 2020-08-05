package group4.mayhembay.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import group4.mayhembay.MayhemGame;

/**
 * This class launches the desktop version of the application.
 */
public class DesktopLauncher {

	/**
	 * This method creates a <b><CODE>LwjglApplication</CODE></b> that contains <b><CODE>MayhemGame</CODE></b>
	 *
	 * @param arg a <b><CODE>String</CODE></b> array of command line arguments.
	 */
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = MayhemGame.TITLE;
		config.height = MayhemGame.VIRTUAL_HEIGHT * MayhemGame.SCALE;
		config.width = MayhemGame.VIRTUAL_WIDTH * MayhemGame.SCALE;
		config.forceExit = false;

		new LwjglApplication(new MayhemGame(), config);
	}
}
