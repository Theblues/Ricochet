package com.erwan.ricochetRobots.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.erwan.ricochetRobots.model.Block;
import com.erwan.ricochetRobots.model.Mur;
import com.erwan.ricochetRobots.model.Robot;
import com.erwan.ricochetRobots.model.Solo;

public class SoloRenderer extends Widget {
    private Solo solo;
    private float top;

    private float ppu; // pixels per unit

    public SoloRenderer(Solo solo, float top) {
	this.solo = solo;
	this.top = top;
	ppu = Gdx.graphics.getWidth() / Solo.SIZE_PLATEAU;
    }

    public void draw(Batch batch, float parentAlpha) {
	validate();
	drawBlocks(batch);
	drawRobots(batch);
	drawWall(batch);
    }

    private void drawBlocks(Batch batch) {
	for (Block block : solo.getBlocks()) {
	    float posX = block.getPosition().x * ppu;
	    float posY = top - Gdx.graphics.getWidth() + block.getPosition().y
		    * ppu;
	    batch.draw(block.getTexture(), posX, posY, block.getBounds().width
		    * ppu, block.getBounds().height * ppu);
	}
    }

    private void drawRobots(Batch batch) {
	for (Robot robots : solo.getRobots()) {
	    float posX = (robots.getPosition().x + .1f) * ppu;
	    float posY = top - Gdx.graphics.getWidth()
		    + (robots.getPosition().y + .1f) * ppu;
	    batch.draw(robots.getTexture(), posX, posY,
		    robots.getBounds().width * ppu, robots.getBounds().height
			    * ppu);
	}
    }

    private void drawWall(Batch batch) {

	for (Mur mur : solo.getMurs()) {
	    float posX = mur.getPosition().x * ppu;
	    float posY = top - Gdx.graphics.getWidth() + mur.getPosition().y
		    * ppu;
	    batch.draw(mur.getTexture(), posX, posY,
		    (mur.getBounds().width + .05f) * ppu,
		    (mur.getBounds().height + .05f) * ppu);
	}
    }
}
