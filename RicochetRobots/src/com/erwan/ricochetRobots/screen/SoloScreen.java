package com.erwan.ricochetRobots.screen;

import android.os.SystemClock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.erwan.ricochetRobots.controller.InputController;
import com.erwan.ricochetRobots.controller.SoloController;
import com.erwan.ricochetRobots.model.Solo;
import com.erwan.ricochetRobots.view.SoloRenderer;

public class SoloScreen implements Screen {

    private Stage stage;

    private SpriteBatch batch;
    private Sprite fondSprite;

    private Solo solo;
    private SoloRenderer renderer;
    private SoloController controller;

    private Label title;
    private Label nbObjectif;
    private Label nbMouvement;
    private Label timer;
    private Label message;
    private Label fps;

    private BitmapFont fontWhite;

    private long timePause;

    @Override
    public void render(float delta) {
	Gdx.gl.glClearColor(0, 0, 0, 0);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	controller.update(delta);

	// On dessine l'image de fond
	batch.begin();
	fondSprite.draw(batch);
	batch.end();

	int iFps = Math.round(1 / delta);
	fps.setText("FPS : " + iFps);
	// modification du nombre d'objectif reussi
	nbObjectif.setText("OBJECTIFS REUSSIS : "
		+ (17 - solo.getAlObjectif().size()) + "/17");

	// modification du nombre de deplacements
	nbMouvement.setText("MOUVEMENTS : " + solo.getNbMouvement()
		+ " | TOTAL : " + solo.getNbMouvementTotal());

	// calcul du temps
	if (!solo.isStop()) {
	    solo.getChrono().setFinalTime(
		    SystemClock.uptimeMillis()
			    - solo.getChrono().getStartTime()
			    - solo.getChrono().getTimeSwap());
	    solo.getChronoTotal().setFinalTime(
		    SystemClock.uptimeMillis()
			    - solo.getChronoTotal().getStartTime()
			    - solo.getChronoTotal().getTimeSwap());
	}

	// modification du temps
	timer.setText("TEMPS : " + solo.getChrono().getMinutes() + ":"
		+ String.format("%02d", solo.getChrono().getSecondes())
		+ " | TOTAL : " + solo.getChronoTotal().getMinutes() + ":"
		+ String.format("%02d", solo.getChronoTotal().getSecondes()));

	solo.getInfo()
		.getChrono()
		.setFinalTime(
			SystemClock.uptimeMillis()
				- solo.getInfo().getChrono().getStartTime()
				- solo.getInfo().getChrono().getTimeSwap());

	// modification du message
	if (solo.getInfo().getChrono().getSecondes() > 3)
	    solo.getInfo().setMessage("");

	LabelStyle style = new LabelStyle(fontWhite, solo.getInfo().getColor());
	message.setStyle(style);
	message.setText(solo.getInfo().getMessage());

	stage.act(delta);
	stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
	batch = new SpriteBatch();
	stage = new Stage();

	// on créer l'image du fond
	Texture fondTexture = new Texture("data/images/fond_screen.png");
	fondSprite = new Sprite(fondTexture);
	fondSprite.setPosition(0, 0);
	fondSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	fontWhite = new BitmapFont(Gdx.files.internal("font/font_white.fnt"),
		false);
	LabelStyle style = new LabelStyle(fontWhite, Color.BLACK);

	float width = Gdx.graphics.getWidth();
	float top = Gdx.graphics.getHeight();
	float height = 0f;

	// LABEL TITRE
	title = new Label(" RICOCHET ROBOTS ", style);
	title.setFontScale(width / title.getTextBounds().width);
	title.getTextBounds().height += 30;
	height = title.getTextBounds().height;
	top -= height;
	title.setPosition(0, top);
	title.setSize(width, height);
	title.setAlignment(Align.center);

	// CREATION DU PLATEAU
	solo = new Solo();
	renderer = new SoloRenderer(solo, top);
	controller = new SoloController(solo, top);

	top -= width;
	renderer.setSize(width, width);
	renderer.setPosition(0, top);

	// LABEL COMPTEUR OBJECTIF
	nbObjectif = new Label("OBJECTIFS REUSSIS : 0/17      ", style);
	nbObjectif.setFontScale(width / nbObjectif.getTextBounds().width);
	nbObjectif.getTextBounds().height += 20;
	height = nbObjectif.getTextBounds().height;
	top -= height;
	nbObjectif.setPosition(0, top);
	nbObjectif.setSize(width, height);

	// LABEL MOUVEMENT
	nbMouvement = new Label("MOUVEMENTS : 0 | TOTAL : 0    ", style);
	nbMouvement.setFontScale(width / nbMouvement.getTextBounds().width);
	nbMouvement.getTextBounds().height += 20;
	height = nbMouvement.getTextBounds().height;
	top -= height;
	nbMouvement.setPosition(0, top);
	nbMouvement.setSize(width, height);

	// LABEL TIME
	timer = new Label("TEMPS : 00:00 | TOTAL : 00:00 ", style);
	timer.setFontScale(width / timer.getTextBounds().width);
	timer.getTextBounds().height += 20;
	height = timer.getTextBounds().height;
	top -= height;
	timer.setPosition(0, top);
	timer.setSize(width, height);

	// LABEL MESSAGE
	style = new LabelStyle(fontWhite, Color.GREEN);
	message = new Label("                              ", style);
	message.setFontScale(width / message.getTextBounds().width);
	message.getTextBounds().height += 20;
	height = message.getTextBounds().height;
	top -= height;
	message.setPosition(0, top);
	message.setSize(width, height);

	style = new LabelStyle(fontWhite, Color.BLUE);
	fps = new Label("FPS : 00", style);
	fps.setPosition(0, 0);

	stage.addActor(title);
	stage.addActor(renderer);
	stage.addActor(nbObjectif);
	stage.addActor(nbMouvement);
	stage.addActor(timer);
	stage.addActor(message);
	stage.addActor(fps);

	// on modifie les inputs Controller
	Gdx.input.setInputProcessor(new InputController() {
	    private int initY;
	    private int initX;

	    @Override
	    public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK)
		    ((Game) Gdx.app.getApplicationListener())
			    .setScreen(new MenuScreen());
		return true;
	    }

	    @Override
	    public boolean touchDown(int screenX, int screenY, int pointer,
		    int button) {
		if (!solo.isStop()) {
		    controller.touchDown(screenX, screenY);
		    initX = screenX;
		    initY = screenY;
		    return true;
		}
		return false;
	    }

	    @Override
	    public boolean touchUp(int screenX, int screenY, int pointer,
		    int button) {
		if (!solo.isStop()) {
		    float ppuX = Gdx.graphics.getWidth() / Solo.SIZE_PLATEAU;
		    float ppuY = Gdx.graphics.getHeight() / Solo.SIZE_PLATEAU;
		    int depX = Math.abs(screenX - initX);
		    int depY = Math.abs(screenY - initY);
		    if (controller.getRobotMove() != null) {
			if (depX < ppuX + .25f * ppuX
				&& depY > ppuY + .25f * ppuY
				&& screenY - initY < 0)
			    controller.topPressed();
			if (depX < ppuX + .25f * ppuX
				&& depY > ppuY + .25f * ppuY
				&& screenY - initY > 0)
			    controller.bottomPressed();
			if (depX > ppuX + .25f * ppuX
				&& depY < ppuY + .25f * ppuY
				&& screenX - initX < 0)
			    controller.leftPressed();
			if (depX > ppuX + .25f * ppuX
				&& depY < ppuY + .25f * ppuY
				&& screenX - initX > 0)
			    controller.rightPressed();
		    }
		    return true;
		}
		return false;
	    }
	});

	solo.getChrono().setStartTime(SystemClock.uptimeMillis());
	solo.getChronoTotal().setStartTime(SystemClock.uptimeMillis());
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
	timePause = SystemClock.uptimeMillis();
    }

    @Override
    public void resume() {
	long timeInPause = SystemClock.uptimeMillis() - timePause;
	solo.getChrono().setTimeSwap(
		solo.getChrono().getTimeSwap() + timeInPause);
	solo.getChronoTotal().setTimeSwap(
		solo.getChronoTotal().getTimeSwap() + timeInPause);
    }

    @Override
    public void dispose() {
	batch.dispose();
	fondSprite.getTexture().dispose();
	stage.dispose();
    }
}