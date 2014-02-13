package com.me.ricochetRobots.util;

import java.awt.Color;

public enum ListColor {

    // Objets directement construits
    RED(Color.RED, "rouge"), GREEN(Color.GREEN, "vert"), BLUE(Color.BLUE,
	    "bleu"), YELLOW(Color.YELLOW, "jaune");

    private Color color;
    private String valeur;

    ListColor(Color color, String valeur) {
	this.color = color;
	this.valeur = valeur;
    }

    public Color getColor() {
	return color;
    }

    public String getValeur() {
	return valeur;
    }
}
