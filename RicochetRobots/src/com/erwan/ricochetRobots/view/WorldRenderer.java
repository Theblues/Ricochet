package com.erwan.ricochetRobots.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.erwan.ricochetRobots.model.Block;
import com.erwan.ricochetRobots.model.Mur;
import com.erwan.ricochetRobots.model.Robot;
import com.erwan.ricochetRobots.model.World;
import com.erwan.ricochetRobots.screen.GameScreen;

public class WorldRenderer {
    private GameScreen gameScreen;
    private World world;
    private OrthographicCamera cam;
    private int width;
    private int height;

    private float ppuX; // pixels per unit on the X axis
    private float ppuY; // pixels per unit on the Y axis

    protected ShapeRenderer debugRenderer;

    public WorldRenderer(GameScreen gameScreen, World world) {
	this.gameScreen = gameScreen;
	this.world = world;
	// emplacement de notre fenetre
	this.cam = new OrthographicCamera(World.SIZE_PLATEAU,
		World.SIZE_PLATEAU);
	this.cam.position.set(World.SIZE_PLATEAU / 2f, World.SIZE_PLATEAU / 2f,
		0);
	this.cam.update();
	debugRenderer = new ShapeRenderer();
    }

    public void setSize(int w, int h) {
	this.width = w;
	this.height = h;
	ppuX = (float) width / World.SIZE_PLATEAU;
	ppuY = (float) height / World.SIZE_PLATEAU;
    }

    public void render() {
	drawBlocks();
	drawRobots();
	drawWall();
    }

    private void drawBlocks() {
	for (Block block : world.getWorld()) {
	    gameScreen.getBatch().draw(block.getTexture(),
		    block.getPosition().x * ppuX, block.getPosition().y * ppuY,
		    block.getBounds().width * ppuX,
		    block.getBounds().height * ppuY);
	}
    }

    private void drawRobots() {
	for (Robot robots : world.getRobots()) {
	    gameScreen.getBatch().draw(robots.getTexture(),
		    robots.getPosition().x * ppuX,
		    robots.getPosition().y * ppuY,
		    robots.getBounds().width * ppuX,
		    robots.getBounds().height * ppuY);
	}
    }

    private void drawWall() {
	for (Mur mur : world.getMurs()) {
	    gameScreen.getBatch()
		    .draw(mur.getTexture(), mur.getPosition().x * ppuX,
			    mur.getPosition().y * ppuY,
			    mur.getBounds().width * ppuX,
			    mur.getBounds().height * ppuY);
	}
    }
}
