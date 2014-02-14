package com.erwan.ricochetRobots.model;

public class Objectif {
    private String color;
    private String form;

    public Objectif(String form, String color) {
	this.color = color;
	this.form = form;
    }

    public String getColor() {
	return color;
    }

    public String getForm() {
	return form;
    }
}
