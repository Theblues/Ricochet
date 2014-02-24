package com.erwan.ricochetRobots.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.erwan.ricochetRobots.tween.SpriteAccessor;

public class SplashScreen implements Screen {

    private SpriteBatch batch;
    private TweenManager tweenManager;
    private Sprite splashSprite;

    @Override
    public void show() {
	batch = new SpriteBatch();
	tweenManager = new TweenManager();
	Tween.registerAccessor(Sprite.class, new SpriteAccessor());

	Texture splashTexture = new Texture("data/ricochet_robots.jpg");
	splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	TextureRegion splashTextureRegion = new TextureRegion(splashTexture, 0,
		0, 512, 364);
	splashSprite = new Sprite(splashTextureRegion);
	splashSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	Tween.set(splashSprite, SpriteAccessor.ALPHA).target(0)
		.start(tweenManager);
	// FADE IN WITH REPEAT
	Tween.to(splashSprite, SpriteAccessor.ALPHA, 1.5f).target(1)
		.repeatYoyo(1, 1.5f).setCallback(new TweenCallback() {

		    @Override
		    public void onEvent(int type, BaseTween<?> source) {
			((Game) Gdx.app.getApplicationListener())
				.setScreen(new MenuScreen());
		    }
		}).start(tweenManager);
    }

    @Override
    public void render(float delta) {
	Gdx.gl.glClearColor(0, 0, 0, 0);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	tweenManager.update(delta);
	// we use the SpriteBatch to draw 2D textures (it is defined in our base
	// class: AbstractScreen)
	batch.begin();

	// we tell the batch to draw the region starting at (0,0) of the
	// lower-left corner with the size of the screen
	splashSprite.draw(batch);

	// the end method does the drawing
	batch.end();
    }

    @Override
    public void dispose() {
	batch.dispose();
	splashSprite.getTexture().dispose();
    }

    @Override
    public void resize(int width, int height) {
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
}
