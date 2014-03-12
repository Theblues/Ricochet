package com.erwan.ricochetRobots;

import com.badlogic.gdx.Game;
import com.erwan.ricochetRobots.bluetooth.BluetoothInterface;
import com.erwan.ricochetRobots.screen.MenuScreen;
import com.erwan.ricochetRobots.screen.SplashScreen;
import com.erwan.ricochetRobots.util.PopupInterface;

public class RicochetRobots extends Game {

    public static final String LOG = "RicochetRobot";

    private static BluetoothInterface bluetooth;
    private static PopupInterface popup;
    private boolean firstTime;

    public RicochetRobots(BluetoothInterface bluetooth, PopupInterface popup) {
	RicochetRobots.bluetooth = bluetooth;
	RicochetRobots.popup = popup;
	firstTime = true;
    }

    public static BluetoothInterface getBluetooth() {
	return bluetooth;
    }
    
    public static PopupInterface getPopup() {
	return RicochetRobots.popup;
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
