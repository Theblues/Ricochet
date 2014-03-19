package com.erwan.ricochetRobots.screen;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.erwan.ricochetRobots.RicochetRobots;
import com.erwan.ricochetRobots.bluetooth.BluetoothConnexion;
import com.erwan.ricochetRobots.tween.ActorAccessor;

public class MenuScreen implements Screen {

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont fontBlack;
    private Table table;
    private TextButton btSolo;
    private TextButton btAmi;
    private TextButton btMulti;
    private TextButton btEssai;
    private TextButton btQuitter;

    private TweenManager tweenManager;

    @Override
    public void render(float delta) {
	Gdx.gl.glClearColor(0, 0, 0, 0);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	// Table.drawDebug(stage);

	tweenManager.update(delta);

	stage.act(delta);
	stage.draw();
    }

    @Override
    public void resize(int width, int height) {
	stage.setViewport(width, height, true);
	table.setSize(width, height);
	table.invalidateHierarchy();
    }

    @Override
    public void show() {
	stage = new Stage();

	Gdx.input.setInputProcessor(stage);

	atlas = new TextureAtlas("ui/button.pack");
	skin = new Skin(atlas);

	fontBlack = new BitmapFont(Gdx.files.internal("font/font_black.fnt"),
		false);

	table = new Table(skin);
	table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	float width = Gdx.graphics.getWidth() * .65f;
	float height = 100f;

	TextButtonStyle txtBtStyle = new TextButtonStyle();
	txtBtStyle.up = skin.getDrawable("button.up");
	txtBtStyle.down = skin.getDrawable("button.down");
	txtBtStyle.pressedOffsetX = 1;
	txtBtStyle.pressedOffsetY = -1;
	txtBtStyle.font = fontBlack;
	// on modifie la taille du texte en fonction de la largeur de l'ecran
	txtBtStyle.font.setScale(width / (txtBtStyle.font.getXHeight() * 30f));

	btSolo = new TextButton("ENTRAINEMENT", txtBtStyle);
	btSolo.addListener(new ClickListener() {
	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		((Game) Gdx.app.getApplicationListener())
			.setScreen(new SoloScreen());
	    }
	});
	btAmi = new TextButton("JOUER CONTRE UN AMI", txtBtStyle);
	btAmi.addListener(new ClickListener() {
	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		new BluetoothConnexion();
		BluetoothConnexion.initialize();
	    }
	});
	btMulti = new TextButton("JOUER EN LIGNE", txtBtStyle);
	btMulti.addListener(new ClickListener() {
	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		RicochetRobots.getRicochetInterface().popup_indisponible();
	    }
	});
	btEssai = new TextButton("OPTIONS", txtBtStyle);
	btEssai.addListener(new ClickListener() {
	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		RicochetRobots.getRicochetInterface().popup_indisponible();
	    }
	});
	btQuitter = new TextButton("QUITTER", txtBtStyle);
	btQuitter.addListener(new ClickListener() {
	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		Gdx.app.exit();
	    }
	});

	table.add(btSolo).height(height).width(width);
	table.row();
	table.add(btAmi).spaceTop(30).height(height).width(width);
	table.row();
	table.add(btMulti).spaceTop(30).height(height).width(width);
	table.row();
	table.add(btEssai).spaceTop(30).height(height).width(width);
	table.row();
	table.add(btQuitter).spaceTop(30).height(height).width(width);

	table.debug();
	stage.addActor(table);

	// creation animations
	tweenManager = new TweenManager();
	Tween.registerAccessor(Actor.class, new ActorAccessor());

	// heading and button fadeIn
	Timeline.createSequence().beginSequence()
		.push(Tween.set(btSolo, ActorAccessor.ALPHA).target(0))
		.push(Tween.set(btMulti, ActorAccessor.ALPHA).target(0))
		.push(Tween.set(btEssai, ActorAccessor.ALPHA).target(0))
		.push(Tween.set(btQuitter, ActorAccessor.ALPHA).target(0))
		.push(Tween.to(btSolo, ActorAccessor.ALPHA, .5f).target(1))
		.push(Tween.to(btMulti, ActorAccessor.ALPHA, .5f).target(1))
		.push(Tween.to(btEssai, ActorAccessor.ALPHA, .5f).target(1))
		.push(Tween.to(btQuitter, ActorAccessor.ALPHA, .5f).target(1))
		.end().start(tweenManager);

	// table fade in
	Tween.from(table, ActorAccessor.ALPHA, .5f).target(0)
		.start(tweenManager);
	Tween.from(table, ActorAccessor.Y, .5f)
		.target(Gdx.graphics.getHeight() / 8).start(tweenManager);
    }

    @Override
    public void hide() {
	dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
	skin.dispose();
	stage.dispose();
    }
}
