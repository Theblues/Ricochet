package com.erwan.ricochetRobots.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.erwan.ricochetRobots.controller.InputController;

public class EssaiScreen implements Screen {

    private World world;
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private OrthographicCamera camera;

    @Override
    public void render(float delta) {
	Gdx.gl.glClearColor(0, 0, 0, 0);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	debugRenderer.render(world, camera.combined);

	world.step(1 / 45f, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
	camera.viewportWidth = width;
	camera.viewportHeight = height;
	camera.update();
    }

    @Override
    public void show() {

	world = new World(new Vector2(0, -9.81f), true);
	camera = new OrthographicCamera();

	BodyDef robotDef = new BodyDef();
	robotDef.type = BodyType.DynamicBody;
	robotDef.position.set(100, 300);

	BodyDef wall = new BodyDef();
	wall.type = BodyType.StaticBody;
	wall.position.set(50, -200);

	CircleShape circle = new CircleShape();
	// Create a fixture from our polygon shape and add it to our ground body
	circle.setRadius(100f);

	PolygonShape rect = new PolygonShape();
	rect.setAsBox(150f, 20f);

	FixtureDef fixtureDef = new FixtureDef();
	fixtureDef.shape = circle;
	fixtureDef.density = 0.5f;
	fixtureDef.friction = 0.4f;
	fixtureDef.restitution = 0.6f; // Make it bounce a little bit

	Body body = world.createBody(robotDef);
	// body.setLinearVelocity(0.0f, -20.0f);
	// Create our fixture and attach it to the body
	body.createFixture(fixtureDef);

	Body groundBody = world.createBody(wall);
	groundBody.createFixture(rect, 0.0f);
	// Clean up after ourselves
	rect.dispose();

	circle.dispose();

	Gdx.input.setInputProcessor(new InputController() {
	    @Override
	    public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK)
		    ((Game) Gdx.app.getApplicationListener())
			    .setScreen(new MenuScreen());
		return true;
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