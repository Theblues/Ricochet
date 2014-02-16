package com.erwan.ricochetRobots.controller;

import com.badlogic.gdx.math.Vector2;
import com.erwan.ricochetRobots.model.Robot;
import com.erwan.ricochetRobots.model.World;

public class WorldController {

    private World world;

    public WorldController(World world) {
	this.world = world;
	
    }

    public void rightPressed(int initX, int initY, int width, int height) {
	float ppuX = (float) width / World.SIZE_PLATEAU;
	float ppuY = (float) height / World.SIZE_PLATEAU;
	Robot robotMove = null;
	// On recupere notre robot initiale
	for (Robot robot : world.getRobots()) {

	    double coordX = robot.getPosition().x * ppuX
		    + robot.getBounds().width * ppuX;
	    double coordY = (World.SIZE_PLATEAU - robot.getPosition().y) * ppuY
		    + robot.getBounds().height * ppuY;

	    if (Math.abs(initX - coordX) < ppuX + 15f
		    && Math.abs(initY - coordY) < ppuY + 15f) {
		robotMove = robot;
		break;
	    }
	}
	// on deplace notre robot
	if (robotMove != null) {
	    float moveX = World.SIZE_PLATEAU - 1f + 0.1f;
	    float moveY = robotMove.getPosition().y;

	    for (Robot robot : world.getRobots())
		if (robot.getPosition().y == robotMove.getPosition().y
			&& robot.getPosition().x > robotMove.getPosition().x)
		    if (robot.getPosition().x < moveX)
			moveX = robot.getPosition().x - 1;

	    // on deplace notre robot
	    robotMove.setPosition(new Vector2(moveX, moveY));
	}
    }

    public void leftPressed(int initX, int initY, int width, int height) {
	float ppuX = (float) width / World.SIZE_PLATEAU;
	float ppuY = (float) height / World.SIZE_PLATEAU;
	Robot robotMove = null;
	for (Robot robot : world.getRobots()) {

	    double coordX = robot.getPosition().x * ppuX
		    + robot.getBounds().width * ppuX;
	    double coordY = (World.SIZE_PLATEAU - robot.getPosition().y) * ppuY
		    + robot.getBounds().height * ppuY;

	    if (Math.abs(initX - coordX) < ppuX + 15f
		    && Math.abs(initY - coordY) < ppuY + 15f) {
		robotMove = robot;
		break;
	    }
	}
	if (robotMove != null) {
	    float moveX = 0.1f;
	    float moveY = robotMove.getPosition().y;

	    for (Robot robot : world.getRobots())
		if (robot.getPosition().y == robotMove.getPosition().y
			&& robot.getPosition().x < robotMove.getPosition().x)
		    if (robot.getPosition().x > moveX)
			moveX = robot.getPosition().x + 1;

	    // on deplace notre robot
	    robotMove.setPosition(new Vector2(moveX, moveY));
	}
    }

    public void topPressed(int initX, int initY, int width, int height) {
	float ppuX = (float) width / World.SIZE_PLATEAU;
	float ppuY = (float) height / World.SIZE_PLATEAU;
	Robot robotMove = null;
	for (Robot robot : world.getRobots()) {

	    double coordX = robot.getPosition().x * ppuX
		    + robot.getBounds().width * ppuX;
	    double coordY = (World.SIZE_PLATEAU - robot.getPosition().y) * ppuY
		    + robot.getBounds().height * ppuY;

	    if (Math.abs(initX - coordX) < ppuX + 15f
		    && Math.abs(initY - coordY) < ppuY + 15f) {
		robotMove = robot;
		break;
	    }
	}
	if (robotMove != null) {
	    float moveX = robotMove.getPosition().x;
	    float moveY = World.SIZE_PLATEAU - 1f + 0.1f;

	    for (Robot robot : world.getRobots())
		if (robot.getPosition().x == robotMove.getPosition().x
			&& robot.getPosition().y > robotMove.getPosition().y)
		    if (robot.getPosition().y < moveY)
			moveY = robot.getPosition().y - 1;

	    // on deplace notre robot
	    robotMove.setPosition(new Vector2(moveX, moveY));
	}
    }

    public void bottomPressed(int initX, int initY, int width, int height) {
	float ppuX = (float) width / World.SIZE_PLATEAU;
	float ppuY = (float) height / World.SIZE_PLATEAU;
	Robot robotMove = null;
	for (Robot robot : world.getRobots()) {

	    double coordX = robot.getPosition().x * ppuX
		    + robot.getBounds().width * ppuX;
	    double coordY = (World.SIZE_PLATEAU - robot.getPosition().y) * ppuY
		    + robot.getBounds().height * ppuY;

	    if (Math.abs(initX - coordX) < ppuX + 15f
		    && Math.abs(initY - coordY) < ppuY + 15f) {		    
		robotMove = robot;
		break;
	    }
	}
	if (robotMove != null) {
	    float moveX = robotMove.getPosition().x;
	    float moveY = 0.1f;

	    for (Robot robot : world.getRobots())
		if (robot.getPosition().x == robotMove.getPosition().x
			&& robot.getPosition().y < robotMove.getPosition().y)
		    if (robot.getPosition().y > moveY)
			moveY = robot.getPosition().y + 1;
	    // on deplace notre robot
	    robotMove.setPosition(new Vector2(moveX, moveY));
	}
    }
}
