package com.erwan.ricochetRobots.model;

import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.SystemClock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Solo {
    public static final float SIZE_PLATEAU = 16;
    private static final int NB_QUARTIER = 4;

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
    private Information info;
    private boolean stop;

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
	// initialisation des plateaux possibles
	Array<Integer> liste = new Array<Integer>();
	for (int i = 0; i < NB_QUARTIER; i++)
	    liste.add(i);

	// ouverture du fichier XML
	try {
	    DocumentBuilderFactory factory = DocumentBuilderFactory
		    .newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.parse(Gdx.files.internal("data/source.xml")
		    .read());
	    doc.getDocumentElement().normalize();

	    // on creer notre liste des noeuds "QUARTIER"
	    NodeList nlQuartiers = doc.getElementsByTagName("quartier");

	    // on fait notre boucle pour avoir notre quatier
	    for (int cpt = 0; cpt < NB_QUARTIER; cpt++) {
		// on choisi aleatoire notre plateau
		int rand = r.nextInt(liste.size);
		int numPlateau = liste.get(rand);
		liste.removeIndex(rand);
		int face = r.nextInt(2);

		// on selectionne notre element
		Element eQuartier = null;
		for (int temp = 0; temp < nlQuartiers.getLength(); temp++) {
		    Node nQuartier = nlQuartiers.item(temp);

		    if (nQuartier.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nQuartier;
			if (Integer.parseInt(eElement.getAttribute("id")) == numPlateau
				&& Integer.parseInt(eElement
					.getAttribute("face")) == face)
			    eQuartier = eElement;
		    }
		}
		NodeList cases = eQuartier.getElementsByTagName("case");
		for (int temp = 0; temp < cases.getLength(); temp++) {
		    Element eCase = (Element) cases.item(temp);
		    float[] coordonnee = {
			    Float.parseFloat(eCase.getAttribute("x")),
			    Float.parseFloat(eCase.getAttribute("y")) };
		    String forme = eCase.getElementsByTagName("forme").item(0)
			    .getTextContent();
		    String color = eCase.getElementsByTagName("color").item(0)
			    .getTextContent();

		    // on effectue nos rotations
		    coordonnee = rotate(coordonnee, cpt);
		    coordonnee = translate(coordonnee, cpt);

		    float x = coordonnee[0];
		    float y = coordonnee[1];
		    suppressionBlock(x, y);

		    // on ajoute notre bloc objectif
		    blocks.add(new Block(new Vector2(x, y), 1, forme, color));
		    // on l'ajoute a la liste des objectif
		    alObjectif.add(new Objectif(forme, color));
		}
		// ajout des murs de plateau selectionné
		initWall(cpt, eQuartier);
	    }
	} catch (Exception e) {
	    System.out.println(e.toString());
	}
    }

    private void initWall(int cpt, Element eQuartier) {
	NodeList nlMurs = eQuartier.getElementsByTagName("mur");
	for (int temp = 0; temp < nlMurs.getLength(); temp++) {
	    Element eMur = (Element) nlMurs.item(temp);
	    float[] attributs = new float[4];
	    attributs[0] = Float.parseFloat(eMur.getAttribute("x"));
	    attributs[1] = Float.parseFloat(eMur.getAttribute("y"));
	    attributs[2] = Float.parseFloat(eMur.getAttribute("l"));
	    attributs[3] = Float.parseFloat(eMur.getAttribute("h"));
	    // on effectue les rotations et une rotation de nos murs
	    attributs = rotateWall(attributs, cpt);
	    attributs = translate(attributs, cpt);

	    // on crée nos murs
	    murs.add(new Mur(new Vector2(attributs[0], attributs[1]),
		    attributs[2], attributs[3]));
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

    private float[] rotate(float[] coordonnee, int cpt) {
	float x = coordonnee[0];
	float y = coordonnee[1];
	for (int nbRotate = 0; nbRotate < cpt; nbRotate++) {
	    float temp = x;
	    x = y;
	    y = 7 - temp;
	}
	coordonnee[0] = x;
	coordonnee[1] = y;
	return coordonnee;
    }

    private float[] rotateWall(float[] attributs, int cpt) {
	float x = attributs[0];
	float y = attributs[1];

	float width = attributs[2];
	float height = attributs[3];

	for (int nbRotate = 0; nbRotate < cpt; nbRotate++) {
	    float temp = x;
	    x = y;
	    y = width > height ? 7 - temp : 8 - temp;
	    temp = width;
	    width = height;
	    height = temp;
	}
	attributs[0] = x;
	attributs[1] = y;
	attributs[2] = width;
	attributs[3] = height;
	return attributs;
    }

    private float[] translate(float[] coordonnee, int cpt) {
	float x = coordonnee[0];
	float y = coordonnee[1];
	if (cpt == 1)
	    y += 8;
	else if (cpt == 2) {
	    x += 8;
	    y += 8;
	} else if (cpt == 3)
	    x += 8;
	coordonnee[0] = x;
	coordonnee[1] = y;
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