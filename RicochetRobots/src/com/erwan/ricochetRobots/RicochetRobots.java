package com.erwan.ricochetRobots;

import com.badlogic.gdx.Game;
import com.erwan.ricochetRobots.screen.MenuScreen;
import com.erwan.ricochetRobots.screen.SplashScreen;
import com.erwan.ricochetRobots.util.RicochetInterface;

public class RicochetRobots extends Game {

    public static final String LOG = "RicochetRobot";

    private static RicochetInterface ricochetInterface;
    private boolean firstTime;

    public RicochetRobots(RicochetInterface ricochetInterface) {
	RicochetRobots.ricochetInterface = ricochetInterface;
	firstTime = true;
    }

    public static RicochetInterface getRicochetInterface() {
	return RicochetRobots.ricochetInterface;
    }

    @Override
    public void create() {
	setScreen(firstTime ? new SplashScreen() : new MenuScreen());
	firstTime = false;
    }
}