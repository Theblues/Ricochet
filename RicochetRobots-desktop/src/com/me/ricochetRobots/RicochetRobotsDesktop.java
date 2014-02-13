package com.me.ricochetRobots;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class RicochetRobotsDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Ricochet Robots";
		cfg.useGL20 = true;
		cfg.width = 480;
		cfg.height = 480;
		
		new LwjglApplication(new RicochetRobots(), cfg);
	}
}
