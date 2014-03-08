package com.erwan.ricochetRobots.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.erwan.ricochetRobots.controller.InputController;

public class MultiScreen implements Screen {

    @Override
    public void render(float delta) {
	Gdx.gl.glClearColor(0, 0, 0, 0);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

	Gdx.input.setInputProcessor(new InputController() {
	    @Override
	    public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK)
		    ((Game) Gdx.app.getApplicationListener())
			    .setScreen(new MenuScreen());
		return true;
	    }
	});
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
