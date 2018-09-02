package com.mygdx.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.input.InputHandler;
import com.mygdx.game.sprite.Player;

public class PlayScreen implements Screen {
    public ZombieTrain game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private InputHandler inputHandler;

    private Player mainPlayer;

    public PlayScreen(ZombieTrain game) {
        this.game = game;
        //create cam used to follow the player through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(ZombieTrain.V_WIDTH, ZombieTrain.V_HEIGHT, gamecam);

        //initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        this.mainPlayer = new Player(this);

        inputHandler = new InputHandler(gamecam, mainPlayer.getStartPos());
    }

    @Override
    public void show() {

    }

    private void handleInput(float dt) {

    }

    private void update(float dt) {
        //update our gamecam with correct coordinates after changes
        handleInput(dt);
        gamecam.update();

        mainPlayer.setTargetPos(inputHandler.getX(), inputHandler.getY());
        mainPlayer.update(dt);
    }

    @Override
    public void render(float dt) {
        update(dt);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
            mainPlayer.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
