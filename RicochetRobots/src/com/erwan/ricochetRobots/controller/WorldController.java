package com.erwan.ricochetRobots.controller;

import com.badlogic.gdx.math.Vector2;
import com.erwan.ricochetRobots.model.Robot;
import com.erwan.ricochetRobots.model.World;

public class WorldController {

    private World world;

    public WorldController(World world) {
	this.world = world;
    }

    public void update(float delta) {
    }

    public void rightPressed(int initX, int initY, int width, int height) {
	float ppuX = (float) width / World.SIZE_PLATEAU;
	float ppuY = (float) height / World.SIZE_PLATEAU;
	for (Robot robot : world.getRobots()) {

	    double coordX = robot.getPosition().x * ppuX
		    + robot.getBounds().width * ppuX;
	    double coordY = (World.SIZE_PLATEAU - robot.getPosition().y) * ppuY
		    + robot.getBounds().height * ppuY;

	    if (Math.abs(initX - coordX) < ppuX + 15f
		    && Math.abs(initY - coordY) < ppuY + 15f) {
		robot.setPosition(new Vector2(World.SIZE_PLATEAU - 1f + 0.1f,
			robot.getPosition().y));
		break;
	    }
	}
    }

    public void leftPressed(int initX, int initY, int width, int height) {
	float ppuX = (float) width / World.SIZE_PLATEAU;
	float ppuY = (float) height / World.SIZE_PLATEAU;
	for (Robot robot : world.getRobots()) {

	    double coordX = robot.getPosition().x * ppuX
		    + robot.getBounds().width * ppuX;
	    double coordY = (World.SIZE_PLATEAU - robot.getPosition().y) * ppuY
		    + robot.getBounds().height * ppuY;

	    if (Math.abs(initX - coordX) < ppuX + 15f
		    && Math.abs(initY - coordY) < ppuY + 15f) {
		robot.setPosition(new Vector2(0.1f, robot.getPosition().y));
		break;
	    }
	}
    }

    public void topPressed(int initX, int initY, int width, int height) {
	float ppuX = (float) width / World.SIZE_PLATEAU;
	float ppuY = (float) height / World.SIZE_PLATEAU;
	for (Robot robot : world.getRobots()) {

	    double coordX = robot.getPosition().x * ppuX
		    + robot.getBounds().width * ppuX;
	    double coordY = (World.SIZE_PLATEAU - robot.getPosition().y) * ppuY
		    + robot.getBounds().height * ppuY;

	    if (Math.abs(initX - coordX) < ppuX + 15f
		    && Math.abs(initY - coordY) < ppuY + 15f) {
		robot.setPosition(new Vector2(robot.getPosition().x, World.SIZE_PLATEAU - 1f + 0.1f));
		break;
	    }
	}
    }

    public void bottomPressed(int initX, int initY, int width, int height) {
	float ppuX = (float) width / World.SIZE_PLATEAU;
	float ppuY = (float) height / World.SIZE_PLATEAU;
	for (Robot robot : world.getRobots()) {

	    double coordX = robot.getPosition().x * ppuX
		    + robot.getBounds().width * ppuX;
	    double coordY = (World.SIZE_PLATEAU - robot.getPosition().y) * ppuY
		    + robot.getBounds().height * ppuY;

	    if (Math.abs(initX - coordX) < ppuX + 15f
		    && Math.abs(initY - coordY) < ppuY + 15f) {
		robot.setPosition(new Vector2(robot.getPosition().x,
			0.1f));
		break;
	    }
	}
    }

}
