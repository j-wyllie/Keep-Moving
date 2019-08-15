package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screen.MenuScreen;
import com.mygdx.game.tool.AdHandler;

public class ZombieTrain extends Game {
    // Virtual Screen size and Box2D Scale(Pixels Per Meter)
    public static final int V_INIT_WIDTH = 270;//225;
    public static final int V_INIT_HEIGHT = 480;

    public static final short PLAYER_COLLISION_BIT = 2;
    public static final short ZOMBIE_COLLISION_BIT = 4;
    public static final short TRAP_COLLISION_BIT = 8;
    //public static final int PPM = 100;

    public SpriteBatch batch;
    public static AdHandler adHandler;

    public ZombieTrain() {
    }

    public ZombieTrain(AdHandler adHandler) {
        this.adHandler = adHandler;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MenuScreen(this));
//		adHandler.showAds(true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.8862745098f, 0.8431372549f, 0.8862745098f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
