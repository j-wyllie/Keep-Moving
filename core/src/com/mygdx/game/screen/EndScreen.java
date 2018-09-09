package com.mygdx.game.screen;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.tool.InputHandler;

import static com.mygdx.game.tool.InputHandler.isTouched;

public class EndScreen implements Screen {
    private ZombieTrain game;

    public EndScreen(ZombieTrain game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    private void update(float dt) {
       if (isTouched()) {
            game.setScreen(new PlayScreen(game));
       }
    }

    @Override
    public void render(float dt) {
        update(dt);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
