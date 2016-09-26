package de.macbury.startup.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.macbury.startup.CoreGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1368;
		config.height = 768;
		config.resizable = true;
		new LwjglApplication(new DesktopGame(), config);
	}
}
