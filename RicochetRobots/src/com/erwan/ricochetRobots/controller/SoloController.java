package com.erwan.ricochetRobots.controller;

import android.os.SystemClock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.erwan.ricochetRobots.model.Mur;
import com.erwan.ricochetRobots.model.Robot;
import com.erwan.ricochetRobots.model.Robot.State;
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

    public void update(float delta) {
	if (robotMove == null)
	    return;

	// si le robot est a l'arret on ne fait rien
	if (robotMove.getState() == State.IDLE)
	    return;
	
	switch (robotMove.getDirection()) {
	case 'R':
	case 'T':
	    if (robotMove.getPosition().x >= robotMove.getPositionMax().x
		    && robotMove.getPosition().y >= robotMove.getPositionMax().y) {
		// on recalibre la position
		robotMove.getPosition().x = robotMove.getPositionMax().x;
		robotMove.getPosition().y = robotMove.getPositionMax().y;
		// on reinitialise les positions max
		robotMove.getPositionMax().x = 0;
		robotMove.getPositionMax().y = 0;
		// on arrete le robot
		robotMove.getVelocity().x = 0;
		robotMove.getVelocity().y = 0;
		// le robot repasse en etat stationaire
		robotMove.setState(State.IDLE);
		solo.deplacementRobot(robotMove);
	    }
	    break;
	case 'L':
	case 'B':
	    if (robotMove.getPosition().x <= robotMove.getPositionMax().x
		    && robotMove.getPosition().y <= robotMove.getPositionMax().y) {
		robotMove.getPosition().x = robotMove.getPositionMax().x;
		robotMove.getPosition().y = robotMove.getPositionMax().y;
		robotMove.getPositionMax().x = 0;
		robotMove.getPositionMax().y = 0;
		robotMove.getVelocity().x = 0;
		robotMove.getVelocity().y = 0;
		robotMove.setState(State.IDLE);
		solo.deplacementRobot(robotMove);
	    }
	    break;
	}
	robotMove.update(delta);
    }

    public void rightPressed() {
	if (robotMove != null) {
	    if (robotMove.getState() != State.IDLE)
		    return;
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
		
		// on lance le déplacement de notre robot
		robotMove.getVelocity().x = Robot.SPEED_ROBOT;
		robotMove.getVelocity().y = 0;
		robotMove.getPositionMax().x = moveX - 1;
		robotMove.getPositionMax().y = moveY;
		robotMove.setState(State.WALKING);
		robotMove.setDirection('R');
		
		if (!solo.objectifAccompli(robotMove)) {
		    ancienRobot = robotMove;
		    direction = 'R';
		}
	    } else {
		messageErreur();
	    }
	}
    }

    public void leftPressed() {
	if (robotMove != null) {
	    if (robotMove.getState() != State.IDLE)
		    return;
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

		robotMove.getVelocity().x = -Robot.SPEED_ROBOT;
		robotMove.getVelocity().y = 0;
		robotMove.getPositionMax().x = moveX;
		robotMove.getPositionMax().y = moveY;
		robotMove.setState(State.WALKING);
		robotMove.setDirection('L');
		if (!solo.objectifAccompli(robotMove)) {
		    ancienRobot = robotMove;
		    direction = 'L';
		}
	    } else {
		messageErreur();
	    }
	}
    }

    public void topPressed() {
	if (robotMove != null) {
	    if (robotMove.getState() != State.IDLE)
		    return;
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

		robotMove.getVelocity().x = 0;
		robotMove.getVelocity().y = Robot.SPEED_ROBOT;
		robotMove.getPositionMax().x = moveX;
		robotMove.getPositionMax().y = moveY - 1;
		robotMove.setState(State.WALKING);
		robotMove.setDirection('T');
		if (!solo.objectifAccompli(robotMove)) {
		    ancienRobot = robotMove;
		    direction = 'T';
		}
	    } else {
		messageErreur();
	    }
	}
    }

    public void bottomPressed() {
	if (robotMove != null) {
	    if (robotMove.getState() != State.IDLE)
		    return;
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

		robotMove.getVelocity().x = 0;
		robotMove.getVelocity().y = -Robot.SPEED_ROBOT;
		robotMove.getPositionMax().x = moveX;
		robotMove.getPositionMax().y = moveY;
		robotMove.setState(State.WALKING);
		robotMove.setDirection('B');
		if (!solo.objectifAccompli(robotMove)) {
		    ancienRobot = robotMove;
		    direction = 'B';
		}
	    } else {
		messageErreur();
	    }
	}
    }

    public void touchDown(int screenX, int screenY) {
	if (robotMove != null && robotMove.getState() != State.IDLE)
	    return;
	robotMove = null;
	
	float ppu = Gdx.graphics.getWidth() / Solo.SIZE_PLATEAU;
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
    
    private void messageErreur() {
	solo.getInfo().setColor(Color.RED);
	solo.getInfo().setMessage("Déplacement Impossible !");
	solo.getInfo().getChrono().setStartTime(SystemClock.uptimeMillis());
    }
}