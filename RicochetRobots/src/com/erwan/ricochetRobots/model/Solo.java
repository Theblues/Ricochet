package com.erwan.ricochetRobots.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import android.os.SystemClock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Solo {
    public static final float SIZE_PLATEAU = 16;

    /** The blocks making up the blocks **/
    protected Array<Block> blocks;
    protected Array<Robot> robots;
    protected Array<Mur> murs;
    private ArrayList<Objectif> alObjectif;
    private Objectif objectifEnCours;
    private int nbMouvement;
    private int nbMouvementTotal;
    private Random r;
    private Chronometre chrono;
    private Chronometre chronoTotal;
    private boolean stop;

    private Information info;

    public Solo() {
	blocks = new Array<Block>();
	robots = new Array<Robot>();
	murs = new Array<Mur>();
	alObjectif = new ArrayList<Objectif>();
	r = new Random();
	nbMouvement = nbMouvementTotal = 0;
	chrono = new Chronometre();
	chronoTotal = new Chronometre();
	stop = false;
	info = new Information("");
	createWorld();
    }

    private void createWorld() {
	createBlocks();
	createBlocksObjectif();
	createMiddle();
	initWallBorder();
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
	/*
	 * Ouverture du fichier
	 */
	Array<Integer> liste = new Array<Integer>();
	liste.add(0);
	liste.add(1);
	liste.add(2);
	liste.add(3);
	for (int cpt = 0; cpt < 4; cpt++) {
	    int rand = r.nextInt(liste.size);
	    int nbPlateau = liste.get(rand);
	    liste.removeIndex(rand);
	    int verso = r.nextInt(2);
	    initWall(cpt, nbPlateau, verso);
	    try {
		InputStream is = Gdx.files.internal(
			"data/ressources/plateau_" + nbPlateau + "_" + verso
				+ ".txt").read();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String ligne;
		while ((ligne = br.readLine()) != null) {
		    String[] tabSplit = ligne.split(" ");
		    String[] coordonnee = tabSplit[0].split(",");

		    // on effectue nos rotations
		    coordonnee = rotate(coordonnee, cpt);
		    // on effectue nos translations
		    coordonnee = translate(coordonnee, cpt);

		    // on recupere les informations de notre blocs
		    float x = Float.parseFloat(coordonnee[0]);
		    float y = Float.parseFloat(coordonnee[1]);
		    String form = tabSplit[1];
		    String color = tabSplit[2];

		    // on supprime les blocs normaux
		    suppressionBlock(x, y);

		    // on ajoute notre bloc objectif
		    blocks.add(new Block(new Vector2(x, y), 1, form, color));
		    // on l'ajoute a la liste des objectif
		    alObjectif.add(new Objectif(form, color));
		}
		br.close();
		isr.close();
		is.close();
	    } catch (Exception e) {
		System.out.println(e.toString());
	    }
	}
    }

    private void initWall(int cpt, int nbPlateau, int verso) {
	try {
	    InputStream is = Gdx.files.internal(
		    "data/ressources/wall_" + nbPlateau + "_" + verso + ".txt")
		    .read();
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String ligne;
	    while ((ligne = br.readLine()) != null) {
		String[] tabSplit = ligne.split(" ");

		// on effectue nos rotations
		tabSplit = rotateWall(tabSplit, cpt);
		// on effectue nos translations
		tabSplit = translate(tabSplit, cpt);

		// on recupere les coordonées et la taille des murs
		float initX = Float.parseFloat(tabSplit[0]);
		float initY = Float.parseFloat(tabSplit[1]);
		float width = Float.parseFloat(tabSplit[2]);
		float height = Float.parseFloat(tabSplit[3]);
		// on crée nos murs
		murs.add(new Mur(new Vector2(initX, initY), width, height));
	    }
	    br.close();
	    isr.close();
	    is.close();
	} catch (Exception e) {
	    System.out.println(e.toString());
	}
    }

    private void initRobots() {
	int xRand;
	int yRand;
	String[] listColor = { "rouge", "bleu", "jaune", "vert", "noir" };
	for (int i = 0; i < listColor.length; i++) {
	    boolean pos;
	    do {
		pos = true;
		// on place aléatoirement nos robots
		xRand = r.nextInt((int) SIZE_PLATEAU);
		yRand = r.nextInt((int) SIZE_PLATEAU);
		for (Robot robot : robots)
		    if (robot.getPosition().x == xRand
			    && robot.getPosition().y == yRand)
			pos = false;
		// on ne peut pas placer directement sur les cases ojectifs
		for (Block block : blocks)
		    if (block.getColor() != "blanc"
			    && block.getPosition().x == xRand
			    && block.getPosition().y == yRand)
			pos = false;
	    } while (!pos
		    || (xRand >= 7 && xRand <= 8 && yRand >= 7 && yRand <= 8));
	    robots.add(new Robot(new Vector2(xRand, yRand), listColor[i]));
	}
    }

    private void createMiddle() {
	blocks.add(new Block(new Vector2(7, 7), 2, "game", "blanc"));
	if (alObjectif.size() > 0) {
	    float size = SIZE_PLATEAU / 2f;
	    // on ajoute un objectif aléatoire parmis les objectifs disponibles
	    objectifEnCours = alObjectif.get(r.nextInt(alObjectif.size()));
	    blocks.add(new Block(new Vector2(size - 0.5f, size - 0.5f), 1,
		    objectifEnCours.getForm(), objectifEnCours.getColor()));
	}
    }

    private void initWallBorder() {
	for (float i = 0f; i < SIZE_PLATEAU; i++) {
	    for (float j = 0f; j < SIZE_PLATEAU; j++) {
		// on initialise les bords du plateau
		if (i == 0f)
		    murs.add(new Mur(new Vector2(i, j), .1f, 1f));
		if (i == SIZE_PLATEAU - 1f)
		    murs.add(new Mur(new Vector2(i + 1f, j), .1f, 1f));
		if (j == 0f)
		    murs.add(new Mur(new Vector2(i, j), 1f, .1f));
		if (j == SIZE_PLATEAU - 1f)
		    murs.add(new Mur(new Vector2(i, j + 1f), 1f, .1f));
		// on initialise le bloc objectif
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

    public void deplacementRobot(Robot robot) {
	nbMouvement++;
	nbMouvementTotal++;
	// le robot est sur l'objectif
	if (objectifAccompli(robot)) {
	    nbMouvement = 0;
	    chrono.setStartTime(SystemClock.uptimeMillis());
	    chrono.setTimeSwap(0);
	    // on supprime l'objectif de la liste
	    alObjectif.remove(objectifEnCours);

	    if (alObjectif.size() > 0) {
		info.setMessage("Objectif suivant !");
		info.getChrono().setStartTime(SystemClock.uptimeMillis());
	    } else {
		nbMouvement = nbMouvementTotal;
		stop = true;
		info.setMessage("Félicitation ! Vous avez terminé");
		info.getChrono().setStartTime(SystemClock.uptimeMillis());
	    }
	    info.setColor(Color.GREEN);
	    // on tire un nouvel objectif
	    createMiddle();
	}
    }

    public boolean objectifAccompli(Robot robot) {
	for (Block block : blocks) {
	    // on vérifie s'il s'agit du bloc objectif
	    if (objectifEnCours.getColor().equals(block.color)
		    && objectifEnCours.getForm().equals(block.getForm())) {
		// on verifie les coordonées
		if (block.getPosition().x == robot.getPosition().x
			&& block.getPosition().y == robot.getPosition().y)
		    // on vérifie que le bloc a la même couleur que le robot
		    if (block.getColor().equals(robot.getColor()))
			return true;
		    else if (block.getColor().equals("multi"))
			return true;
	    }
	}
	return false;
    }

    private void suppressionBlock(float x, float y) {
	for (int i = 0; i < blocks.size; i++)
	    if (blocks.get(i).getPosition().x == x
		    && blocks.get(i).getPosition().y == y)
		blocks.removeIndex(i);
    }

    private String[] rotate(String[] coordonnee, int cpt) {
	float x = Float.parseFloat(coordonnee[0]);
	float y = Float.parseFloat(coordonnee[1]);
	for (int nbRotate = 0; nbRotate < cpt; nbRotate++) {
	    float temp = x;
	    x = y;
	    y = 7 - temp;
	}
	coordonnee[0] = x + "";
	coordonnee[1] = y + "";
	return coordonnee;
    }

    private String[] rotateWall(String[] wall, int cpt) {
	float x = Float.parseFloat(wall[0]);
	float y = Float.parseFloat(wall[1]);

	float width = Float.parseFloat(wall[2]);
	float height = Float.parseFloat(wall[3]);

	for (int nbRotate = 0; nbRotate < cpt; nbRotate++) {
	    float temp = x;

	    x = y;
	    y = width > height ? 7 - temp : 8 - temp;
	    temp = width;
	    width = height;
	    height = temp;
	}

	wall[0] = x + "";
	wall[1] = y + "";
	wall[2] = width + "";
	wall[3] = height + "";
	return wall;
    }

    private String[] translate(String[] coordonnee, int cpt) {
	float x = Float.parseFloat(coordonnee[0]);
	float y = Float.parseFloat(coordonnee[1]);
	if (cpt == 1)
	    y += 8;
	else if (cpt == 2) {
	    x += 8;
	    y += 8;
	} else if (cpt == 3)
	    x += 8;
	coordonnee[0] = x + "";
	coordonnee[1] = y + "";
	return coordonnee;
    }

    public Information getInfo() {
	return info;
    }

    public Array<Block> getBlocks() {
	return blocks;
    }

    public Array<Robot> getRobots() {
	return robots;
    }

    public Array<Mur> getMurs() {
	return murs;
    }

    public ArrayList<Objectif> getAlObjectif() {
	return alObjectif;
    }

    public Objectif getObjectifEnCours() {
	return objectifEnCours;
    }

    public int getNbMouvement() {
	return nbMouvement;
    }

    public int getNbMouvementTotal() {
	return nbMouvementTotal;
    }

    public Chronometre getChrono() {
	return chrono;
    }

    public Chronometre getChronoTotal() {
	return chronoTotal;
    }

    public boolean isStop() {
	return stop;
    }
}
