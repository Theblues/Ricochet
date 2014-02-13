package com.me.ricochetRobots.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {
    protected Texture texture;
    protected Vector2 position;
    protected Rectangle bounds;

    public Block(Vector2 pos, float width, float height,
	    String form, String color) {
	this.position = pos;
	bounds = new Rectangle();
	bounds.width = width;
	bounds.height = height;
	texture = new Texture(Gdx.files.internal("data/images/block_" + form + "_" + color + ".png"));
    }

    public Block(Vector2 pos, float size,  String form, String color) {
	this(pos, size, size, form, color);
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
}
