package com.erwan.ricochetRobots;

import com.badlogic.gdx.Game;
import com.erwan.ricochetRobots.screen.SplashScreen;

public class RicochetRobots extends Game {

    public static final String LOG = "RicochetRobot";

    @Override
    public void create() {
	setScreen(new SplashScreen());
    }
}
