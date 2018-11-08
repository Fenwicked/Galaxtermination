package com.gtngame.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gtngame.game.gtnGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Galaxtermination";
		config.width = 1260;
		config.height = 668;
		//config.fullscreen = true;
		//System.out.println("window width: " + config.width);
		//System.out.println("window height: " + config.height);
		new LwjglApplication(new gtnGame(), config);
	}
}
