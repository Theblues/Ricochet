package com.me.ricochetRobots.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import android.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {
    public static final float SIZE_PLATEAU = 16;

    private static final String TAG_RICOCHET = "RicochetRobot";

    /** The blocks making up the blocks **/
    protected Array<Block> blocks;
    protected Array<Robot> robots;
    private Random r;
    private ArrayList<Objectif> alObjectif;

    public World() {
	blocks = new Array<Block>();
	robots = new Array<Robot>();
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
	    InputStream is = Gdx.files.internal("data/ressources/plateau1.txt")
		    .read();
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String ligne;
	    while ((ligne = br.readLine()) != null) {
		String[] tabSplit = ligne.split(" ");
		String[] coordonnee = tabSplit[0].split(",");
		String form = tabSplit[1];
		String color = tabSplit[2];
		float x = Integer.parseInt(coordonnee[0]) - 1;
		float y = SIZE_PLATEAU - Integer.parseInt(coordonnee[1]);
		Log.i(TAG_RICOCHET, x + " " + y);
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
	    } while (xRand > SIZE_PLATEAU / 2f - 1 && xRand < SIZE_PLATEAU / 2f
		    && yRand > SIZE_PLATEAU / 2f - 1
		    && yRand < SIZE_PLATEAU / 2f);
	    robots.add(new Robot(new Vector2(xRand, yRand), listColor[i]));
	}
    }

    private void initWall() {
	initWallBorder();
	// / TODO le reste des murs
    }

    private void initWallBorder() {
	for (float i = 0f; i < SIZE_PLATEAU; i++) {
	    for (float j = 0f; j < SIZE_PLATEAU; j++) {
		if (i == 0f)
		    blocks.add(new Block(new Vector2(i, j), 0.1f, 1f, "wall",
			    "black"));
		if (i == SIZE_PLATEAU - 1f)
		    blocks.add(new Block(new Vector2(i + 1f, j), -0.1f, 1f,
			    "wall", "black"));
		if (j == 0f)
		    blocks.add(new Block(new Vector2(i, j), 1f, 0.1f, "wall",
			    "black"));
		if (j == SIZE_PLATEAU - 1f)
		    blocks.add(new Block(new Vector2(i, j + 1f), 1f, -0.1f,
			    "wall", "black"));
		if (i == (SIZE_PLATEAU / 2f - 1f)
			&& j == (SIZE_PLATEAU / 2f - 1f)) {
		    blocks.add(new Block(new Vector2(i, j), 0.1f, 1f, "wall",
			    "black"));
		    blocks.add(new Block(new Vector2(i, j), 1f, 0.1f, "wall",
			    "black"));
		}
		if (i == (SIZE_PLATEAU / 2f - 1f)
			&& j == (SIZE_PLATEAU / 2f + 1)) {
		    blocks.add(new Block(new Vector2(i, j), 0.1f, -1f, "wall",
			    "black"));
		    blocks.add(new Block(new Vector2(i, j), 1f, -0.1f, "wall",
			    "black"));
		}
		if (i == (SIZE_PLATEAU / 2f + 1f)
			&& j == (SIZE_PLATEAU / 2f - 1)) {
		    blocks.add(new Block(new Vector2(i, j), -0.1f, 1f, "wall",
			    "black"));
		    blocks.add(new Block(new Vector2(i, j), -1f, 0.1f, "wall",
			    "black"));
		}
		if (i == (SIZE_PLATEAU / 2f + 1f)
			&& j == (SIZE_PLATEAU / 2f + 1)) {
		    blocks.add(new Block(new Vector2(i, j), -0.1f, -1f, "wall",
			    "black"));
		    blocks.add(new Block(new Vector2(i, j), -1f, -0.1f, "wall",
			    "black"));
		}
	    }
	}
    }

    public Array<Block> getWorld() {
	return blocks;
    }

    public Array<Robot> getRobots() {
	return robots;
    }
}
