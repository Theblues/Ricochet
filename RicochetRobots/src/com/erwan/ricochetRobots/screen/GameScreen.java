package com.erwan.ricochetRobots.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erwan.ricochetRobots.controller.WorldController;
import com.erwan.ricochetRobots.model.World;
import com.erwan.ricochetRobots.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {
   
    private SpriteBatch batch;
    private World world;
    private WorldRenderer renderer;
    private WorldController controller;

    private int initX;
    private int initY;

    private int width, height;

    @Override
    public void render(float delta) {
	Gdx.gl.glClearColor(0,0,0,0);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
	batch.begin();
	renderer.render();
	batch.end();
    }

    @Override
    public void resize(int width, int height) {
	renderer.setSize(width, height);
	this.width = width;
	this.height = height;
    }

    @Override
    public void show() {
	world = new World();
	batch = new SpriteBatch();
	renderer = new WorldRenderer(this, world);
	controller = new WorldController(world);
	Gdx.input.setInputProcessor(this);
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
	batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
	return true;
    }

    @Override
    public boolean keyUp(int keycode) {
	return true;
    }

    @Override
    public boolean keyTyped(char character) {
	return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	initX = screenX;
	initY = screenY;
	return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
	float ppuX = (float) width / World.SIZE_PLATEAU;
	float ppuY = (float) height / World.SIZE_PLATEAU;
	if (Math.abs(screenX - initX) < ppuX + 15f
		&& Math.abs(screenY - initY) > ppuY + 15f
		&& screenY - initY < 0)
	    controller.topPressed(initX, initY, width, height);
	if (Math.abs(screenX - initX) < ppuX + 15f
		&& Math.abs(screenY - initY) > ppuY + 15f
		&& screenY - initY > 0)
	    controller.bottomPressed(initX, initY, width, height);
	if (Math.abs(screenX - initX) > ppuX + 15f
		&& Math.abs(screenY - initY) < ppuY + 15f
		&& screenX - initX < 0)
	    controller.leftPressed(initX, initY, width, height);
	if (Math.abs(screenX - initX) > ppuX + 15f
		&& Math.abs(screenY - initY) < ppuY + 15f
		&& screenX - initX > 0) {
	    controller.rightPressed(initX, initY, width, height);
	}
	return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
	return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
	return false;
    }

    @Override
    public boolean scrolled(int amount) {
	return false;
    }
    
    public SpriteBatch getBatch()
    {
	return batch;
    }
}