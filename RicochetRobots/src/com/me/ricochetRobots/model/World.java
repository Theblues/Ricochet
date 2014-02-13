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

    /** The blocks making up the world **/
    protected Array<Block> world;
    private Random r;
    private ArrayList<Objectif> alObjectif;

    public World() {
	world = new Array<Block>();
	alObjectif = new ArrayList<Objectif>();
	r = new Random();
	createWorld();
    }

    private void createWorld() {
	createBlocks();
	createBlocksObjectif();
	createMiddle();
	initWall();
    }

    private void createBlocks() {
	for (float i = 0f; i < SIZE_PLATEAU; i++)
	    for (float j = 0f; j < SIZE_PLATEAU; j++)
		world.add(new Block(new Vector2(i, j), 1, "game", "blanc"));
    }

    private void createMiddle() {
	float size = SIZE_PLATEAU / 2f;
	int rand = r.nextInt(alObjectif.size());
	world.add(new Block(new Vector2(size - 0.5f, size - 0.5f), 1,
		alObjectif.get(rand).getForm(), alObjectif.get(rand).getColor()));
    }

    private void createBlocksObjectif() {
	try {
	    /*
	     * Ouverture du fichier
	     */
	    InputStream is = Gdx.files.internal("data/ressources/plateau1.txt").read();
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
		for (int i = 0; i < world.size; i++)
		    if (world.get(i).getPosition().x == x
			    && world.get(i).getPosition().y == y)
			world.removeIndex(i);
		world.add(new Block(new Vector2(x, y), 1, form, color));
		alObjectif.add(new Objectif(form, color));
	    }
	    br.close();
	    isr.close();
	    is.close();
	} catch (Exception e) {
	    System.out.println(e.toString());
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
		    world.add(new Block(new Vector2(i, j), 0.1f, 1f, "wall",
			    "black"));
		if (i == SIZE_PLATEAU - 1f)
		    world.add(new Block(new Vector2(i + 1f, j), -0.1f, 1f,
			    "wall", "black"));
		if (j == 0f)
		    world.add(new Block(new Vector2(i, j), 1f, 0.1f, "wall",
			    "black"));
		if (j == SIZE_PLATEAU - 1f)
		    world.add(new Block(new Vector2(i, j + 1f), 1f, -0.1f,
			    "wall", "black"));
		if (i == (SIZE_PLATEAU / 2f - 1f)
			&& j == (SIZE_PLATEAU / 2f - 1f)) {
		    world.add(new Block(new Vector2(i, j), 0.1f, 1f, "wall",
			    "black"));
		    world.add(new Block(new Vector2(i, j), 1f, 0.1f, "wall",
			    "black"));
		}
		if (i == (SIZE_PLATEAU / 2f - 1f)
			&& j == (SIZE_PLATEAU / 2f + 1)) {
		    world.add(new Block(new Vector2(i, j), 0.1f, -1f, "wall",
			    "black"));
		    world.add(new Block(new Vector2(i, j), 1f, -0.1f, "wall",
			    "black"));
		}
		if (i == (SIZE_PLATEAU / 2f + 1f)
			&& j == (SIZE_PLATEAU / 2f - 1)) {
		    world.add(new Block(new Vector2(i, j), -0.1f, 1f, "wall",
			    "black"));
		    world.add(new Block(new Vector2(i, j), -1f, 0.1f, "wall",
			    "black"));
		}
		if (i == (SIZE_PLATEAU / 2f + 1f)
			&& j == (SIZE_PLATEAU / 2f + 1)) {
		    world.add(new Block(new Vector2(i, j), -0.1f, -1f, "wall",
			    "black"));
		    world.add(new Block(new Vector2(i, j), -1f, -0.1f, "wall",
			    "black"));
		}
	    }
	}
    }

    public Array<Block> getWorld() {
	return world;
    }
}
