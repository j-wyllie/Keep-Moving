package com.mygdx.game.Scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    private Float score;
    private Label scoreLabel;

    public Hud(SpriteBatch sb) {
        viewport = new FitViewport(ZombieTrain.V_WIDTH, ZombieTrain.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        score = 0f;
        scoreLabel = new Label(String.valueOf(score.intValue()), new Label.LabelStyle(new BitmapFont(), Color.BLUE));

        stage.addActor(scoreLabel);
    }

    public void update(float dt) {
        score += dt;
        scoreLabel.setText(String.valueOf(score.intValue()));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
