package com.me.ricochetRobots.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.me.ricochetRobots.model.World;
import com.me.ricochetRobots.view.WorldRenderer;

public class GameScreen implements Screen
{
	private World world;
	private WorldRenderer renderer;
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		renderer.render();
	}

	@Override
	public void resize(int width, int height)
	{
	    renderer.setSize(width, height);
	}

	@Override
	public void show()
	{
		world = new World();
		renderer = new WorldRenderer(world, false);
	}

	@Override
	public void hide()
	{
		// TODO Stub de la méthode généré automatiquement

	}

	@Override
	public void pause()
	{
		// TODO Stub de la méthode généré automatiquement

	}

	@Override
	public void resume()
	{
		// TODO Stub de la méthode généré automatiquement

	}

	@Override
	public void dispose()
	{
		// TODO Stub de la méthode généré automatiquement

	}

}
