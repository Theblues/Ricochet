package com.me.ricochetRobots;

import com.badlogic.gdx.Game;
import com.me.ricochetRobots.screen.GameScreen;

public class RicochetRobots extends Game {

    @Override
    public void create() {
	setScreen(new GameScreen());
    }

}
