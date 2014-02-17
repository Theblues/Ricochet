package com.erwan.ricochetRobots.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {
    public static final float SIZE_PLATEAU = 16;

    /** The blocks making up the blocks **/
    protected Array<Block> blocks;
    protected Array<Robot> robots;
    protected Array<Mur> murs;
    private ArrayList<Objectif> alObjectif;
    private Random r;

    public World() {
	blocks = new Array<Block>();
	robots = new Array<Robot>();
	murs = new Array<Mur>();
	alObjectif = new ArrayList<Objectif>();
	r = new Random();
	createWorld();
    }

    private void createWorld() {
	createBlocks();
	createBlocksObjectif();
	createMiddle();
	initWall();
	initRobots();
    }

    private void createBlocks() {
	for (float i = 0f; i < SIZE_PLATEAU; i++)
	    for (float j = 0f; j < SIZE_PLATEAU; j++)
		// on n'en met pas au milieu
		if (i < SIZE_PLATEAU / 2f - 1 || i > SIZE_PLATEAU / 2f
			|| j < SIZE_PLATEAU / 2f - 1 || j > SIZE_PLATEAU / 2f)
		    blocks.add(new Block(new Vector2(i, j), 1, "game", "blanc"));
    }

    private void createBlocksObjectif() {
	try {
	    /*
	     * Ouverture du fichier
	     */
	    InputStream is = Gdx.files
		    .internal("data/ressources/plateau_1.txt").read();
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String ligne;
	    while ((ligne = br.readLine()) != null) {
		String[] tabSplit = ligne.split(" ");
		String[] coordonnee = tabSplit[0].split(",");
		String form = tabSplit[1];
		String color = tabSplit[2];
		float x = Integer.parseInt(coordonnee[0]);
		float y = Integer.parseInt(coordonnee[1]);

		for (int i = 0; i < blocks.size; i++)
		    if (blocks.get(i).getPosition().x == x
			    && blocks.get(i).getPosition().y == y)
			blocks.removeIndex(i);
		blocks.add(new Block(new Vector2(x, y), 1, form, color));
		alObjectif.add(new Objectif(form, color));
	    }
	    br.close();
	    isr.close();
	    is.close();
	} catch (Exception e) {
	    System.out.println(e.toString());
	}
    }

    private void createMiddle() {
	float size = SIZE_PLATEAU / 2f;
	int rand = r.nextInt(alObjectif.size());
	blocks.add(new Block(new Vector2(size - 0.5f, size - 0.5f), 1,
		alObjectif.get(rand).getForm(), alObjectif.get(rand).getColor()));
    }

    private void initRobots() {
	int xRand;
	int yRand;
	String[] listColor = { "rouge", "bleu", "jaune", "vert", "noir" };
	for (int i = 0; i < listColor.length; i++) {
	    do {
		xRand = r.nextInt((int) SIZE_PLATEAU);
		yRand = r.nextInt((int) SIZE_PLATEAU);
	    } while (xRand >= SIZE_PLATEAU / 2f - 1
		    && xRand <= SIZE_PLATEAU / 2f
		    && yRand >= SIZE_PLATEAU / 2f - 1
		    && yRand <= SIZE_PLATEAU / 2f);
	    robots.add(new Robot(new Vector2(xRand, yRand),
		    listColor[i]));
	}
    }

    private void initWall() {
	initWallBorder();

	try {
	    InputStream is = Gdx.files.internal("data/ressources/wall_1.txt")
		    .read();
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String ligne;
	    while ((ligne = br.readLine()) != null) {
		String[] tabSplit = ligne.split(" ");
		float initX = Float.parseFloat(tabSplit[0]);
		float initY = Float.parseFloat(tabSplit[1]);
		float width = Float.parseFloat(tabSplit[2]);
		float height = Float.parseFloat(tabSplit[3]);

		murs.add(new Mur(new Vector2(initX, initY), width, height));
	    }
	    br.close();
	    isr.close();
	    is.close();
	} catch (Exception e) {
	    System.out.println(e.toString());
	}
    }

    private void initWallBorder() {
	for (float i = 0f; i < SIZE_PLATEAU; i++) {
	    for (float j = 0f; j < SIZE_PLATEAU; j++) {
		if (i == 0f)
		    murs.add(new Mur(new Vector2(i, j), .1f, 1f));
		if (i == SIZE_PLATEAU - 1f)
		    murs.add(new Mur(new Vector2(i + 1f, j), .1f, 1f));
		if (j == 0f)
		    murs.add(new Mur(new Vector2(i, j), 1f, .1f));
		if (j == SIZE_PLATEAU - 1f)
		    murs.add(new Mur(new Vector2(i, j + 1f), 1f, .1f));
		if (i == 7 && j == 7) {
		    murs.add(new Mur(new Vector2(i, j), 0.1f, 1f));
		    murs.add(new Mur(new Vector2(i, j), 1f, .1f));
		}
		if (i == 7 && j == 8)
		    murs.add(new Mur(new Vector2(i, j), .1f, 1f));
		if (i == 7 && j == 9)
		    murs.add(new Mur(new Vector2(i, j), 1f, .1f));
		if (i == 8 && j == 7)
		    murs.add(new Mur(new Vector2(i, j), 1f, .1f));
		if (i == 8 && j == 9)
		    murs.add(new Mur(new Vector2(i, j), 1f, .1f));
		if (i == 9 && j == 7)
		    murs.add(new Mur(new Vector2(i, j), .1f, 1f));
		if (i == 9 && j == 8)
		    murs.add(new Mur(new Vector2(i, j), .1f, 1f));
	    }
	}
    }

    public Array<Block> getWorld() {
	return blocks;
    }

    public Array<Robot> getRobots() {
	return robots;
    }

    public Array<Mur> getMurs() {
	return murs;
    }
}
