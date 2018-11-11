package com.mygdx.game.Scene;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ZombieTrain;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;

    private Integer score;
    private Label scoreLabel;

    public Hud(SpriteBatch sb) {
        score = 0;

        viewport = new FitViewport(ZombieTrain.V_WIDTH, ZombieTrain.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        stage.addActor(scoreLabel);
    }

    public void update(float dt) {
        score += (int) dt / 1000;
        scoreLabel.setText(String.valueOf(score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
