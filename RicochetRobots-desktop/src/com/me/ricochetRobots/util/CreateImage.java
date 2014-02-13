package com.me.ricochetRobots.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.image.*;
import javax.imageio.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Dans cette classe : On crée une image de taille 255x255, On y dessine une
 * ligne diagonale en rouge L'objet TestBuffered sert à afficher cette image.
 * 
 * 
 * voir aussi : uiswing/painting/usingImages.html dans le tutorial de java.
 * 
 */

public class CreateImage {
    // Constantes
    public static final int DIMX = 72;
    public static final int DIMY = 72;

    BufferedImage image;

    public CreateImage() {
    }

    public BufferedImage getImage() {
	return image;
    }

    public void creerImage() {
	// On crée l'image en RGB.
	image = new BufferedImage(DIMX, DIMY, BufferedImage.TYPE_INT_RGB);
    }

    public void dessinerImage(Color couleur) {
	Graphics2D g = image.createGraphics();

	
	g.setColor(couleur);
	g.fillOval(10, 10, 50, 50);

    }

    public void sauverImage(String nom, String format) {
	try {
	    // On sauve l'image histoire de rire un peu :
	    File fic = new File(nom + "." + format);
	    ImageIO.write(getImage(), format, fic);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static void main(String args[]) {
	CreateImage t = new CreateImage();
	t.creerImage();
	for (ListColor color : ListColor.values()) {
	    t.dessinerImage(color.getColor());
	    t.sauverImage("block_cercle_" + color.getValeur(), "png");
	}
    }
}