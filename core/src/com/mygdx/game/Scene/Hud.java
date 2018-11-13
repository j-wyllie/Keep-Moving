package com.mygdx.game.Scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ZombieTrain;

public class Hud implements Disposable {
    private final Vector2 SCORE_POS = new Vector2(ZombieTrain.V_WIDTH / 2, ZombieTrain.V_HEIGHT - 40);
    private static Integer highScore = 0;

    public Stage stage;
    private Viewport viewport;

    private Float score = 0f;
    private Label scoreLabel;

    public Hud(SpriteBatch sb) {
        viewport = new FitViewport(ZombieTrain.V_WIDTH, ZombieTrain.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        scoreLabel = new Label(String.valueOf(score.intValue()), new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        scoreLabel.setPosition(SCORE_POS.x, SCORE_POS.y);
        scoreLabel.setFontScale(2);

        stage.addActor(scoreLabel);
    }

    public void update(float dt) {
        score += dt;
        scoreLabel.setText(String.valueOf(score.intValue()));

        if (score > highScore) {
            highScore = score.intValue();
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public static int getHighScore() {
        return highScore.intValue();
    }
}
