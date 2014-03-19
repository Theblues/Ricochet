package com.erwan.ricochetRobots.model;

import com.badlogic.gdx.graphics.Color;

public class Information {
    private Chronometre chrono;
    private String message;
    private Color color;

    public Information(String message) {
	this.chrono = new Chronometre();
	this.message = message;
	color = Color.BLACK;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public Chronometre getChrono() {
	return chrono;
    }

    public Color getColor() {
	return color;
    }

    public void setColor(Color color) {
	this.color = color;
    }

}
