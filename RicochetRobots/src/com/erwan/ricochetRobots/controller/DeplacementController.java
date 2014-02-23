package com.erwan.ricochetRobots.controller;

import com.badlogic.gdx.math.Vector2;
import com.erwan.ricochetRobots.model.Mur;
import com.erwan.ricochetRobots.model.Robot;
import com.erwan.ricochetRobots.model.Solo;

public class DeplacementController {

    private Solo solo;
    private Robot robotMove;

    public DeplacementController(Solo solo) {
	this.solo = solo;
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
	    float moveX = Solo.SIZE_PLATEAU;
	    float moveY = robotMove.getPosition().y;

	    for (Robot robot : solo.getRobots())
		if (robot.getPosition().y == robotY)
		    if (robot.getPosition().x > robotX)
			if (robot.getPosition().x < moveX)
			    moveX = robot.getPosition().x;
	    for (Mur mur : solo.getMurs())
		if (mur.getPosition().y == robotY)
		    if (mur.getPosition().x > robotX)
			if (mur.getPosition().x < moveX)
			    if (mur.getBounds().height == 1)
				moveX = mur.getPosition().x;

	    // on deplace notre robot
	    robotMove.setPosition(new Vector2(moveX - 1, moveY));
	    solo.setNbMouvement(solo.getNbMouvement() + 1);
	    solo.deplacementRobot(robotMove);
	}
    }

    public void leftPressed() {
	if (robotMove != null) {
	    float robotX = robotMove.getPosition().x;
	    float robotY = robotMove.getPosition().y;
	    float moveX = 0f;
	    float moveY = robotMove.getPosition().y;

	    for (Robot robot : solo.getRobots())
		if (robot.getPosition().y == robotY)
		    if (robot.getPosition().x < robotX)
			if (robot.getPosition().x >= moveX)
			    moveX = robot.getPosition().x + 1;
	    for (Mur mur : solo.getMurs())
		if (mur.getPosition().y == robotY)
		    if (mur.getPosition().x <= robotX)
			if (mur.getPosition().x > moveX)
			    if (mur.getBounds().height == 1)
				moveX = mur.getPosition().x;
	    // on deplace notre robot
	    robotMove.setPosition(new Vector2(moveX, moveY));
	    solo.setNbMouvement(solo.getNbMouvement() + 1);
	    solo.deplacementRobot(robotMove);
	}
    }

    public void topPressed() {
	if (robotMove != null) {
	    float robotX = robotMove.getPosition().x;
	    float robotY = robotMove.getPosition().y;
	    float moveX = robotMove.getPosition().x;
	    float moveY = Solo.SIZE_PLATEAU;

	    for (Robot robot : solo.getRobots())
		if (robot.getPosition().x == robotX)
		    if (robot.getPosition().y > robotY)
			if (robot.getPosition().y < moveY)
			    moveY = robot.getPosition().y;
	    for (Mur mur : solo.getMurs())
		if (mur.getPosition().x == robotX)
		    if (mur.getPosition().y > robotY)
			if (mur.getPosition().y < moveY)
			    if (mur.getBounds().width == 1)
				moveY = mur.getPosition().y;

	    // on deplace notre robot
	    robotMove.setPosition(new Vector2(moveX, moveY - 1));
	    solo.setNbMouvement(solo.getNbMouvement() + 1);
	    solo.deplacementRobot(robotMove);
	}
    }

    public void bottomPressed() {

	if (robotMove != null) {
	    float robotX = robotMove.getPosition().x;
	    float robotY = robotMove.getPosition().y;
	    float moveX = robotMove.getPosition().x;
	    float moveY = 0f;

	    for (Robot robot : solo.getRobots())
		if (robot.getPosition().x == robotX)
		    if (robot.getPosition().y < robotY)
			if (robot.getPosition().y >= moveY)
			    moveY = robot.getPosition().y + 1;
	    for (Mur mur : solo.getMurs())
		if (mur.getPosition().x == robotX)
		    if (mur.getPosition().y <= robotY)
			if (mur.getPosition().y > moveY)
			    if (mur.getBounds().width == 1)
				moveY = mur.getPosition().y;
	    // on deplace notre robot
	    robotMove.setPosition(new Vector2(moveX, moveY));
	    solo.setNbMouvement(solo.getNbMouvement() + 1);
	    solo.deplacementRobot(robotMove);
	}
    }

    public void touchDown(int screenX, int screenY, int width,
	    float tailleTop) {
	float ppu = (float) width / Solo.SIZE_PLATEAU;
	robotMove = null;
	for (Robot robot : solo.getRobots()) {
	    double coordX = robot.getPosition().x * ppu;
	    double coordY = (Solo.SIZE_PLATEAU - robot.getPosition().y) * ppu + tailleTop;

	    if (screenX > coordX && screenX < coordX + ppu && screenY < coordY && screenY > coordY - ppu) {		
		robotMove = robot;
		break;
	    }
	}

    }
}
