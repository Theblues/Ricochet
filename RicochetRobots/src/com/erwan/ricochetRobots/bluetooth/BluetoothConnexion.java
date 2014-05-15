package com.erwan.ricochetRobots.bluetooth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.erwan.ricochetRobots.RicochetRobots;
import com.erwan.ricochetRobots.screen.MultiScreen;

public class BluetoothConnexion {

    public static void initialize() {
	RicochetRobots.getRicochetInterface().possedeBluetooth();
    }

    public static void actif() {
	((Game) Gdx.app.getApplicationListener()).setScreen(new MultiScreen());
    }
}