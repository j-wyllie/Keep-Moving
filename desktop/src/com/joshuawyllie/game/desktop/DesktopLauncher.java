package com.joshuawyllie.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.joshuawyllie.game.ZombieTrain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 225;
		config.height = 400;
		new LwjglApplication(new ZombieTrain(), config);
	}
}
