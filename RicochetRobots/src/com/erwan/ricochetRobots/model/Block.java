package com.erwan.ricochetRobots.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {
    protected Texture texture;
    protected Vector2 position;
    protected Rectangle bounds;
    protected String color;
    protected String form;

    public Block(Vector2 pos, float size, String form, String color) {
	this.position = pos;
	bounds = new Rectangle();
	bounds.width = size;
	bounds.height = size;
	this.form = form;
	this.color = color;
	texture = new Texture(Gdx.files.internal("data/images/blocks/block_"
		+ form + "_" + color + ".png"));
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

    public String getForm() {
	return form;
    }
}
