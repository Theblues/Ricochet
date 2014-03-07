package com.erwan.ricochetRobots;

import com.badlogic.gdx.Game;
import com.erwan.interfaces.BluetoothInterface;
import com.erwan.ricochetRobots.screen.SplashScreen;

public class RicochetRobots extends Game {

    public static final String LOG = "RicochetRobot";
    
    private static BluetoothInterface bi;
    
    public RicochetRobots(BluetoothInterface bi) {
	RicochetRobots.bi = bi;
    }

    public static BluetoothInterface getRba() {
        return bi;
    }

    @Override
    public void create() {
	setScreen(new SplashScreen());
    }
}
