package com.me.ricochetRobots.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Robot {
    
    static final float SIZE = 0.8f;

    protected Vector2 position = new Vector2();
    protected Rectangle bounds = new Rectangle();
    protected Texture texture;
    protected String color;

    public Robot(Vector2 position, String color) {
	this.position = position;
	position.x += 0.1;
	position.y += 0.1;
	this.bounds.height = SIZE;
	this.bounds.width = SIZE;
	this.color = color;
	texture = new Texture(Gdx.files.internal("data/images/robot_" + color + ".png"));
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
}
