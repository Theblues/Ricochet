package com.erwan.ricochetRobots;

import com.badlogic.gdx.Game;
import com.erwan.ricochetRobots.screen.GameScreen;

public class RicochetRobots extends Game {

    @Override
    public void create() {
	setScreen(new GameScreen());
    }

}
