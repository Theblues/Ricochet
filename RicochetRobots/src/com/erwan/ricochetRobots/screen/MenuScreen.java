package com.erwan.ricochetRobots.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont fontWhite, fontBlack;
    private Table table;
    private Label heading;
    private TextButton btSolo;
    private TextButton btMulti;
    private TextButton btQuitter;
    
    @Override
    public void render(float delta) {
	Gdx.gl.glClearColor(0,0,0,0);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
	//Table.drawDebug(stage);
	
	stage.act(delta);
	stage.draw();
    }
    
    public void resize(int width, int height) {
    }
    
    public void show()
    {
	stage = new Stage();
	
	Gdx.input.setInputProcessor(stage);
	
	atlas = new TextureAtlas("ui/button.pack");
	skin = new Skin(atlas);
	
	fontBlack = new BitmapFont(Gdx.files.internal("font/font_black.fnt"), false);
	fontWhite = new BitmapFont(Gdx.files.internal("font/font_white.fnt"), false);
	
	table = new Table(skin);
	table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	
	TextButtonStyle txtBtStyle = new TextButtonStyle();
	txtBtStyle.up = skin.getDrawable("button.up");
	txtBtStyle.down = skin.getDrawable("button.down");
	txtBtStyle.pressedOffsetX = 1;
	txtBtStyle.pressedOffsetY = -1;
	txtBtStyle.font = fontBlack;
	
	
	LabelStyle headingStyle = new LabelStyle(fontWhite, Color.WHITE);
	heading = new Label("Robot Ricochet", headingStyle);
	heading.setFontScale(2);
	
	btSolo = new TextButton("Solo", txtBtStyle);
	btSolo.pad(10f);
	btSolo.addListener(new ClickListener() {
	    public void clicked(InputEvent event,
		           float x,
		           float y)
	    {
		((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
	    }
	});
	btMulti = new TextButton("Multijoueur", txtBtStyle);
	btMulti.pad(10f);
	btQuitter = new TextButton("Quitter", txtBtStyle);
	btQuitter.pad(10f);
	btQuitter.addListener(new ClickListener() {
	    public void clicked(InputEvent event,
		           float x,
		           float y)
	    {
		Gdx.app.exit();
	    }
	});
	
	table.add(heading).uniform().spaceTop(50);
	table.row();
	// units
	table.add(btSolo).uniform().fill().spaceTop(50).height(100f);
	// move to the next row
	table.row();
	// add the options button in a cell similiar to the start-game button's
	// cell
	table.add(btMulti).uniform().fill().spaceTop(50).height(100f);
	table.row();
	table.add(btQuitter).uniform().fill().spaceTop(50).height(100f);
	
	table.debug();
	stage.addActor(table);
	
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
