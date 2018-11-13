package com.mygdx.game.screen;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Scene.Hud;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.tool.InputHandler;

import static com.mygdx.game.tool.InputHandler.isTouched;

public class MenuScreen implements Screen {
    private ZombieTrain game;
    private Stage stage;
    private Viewport viewport;

    private final Vector2 SCORE_POS = new Vector2(ZombieTrain.V_WIDTH / 2, ZombieTrain.V_HEIGHT - 60);
    private Label highScoreLabel;

    public MenuScreen(ZombieTrain game) {
        this.game = game;

        viewport = new FitViewport(ZombieTrain.V_WIDTH, ZombieTrain.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        highScoreLabel = new Label(String.valueOf(Hud.getHighScore()), new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        highScoreLabel.setPosition(SCORE_POS.x, SCORE_POS.y);
        highScoreLabel.setFontScale(2);

        stage.addActor(highScoreLabel);
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

        stage.draw();
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
