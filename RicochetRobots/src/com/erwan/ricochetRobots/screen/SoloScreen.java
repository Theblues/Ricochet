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
import com.erwan.ricochetRobots.controller.InputController;
import com.erwan.ricochetRobots.controller.SoloController;
import com.erwan.ricochetRobots.model.Solo;
import com.erwan.ricochetRobots.view.SoloRenderer;

public class SoloScreen implements Screen {

    private Stage stage;

    private SpriteBatch batch;
    private Sprite fondSprite;
    private Sprite titleSprite;

    private Solo solo;
    private SoloRenderer renderer;
    private SoloController controller;

    private Label nbObjectif;
    private Label nbMouvement;
    private Label timer;
    private Label message;

    private BitmapFont fontWhite;

    private long timePause;

    @Override
    public void render(float delta) {
	Gdx.gl.glClearColor(0, 0, 0, 0);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	// we use the SpriteBatch to draw 2D textures (it is defined in our base
	// class: AbstractScreen)
	batch.begin();

	// we tell the batch to draw the region starting at (0,0) of the
	// lower-left corner with the size of the screen
	fondSprite.draw(batch);
	titleSprite.draw(batch);
	renderer.draw(batch);

	// the end method does the drawing
	batch.end();

	nbObjectif.setText("OBJECTIFS REUSSIS : "
		+ (17 - solo.getAlObjectif().size()) + "/17");

	nbMouvement.setText("MOUVEMENTS : " + solo.getNbMouvement()
		+ " | TOTAL : " + solo.getNbMouvementTotal());

	if (!solo.getStop()) {
	    solo.getChrono().setTimeInMillies(
		    SystemClock.uptimeMillis()
			    - solo.getChrono().getStartTime());
	    solo.getChrono().setFinalTime(
		    solo.getChrono().getTimeInMillies()
			    - solo.getChrono().getTimeSwap());
	    solo.getChronoTotal().setTimeInMillies(
		    SystemClock.uptimeMillis()
			    - solo.getChronoTotal().getStartTime());
	    solo.getChronoTotal().setFinalTime(
		    solo.getChronoTotal().getTimeInMillies()
			    - solo.getChronoTotal().getTimeSwap());
	}
	int secondsChrono = (int) (solo.getChrono().getFinalTime() / 1000);
	int minutesChrono = secondsChrono / 60;
	secondsChrono = secondsChrono % 60;
	int secondsChronoTotal = (int) (solo.getChronoTotal().getFinalTime() / 1000);
	int minutesChronoTotal = secondsChronoTotal / 60;
	secondsChronoTotal = secondsChronoTotal % 60;

	timer.setText("TEMPS : " + minutesChrono + ":"
		+ String.format("%02d", secondsChrono) + " | TOTAL : "
		+ minutesChronoTotal + ":"
		+ String.format("%02d", secondsChronoTotal));

	if (secondsChrono == 10)
	    solo.setMessage("");
	message.setText(solo.getMessage());

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

	Texture fondTexture = new Texture("data/images/fond_screen.png");
	fondSprite = new Sprite(fondTexture);
	fondSprite.setPosition(0, 0);
	fondSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	// Creation de l'image
	Texture titleTexture = new Texture("data/images/title.png");
	titleSprite = new Sprite(titleTexture);

	float width = Gdx.graphics.getWidth();
	float height = titleSprite.getHeight() * width / titleSprite.getWidth();
	float top = Gdx.graphics.getHeight() - height;

	titleSprite.setPosition(0, top);
	titleSprite.setSize(width, height);

	solo = new Solo();
	renderer = new SoloRenderer(solo, top);
	controller = new SoloController(solo, top);

	top -= Gdx.graphics.getWidth();
	fontWhite = new BitmapFont(Gdx.files.internal("font/font_white.fnt"),
		false);

	LabelStyle style = new LabelStyle(fontWhite, Color.BLACK);

	// LABEL COMPTEUR OBJECTIF
	nbObjectif = new Label("OBJECTIFS REUSSIS : 0/17   ", style);
	height = nbObjectif.getTextBounds().height + 30;
	top -= height;
	nbObjectif.setFontScale(width / nbObjectif.getTextBounds().width);
	nbObjectif.setPosition(0, top);
	nbObjectif.setSize(width, height);

	// LABEL MOUVEMENT
	nbMouvement = new Label("MOUVEMENTS : 0 | TOTAL : 0 ", style);
	height = nbMouvement.getTextBounds().height + 30;
	top -= height;
	nbMouvement.setFontScale(width / nbMouvement.getTextBounds().width);
	nbMouvement.setPosition(0, top);
	nbMouvement.setSize(width, height);

	// LABEL TIME
	timer = new Label("TEMPS : 0:00 | TOTAL : 0:00", style);
	height = timer.getTextBounds().height + 30;
	top -= height;
	timer.setFontScale(width / timer.getTextBounds().width);
	timer.setPosition(0, top);
	timer.setSize(width, height);

	// LABEL MESSAGE
	style = new LabelStyle(fontWhite, Color.GREEN);
	message = new Label("                           ", style);
	height = message.getTextBounds().height + 30;
	top -= height;
	message.setFontScale(width / message.getTextBounds().width);
	message.setPosition(0, top);
	message.setSize(width, height);

	stage.addActor(nbObjectif);
	stage.addActor(nbMouvement);
	stage.addActor(timer);
	stage.addActor(message);

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
		if (!solo.getStop()) {
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
		if (!solo.getStop()) {
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
	titleSprite.getTexture().dispose();
	fondSprite.getTexture().dispose();
	stage.dispose();
    }
}