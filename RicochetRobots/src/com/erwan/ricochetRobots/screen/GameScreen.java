package com.erwan.ricochetRobots.screen;

import android.os.SystemClock;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.erwan.ricochetRobots.RicochetRobots;
import com.erwan.ricochetRobots.controller.InputController;
import com.erwan.ricochetRobots.controller.DeplacementController;
import com.erwan.ricochetRobots.model.Solo;
import com.erwan.ricochetRobots.tween.ActorAccessor;
import com.erwan.ricochetRobots.view.WorldRenderer;

public class GameScreen implements Screen {

    private WorldRenderer renderer;
    private DeplacementController controller;

    private int initX;
    private int initY;

    private Solo solo;
    private int width, height;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private BitmapFont fontWhite;
    private Label heading;
    private Label timer;
    private Label mouvement;
    private Label message;
    private float tailleTop;
    private TweenManager tweenManager;

    @Override
    public void render(float delta) {
	Gdx.gl.glClearColor(0, 0, 0, 0);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	renderer.setWidth(width, tailleTop);
	tweenManager.update(delta);

	if (!solo.getStop()) {
	    solo.getChrono().setTimeInMillies(
		    SystemClock.uptimeMillis()
			    - solo.getChrono().getStartTime());
	    solo.getChrono().setFinalTime(
		    solo.getChrono().getTimeSwap()
			    + solo.getChrono().getTimeInMillies());
	}

	int seconds = (int) (solo.getChrono().getFinalTime() / 1000);
	int minutes = seconds / 60;
	seconds = seconds % 60;
	timer.setText("Temps " + ((solo.getStop()) ? "total : " : " : ")
		+ minutes + ":" + String.format("%02d", seconds));
	message.setText(solo.getMessage());
	if (solo.getNbMouvement() < 2)
	    mouvement.setText("Mouvement : " + solo.getNbMouvement());
	else
	    mouvement.setText("Mouvements "
		    + ((solo.getStop()) ? "totaux : " : " : ")
		    + solo.getNbMouvement());
	if (seconds == 10)
	    solo.setMessage("");
	stage.act(delta);
	stage.draw();
	//Table.drawDebug(stage);
    }

    @Override
    public void resize(int width, int height) {
	this.width = width;
	this.height = height;
    }

    @Override
    public void show() {
	stage = new Stage();

	solo = new Solo();
	renderer = new WorldRenderer(solo);
	controller = new DeplacementController(solo);

	atlas = new TextureAtlas("ui/button.pack");
	skin = new Skin(atlas);

	fontWhite = new BitmapFont(Gdx.files.internal("font/font_white.fnt"),
		false);
	table = new Table(skin);
	table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	LabelStyle headingStyle = new LabelStyle(fontWhite, Color.WHITE);

	heading = new Label("Ricochet Robot", headingStyle);
	heading.setFontScale(2);
	timer = new Label("Temps : 0:00", headingStyle);
	timer.setFontScale(1.3f);
	mouvement = new Label("Mouvement : " + solo.getNbMouvement(),
		headingStyle);
	mouvement.setFontScale(1.3f);
	headingStyle = new LabelStyle(fontWhite, Color.RED);
	message = new Label(" " + solo.getMessage(), headingStyle);
	mouvement.setFontScale(1.2f);

	int tailleRestante = Gdx.graphics.getHeight() - Gdx.graphics.getWidth();
	tailleTop = tailleRestante / 4f;

	int widthScreen = Gdx.graphics.getWidth();
	// Creation du tableau
	table.add(heading).height(tailleRestante / 4f);
	table.row();
	table.add(renderer).height(widthScreen).width(widthScreen).space(0);
	table.row();
	table.add(mouvement).height(tailleRestante / 4f);
	table.row();
	table.add(timer).height(tailleRestante / 4f);
	table.row();
	table.add(message).height(tailleRestante / 4f);
	table.debug();
	stage.addActor(table);

	// animation
	tweenManager = new TweenManager();
	Tween.registerAccessor(Actor.class, new ActorAccessor());

	// heading color animation
	Timeline.createSequence()
		.beginSequence()
		.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 0))
		.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 1))
		.end().repeat(Tween.INFINITY, 0).start(tweenManager);

	// table animation
	Tween.from(table, ActorAccessor.ALPHA, .5f).target(0)
		.start(tweenManager);
	Tween.from(table, ActorAccessor.Y, .5f)
		.target(Gdx.graphics.getHeight() / 8).start(tweenManager);

	solo.getChrono().setStartTime(SystemClock.uptimeMillis());

	Gdx.input.setInputProcessor(new InputController() {
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
		    Gdx.app.log(RicochetRobots.LOG, tailleTop + "");
		    controller.touchDown(screenX, screenY, width, tailleTop);
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
		    float ppuX = (float) width / Solo.SIZE_PLATEAU;
		    float ppuY = (float) height / Solo.SIZE_PLATEAU;
		    if (controller.getRobotMove() != null) {
			if (Math.abs(screenX - initX) < ppuX + .25f * ppuX
				&& Math.abs(screenY - initY) > ppuY + .25f
					* ppuY && screenY - initY < 0)
			    controller.topPressed();
			if (Math.abs(screenX - initX) < ppuX + .25f * ppuX
				&& Math.abs(screenY - initY) > ppuY + .25f
					* ppuY && screenY - initY > 0)
			    controller.bottomPressed();
			if (Math.abs(screenX - initX) > ppuX + .25f * ppuX
				&& Math.abs(screenY - initY) < ppuY + .25f
					* ppuY && screenX - initX < 0)
			    controller.leftPressed();
			if (Math.abs(screenX - initX) > ppuX + .25f * ppuX
				&& Math.abs(screenY - initY) < ppuY + .25f
					* ppuY && screenX - initX > 0)
			    controller.rightPressed();
		    }
		    return true;
		}
		return false;
	    }
	});
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}