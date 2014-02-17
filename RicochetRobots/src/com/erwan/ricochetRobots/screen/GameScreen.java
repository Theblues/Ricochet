package com.erwan.ricochetRobots.screen;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.erwan.ricochetRobots.controller.WorldController;
import com.erwan.ricochetRobots.model.World;
import com.erwan.ricochetRobots.tween.ActorAccessor;
import com.erwan.ricochetRobots.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {

    private World world;
    private WorldRenderer renderer;
    private WorldController controller;

    private int initX;
    private int initY;

    private int width, height;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private BitmapFont fontWhite;
    private BitmapFont fontBlack;
    private Label heading;
    private Label chrono;
    private float tailleBottom;
    private TweenManager tweenManager;

    @Override
    public void render(float delta) {
	Gdx.gl.glClearColor(0, 0, 0, 0);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	renderer.setWidth(width, tailleBottom);
	tweenManager.update(delta);

	stage.act(delta);
	stage.draw();
	// Table.drawDebug(stage);
    }

    @Override
    public void resize(int width, int height) {
	this.width = width;
	this.height = height;
    }

    @Override
    public void show() {
	stage = new Stage();

	world = new World();
	renderer = new WorldRenderer(world);
	controller = new WorldController(world);
	Gdx.input.setInputProcessor(this);

	atlas = new TextureAtlas("ui/button.pack");
	skin = new Skin(atlas);

	fontBlack = new BitmapFont(Gdx.files.internal("font/font_black.fnt"),
		false);
	fontWhite = new BitmapFont(Gdx.files.internal("font/font_white.fnt"),
		false);
	table = new Table(skin);
	table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	int tailleRestante = Gdx.graphics.getHeight() - Gdx.graphics.getWidth();
	tailleBottom = tailleRestante * 2 / 3f;

	TextButtonStyle txtBtStyle = new TextButtonStyle();
	txtBtStyle.up = skin.getDrawable("button.up");
	txtBtStyle.down = skin.getDrawable("button.down");
	txtBtStyle.pressedOffsetX = 1;
	txtBtStyle.pressedOffsetY = -1;
	txtBtStyle.font = fontBlack;

	LabelStyle headingStyle = new LabelStyle(fontWhite, Color.WHITE);
	heading = new Label("Robot Ricochet", headingStyle);
	heading.setFontScale(2);
	chrono = new Label("00:00", headingStyle);
	chrono.setFontScale(1.5f);

	table.add(heading).expandX().height(tailleRestante - tailleBottom);
	table.row();
	table.add(renderer).height(Gdx.graphics.getWidth());
	table.row();
	// units
	table.add(chrono).expandX().height(tailleBottom);
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

    @Override
    public boolean keyDown(int keycode) {
	return true;
    }

    @Override
    public boolean keyUp(int keycode) {
	return true;
    }

    @Override
    public boolean keyTyped(char character) {
	return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	controller.touchDown(screenX, screenY, width, tailleBottom);
	initX = screenX;
	initY = screenY;
	return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
	float ppuX = (float) width / World.SIZE_PLATEAU;
	float ppuY = (float) height / World.SIZE_PLATEAU;
	if (controller.getRobotMove() != null) {
	    if (Math.abs(screenX - initX) < ppuX + .25f * ppuX
		    && Math.abs(screenY - initY) > ppuY + .25f * ppuY
		    && screenY - initY < 0)
		controller.topPressed();
	    if (Math.abs(screenX - initX) < ppuX + .25f * ppuX
		    && Math.abs(screenY - initY) > ppuY + .25f * ppuY
		    && screenY - initY > 0)
		controller.bottomPressed();
	    if (Math.abs(screenX - initX) > ppuX + .25f * ppuX
		    && Math.abs(screenY - initY) < ppuY + .25f * ppuY
		    && screenX - initX < 0)
		controller.leftPressed();
	    if (Math.abs(screenX - initX) > ppuX + .25f * ppuX
		    && Math.abs(screenY - initY) < ppuY + .25f * ppuY
		    && screenX - initX > 0)
		controller.rightPressed();
	}
	return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
	return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
	return false;
    }

    @Override
    public boolean scrolled(int amount) {
	return false;
    }
}