package com.erwan.ricochetRobots.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.erwan.ricochetRobots.model.Block;
import com.erwan.ricochetRobots.model.Mur;
import com.erwan.ricochetRobots.model.Robot;
import com.erwan.ricochetRobots.model.World;

public class WorldRenderer extends Actor {
    private World world;
    private int width;
    private float tailleBottom;

    private float ppu; // pixels per unit

    public WorldRenderer(World world) {
	this.world = world;
    }

    public void setWidth(int w, float tailleBottom) {
	this.width = w;
	ppu = (float) width / World.SIZE_PLATEAU;
	this.tailleBottom = tailleBottom;
    }

    public void draw(Batch batch, float parentAlpha) {

	drawBlocks(batch);
	drawRobots(batch);
	drawWall(batch);

	super.draw(batch, parentAlpha);
    }

    private void drawBlocks(Batch batch) {
	for (Block block : world.getWorld()) {
	    batch.draw(block.getTexture(), block.getPosition().x * ppu,
		    block.getPosition().y * ppu + tailleBottom,
		    block.getBounds().width * ppu, block.getBounds().height
			    * ppu);
	}
    }

    private void drawRobots(Batch batch) {
	for (Robot robots : world.getRobots()) {
	    batch.draw(robots.getTexture(), (robots.getPosition().x + .1f)
		    * ppu, (robots.getPosition().y + .1f) * ppu + tailleBottom,
		    robots.getBounds().width * ppu, robots.getBounds().height
			    * ppu);
	}
    }

    private void drawWall(Batch batch) {
	for (Mur mur : world.getMurs()) {
	    batch.draw(mur.getTexture(), (mur.getPosition().x - .05f) * ppu,
		    (mur.getPosition().y - .05f) * ppu + tailleBottom,
		    (mur.getBounds().width + .05f) * ppu,
		    (mur.getBounds().height + .05f) * ppu);
	}
    }
}
