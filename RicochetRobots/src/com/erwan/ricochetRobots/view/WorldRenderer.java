package com.erwan.ricochetRobots.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.erwan.ricochetRobots.model.Block;
import com.erwan.ricochetRobots.model.Mur;
import com.erwan.ricochetRobots.model.Robot;
import com.erwan.ricochetRobots.model.Solo;

public class WorldRenderer extends Actor {
    private Solo solo;
    private int width;
    private float tailleTop;

    private float ppu; // pixels per unit

    public WorldRenderer(Solo solo) {
	this.solo = solo;
    }

    public void setWidth(int w, float tailleTop) {
	this.width = w;
	ppu = (float) width / Solo.SIZE_PLATEAU;
	this.tailleTop = tailleTop;
    }

    public void draw(Batch batch, float parentAlpha) {

	drawBlocks(batch);
	drawRobots(batch);
	drawWall(batch);

	super.draw(batch, parentAlpha);
    }

    private void drawBlocks(Batch batch) {
	for (Block block : solo.getWorld()) {
	    batch.draw(block.getTexture(), block.getPosition().x * ppu,
		    block.getPosition().y * ppu + tailleTop*3,
		    block.getBounds().width * ppu, block.getBounds().height
			    * ppu);
	}
    }

    private void drawRobots(Batch batch) {
	for (Robot robots : solo.getRobots()) {
	    batch.draw(robots.getTexture(), (robots.getPosition().x + .1f)
		    * ppu, (robots.getPosition().y + .1f) * ppu + tailleTop*3,
		    robots.getBounds().width * ppu, robots.getBounds().height
			    * ppu);
	}
    }

    private void drawWall(Batch batch) {
	for (Mur mur : solo.getMurs()) {
	    batch.draw(mur.getTexture(), (mur.getPosition().x - .05f) * ppu,
		    (mur.getPosition().y - .05f) * ppu + tailleTop*3,
		    (mur.getBounds().width + .05f) * ppu,
		    (mur.getBounds().height + .05f) * ppu);
	}
    }
}
