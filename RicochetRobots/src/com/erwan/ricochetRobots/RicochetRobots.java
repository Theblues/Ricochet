package com.erwan.ricochetRobots;

import com.badlogic.gdx.Game;
import com.erwan.ricochetRobots.bluetooth.BluetoothInterface;
import com.erwan.ricochetRobots.screen.MenuScreen;
import com.erwan.ricochetRobots.screen.SplashScreen;

public class RicochetRobots extends Game {

    public static final String LOG = "RicochetRobot";

    private static BluetoothInterface bi;
    private boolean firstTime;

    public RicochetRobots(BluetoothInterface bi) {
	RicochetRobots.bi = bi;
	firstTime = true;
    }

    public static BluetoothInterface getRba() {
	return bi;
    }

    @Override
    public void create() {
	if (firstTime) {
	    firstTime = false;
	    setScreen(new SplashScreen());
	} else
	    setScreen(new MenuScreen());
    }
}
