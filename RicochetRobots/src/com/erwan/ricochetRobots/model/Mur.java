package com.erwan.ricochetRobots.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Mur {
    protected Texture texture;
    protected Vector2 position;
    protected Rectangle bounds;

    public Mur(Vector2 pos, float width, float height) {
	position = pos;
	bounds = new Rectangle();
	bounds.width = width;
	bounds.height = height;
	texture = new Texture(Gdx.files.internal("data/images/wall.png"));
    }

    public Texture getTexture() {
	return texture;
    }

    public Vector2 getPosition() {
	return position;
    }

    public Rectangle getBounds() {
	return bounds;
    }
}
