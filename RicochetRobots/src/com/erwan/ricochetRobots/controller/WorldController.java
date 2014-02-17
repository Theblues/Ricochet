package com.erwan.ricochetRobots.controller;

import com.badlogic.gdx.math.Vector2;
import com.erwan.ricochetRobots.model.Mur;
import com.erwan.ricochetRobots.model.Robot;
import com.erwan.ricochetRobots.model.World;

public class WorldController {

    private World world;
    private Robot robotMove;

    public WorldController(World world) {
	this.world = world;
	robotMove = null;
    }

    public Robot getRobotMove() {
        return robotMove;
    }

    public void rightPressed() {
	// on deplace notre robot
	if (robotMove != null) {
	    float robotX = robotMove.getPosition().x;
	    float robotY = robotMove.getPosition().y;
	    float moveX = World.SIZE_PLATEAU;
	    float moveY = robotMove.getPosition().y;

	    for (Robot robot : world.getRobots())
		if (robot.getPosition().y == robotY)
		    if (robot.getPosition().x > robotX)
			if (robot.getPosition().x < moveX)
			    moveX = robot.getPosition().x;
	    for (Mur mur : world.getMurs())
		if (mur.getPosition().y == robotY)
		    if (mur.getPosition().x > robotX)
			if (mur.getPosition().x < moveX)
			    if (mur.getBounds().height == 1)
			    moveX = mur.getPosition().x;

	    // on deplace notre robot
	    robotMove.setPosition(new Vector2(moveX-1, moveY));
	}
    }

    public void leftPressed() {
	if (robotMove != null) {
	    float robotX = robotMove.getPosition().x;
	    float robotY = robotMove.getPosition().y;
	    float moveX = 0f;
	    float moveY = robotMove.getPosition().y;

	    for (Robot robot : world.getRobots())
		if (robot.getPosition().y == robotY)
		    if (robot.getPosition().x < robotX)
			if (robot.getPosition().x >= moveX)
			    moveX = robot.getPosition().x + 1;
	    for (Mur mur : world.getMurs())
		if (mur.getPosition().y == robotY)
		    if (mur.getPosition().x <= robotX)
			if (mur.getPosition().x > moveX)
			    if (mur.getBounds().height == 1)
			    moveX = mur.getPosition().x;
	    // on deplace notre robot
	    robotMove.setPosition(new Vector2(moveX, moveY));
	}
    }

    public void topPressed() {
	if (robotMove != null) {
	    float robotX = robotMove.getPosition().x;
	    float robotY = robotMove.getPosition().y;
	    float moveX = robotMove.getPosition().x;
	    float moveY = World.SIZE_PLATEAU;

	    for (Robot robot : world.getRobots())
		if (robot.getPosition().x == robotX)
		    if (robot.getPosition().y > robotY)
			if (robot.getPosition().y < moveY)
			    moveY = robot.getPosition().y;
	    for (Mur mur : world.getMurs())
		if (mur.getPosition().x == robotX)
		    if (mur.getPosition().y > robotY)
			if (mur.getPosition().y < moveY)
			    if (mur.getBounds().width == 1)
			    moveY = mur.getPosition().y;

	    // on deplace notre robot
	    robotMove.setPosition(new Vector2(moveX, moveY-1));
	}
    }

    public void bottomPressed() {
	
	if (robotMove != null) {
	    float robotX = robotMove.getPosition().x;
	    float robotY = robotMove.getPosition().y;
	    float moveX = robotMove.getPosition().x;
	    float moveY = 0f;

	    for (Robot robot : world.getRobots())
		if (robot.getPosition().x == robotX)
		    if (robot.getPosition().y < robotY)
			if (robot.getPosition().y >= moveY)
			    moveY = robot.getPosition().y + 1;
	    for (Mur mur : world.getMurs())
		if (mur.getPosition().x == robotX)
		    if (mur.getPosition().y <= robotY)
			if (mur.getPosition().y > moveY)
			    if (mur.getBounds().width == 1)
			    moveY = mur.getPosition().y;
	    // on deplace notre robot
	    robotMove.setPosition(new Vector2(moveX, moveY));
	}
    }

    public void touchDown(int screenX, int screenY, int width, int height) {
	float ppuX = (float) width / World.SIZE_PLATEAU;
	float ppuY = (float) height / World.SIZE_PLATEAU;
	robotMove = null;
	for (Robot robot : world.getRobots()) {
	    double coordX = robot.getPosition().x * ppuX
		    + robot.getBounds().width * ppuX;
	    double coordY = (World.SIZE_PLATEAU - robot.getPosition().y) * ppuY
		    + robot.getBounds().height * ppuY;

	    if (Math.abs(screenX - coordX) < ppuX + .25f * ppuX
		    && Math.abs(screenY - coordY) < ppuY + .25f * ppuY) {
		robotMove = robot;
		break;
	    }
	}	
    }
}
