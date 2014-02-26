package com.erwan.ricochetRobots.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.erwan.ricochetRobots.model.Mur;
import com.erwan.ricochetRobots.model.Robot;
import com.erwan.ricochetRobots.model.Solo;

public class SoloController {

    private Solo solo;
    private Robot robotMove;
    private Robot ancienRobot;
    private char direction;
    private float top;

    public SoloController(Solo solo, float top) {
	this.solo = solo;
	robotMove = null;
	this.top = top;
    }

    public Robot getRobotMove() {
	return robotMove;
    }

    public void rightPressed() {
	if (robotMove != null) {
	    // on ne pêut faire le déplacement inverse
	    if (!(ancienRobot != null && ancienRobot.equals(robotMove) && direction == 'L')) {
		// on recupere les coordonnes de notre robots
		float robotX = robotMove.getPosition().x;
		float robotY = robotMove.getPosition().y;
		// on recupere les nouvelles coordonées maximums que le robot
		// peut faire
		float moveX = Solo.SIZE_PLATEAU;
		float moveY = robotMove.getPosition().y;

		// on regarde s'il y a un robot sur la route
		for (Robot robot : solo.getRobots())
		    if (robot.getPosition().y == robotY)
			if (robot.getPosition().x > robotX)
			    if (robot.getPosition().x < moveX)
				moveX = robot.getPosition().x;
		// on regarde s'il y a un mur sur la route
		for (Mur mur : solo.getMurs())
		    if (mur.getPosition().y == robotY)
			if (mur.getPosition().x > robotX)
			    if (mur.getPosition().x < moveX)
				if (mur.getBounds().height == 1)
				    moveX = mur.getPosition().x;

		// on deplace notre robot
		robotMove.setPosition(new Vector2(moveX - 1, moveY));
		// on met a jour les compteurs et on verifie si la partie est
		// fini
		solo.deplacementRobot(robotMove);
		if (!solo.objectifAccompli(robotMove)) {
		    ancienRobot = robotMove;
		    direction = 'R';
		}
	    } else {
		solo.setMessageColor(Color.RED);
		solo.setMessageTimer((int) ((solo.getChrono().getFinalTime() / 1000) % 60));
		solo.setMessage("Déplacement Impossible !");
	    }
	}
    }

    public void leftPressed() {
	if (robotMove != null) {
	    if (!(ancienRobot != null && ancienRobot.equals(robotMove) && direction == 'R')) {
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

		robotMove.setPosition(new Vector2(moveX, moveY));
		solo.deplacementRobot(robotMove);
		if (!solo.objectifAccompli(robotMove)) {
		    ancienRobot = robotMove;
		    direction = 'L';
		}
	    } else {
		solo.setMessageColor(Color.RED);
		solo.setMessageTimer((int) ((solo.getChrono().getFinalTime() / 1000) % 60));
		solo.setMessage("Déplacement Impossible !");
	    }
	}
    }

    public void topPressed() {
	if (robotMove != null) {
	    if (!(ancienRobot != null && ancienRobot.equals(robotMove) && direction == 'B')) {
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

		robotMove.setPosition(new Vector2(moveX, moveY - 1));
		solo.deplacementRobot(robotMove);
		if (!solo.objectifAccompli(robotMove)) {
		    ancienRobot = robotMove;
		    direction = 'T';
		}
	    } else {
		solo.setMessageColor(Color.RED);
		solo.setMessageTimer((int) ((solo.getChrono().getFinalTime() / 1000) % 60));
		solo.setMessage("Déplacement Impossible !");
	    }
	}
    }

    public void bottomPressed() {
	if (robotMove != null) {
	    if (!(ancienRobot != null && ancienRobot.equals(robotMove) && direction == 'T')) {
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

		robotMove.setPosition(new Vector2(moveX, moveY));
		solo.deplacementRobot(robotMove);
		if (!solo.objectifAccompli(robotMove)) {
		    ancienRobot = robotMove;
		    direction = 'B';
		}
	    } else {
		solo.setMessageColor(Color.RED);
		solo.setMessageTimer((int) ((solo.getChrono().getFinalTime() / 1000) % 60));
		solo.setMessage("Déplacement Impossible !");
	    }
	}
    }

    public void touchDown(int screenX, int screenY) {
	float ppu = Gdx.graphics.getWidth() / Solo.SIZE_PLATEAU;
	robotMove = null;
	for (Robot robot : solo.getRobots()) {
	    double coordX = robot.getPosition().x * ppu;
	    // les coordonées du toucher est inversé par rapport au dessin
	    double coordY = Gdx.graphics.getHeight()
		    - (top - Gdx.graphics.getWidth() + (robot.getPosition().y)
			    * ppu);

	    if (screenX > coordX && screenX < coordX + ppu && screenY < coordY
		    && screenY > coordY - ppu) {
		robotMove = robot;
		break;
	    }
	}
    }
}