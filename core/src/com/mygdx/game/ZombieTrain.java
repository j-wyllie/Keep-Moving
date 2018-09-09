package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.screen.PlayScreen;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class ZombieTrain extends Game {
    // Virtual Screen size and Box2D Scale(Pixels Per Meter)
	public static final int V_WIDTH = 225;
	public static final int V_HEIGHT = 400;

	public static final short PLAYER_COLLISION_BIT = 2;
	public static final short ZOMBIE_COLLISION_BIT = 4;
	//public static final int PPM = 100;

	public SpriteBatch batch;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
        setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
