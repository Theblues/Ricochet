package com.me.ricochetRobots.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.ricochetRobots.model.Block;
import com.me.ricochetRobots.model.Robot;
import com.me.ricochetRobots.model.World;

public class WorldRenderer {
    private World world;
    private OrthographicCamera cam;
    private SpriteBatch spriteBatch;
    private int width;
    private int height;

    private float ppuX; // pixels per unit on the X axis
    private float ppuY; // pixels per unit on the Y axis

    /** for debug rendering **/
    protected boolean debug;
    protected ShapeRenderer debugRenderer;

    public WorldRenderer(World world, boolean debug) {
	this.world = world;
	// emplacement de notre fenetre
	this.cam = new OrthographicCamera(World.SIZE_PLATEAU,World.SIZE_PLATEAU);
	this.cam.position.set(World.SIZE_PLATEAU / 2f, World.SIZE_PLATEAU / 2f,
		0);
	this.cam.update();
	spriteBatch = new SpriteBatch();
	this.debug = debug;
	debugRenderer = new ShapeRenderer();
    }

    public void setSize(int w, int h) {
	this.width = w;
	this.height = h;
	ppuX = (float) width / World.SIZE_PLATEAU;
	ppuY = (float) height / World.SIZE_PLATEAU;
    }

    public void render() {
	spriteBatch.begin();
	drawBlocks();
	drawRobots();
	spriteBatch.end();
	if (debug)
	    drawDebug();
    }

    private void drawBlocks() {
	for (Block block : world.getWorld()) {
		spriteBatch.draw(block.getTexture(), block.getPosition().x
			* ppuX, block.getPosition().y * ppuY,
			block.getBounds().width * ppuX,
			block.getBounds().height * ppuY);
	}
    }
    
    private void drawRobots() {
	for (Robot robots : world.getRobots()) {
		spriteBatch.draw(robots.getTexture(), robots.getPosition().x
			* ppuX, robots.getPosition().y * ppuY,
			robots.getBounds().width * ppuX,
			robots.getBounds().height * ppuY);
	}
    }

    public void drawDebug() {
	// render blocks
	debugRenderer.setProjectionMatrix(cam.combined);
	debugRenderer.end();
	debugRenderer.begin(ShapeType.Line);
	for (Block block : world.getWorld()) {
	    Rectangle rect = block.getBounds();
	    float x1 = block.getPosition().x + rect.x;
	    float y1 = block.getPosition().y + rect.y;
	    debugRenderer.setColor(Color.RED);
	    debugRenderer.rect(x1, y1, rect.width, rect.height);
	}
	debugRenderer.end();
    }
}
