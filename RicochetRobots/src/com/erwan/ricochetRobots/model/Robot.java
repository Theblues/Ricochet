package com.erwan.ricochetRobots.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Robot {

    public enum State {
	IDLE, WALKING
    }

    public static final float SIZE_ROBOT = 0.8f;
    public static final float SPEED_ROBOT = 4f; // unit per second

    protected Vector2 position;
    protected Vector2 velocity;
    protected Vector2 positionMax;
    protected Rectangle bounds;
    protected float stateTime;
    protected Texture texture;
    protected String color;
    protected State state;
    protected char direction;

    public Robot(Vector2 position, String color) {
	this.position = position;
	velocity = new Vector2();
	positionMax = new Vector2();

	bounds = new Rectangle();
	bounds.height = SIZE_ROBOT;
	bounds.width = SIZE_ROBOT;
	this.color = color;
	texture = new Texture(Gdx.files.internal("data/images/robots/robot_"
		+ color + ".png"));
	state = State.IDLE;
	stateTime = 0;
	direction = '0';
    }
    
    public Robot(Robot robot) {
	this(new Vector2(robot.position.x, robot.position.y), robot.color);
    }

    public void update(float delta) {
	stateTime += delta;
	position.add(velocity.cpy().scl(delta));
    }

    public State getState() {
	return state;
    }

    public void setState(State state) {
	this.state = state;
    }

    public Vector2 getPositionMax() {
	return positionMax;
    }

    public Vector2 getVelocity() {
	return velocity;
    }

    public Vector2 getPosition() {
	return position;
    }

    public Rectangle getBounds() {
	return bounds;
    }

    public Texture getTexture() {
	return texture;
    }

    public String getColor() {
	return color;
    }

    public char getDirection() {
	return direction;
    }

    public void setDirection(char direction) {
	this.direction = direction;
    }
}
