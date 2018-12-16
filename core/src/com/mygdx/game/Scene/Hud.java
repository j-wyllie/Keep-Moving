package com.mygdx.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ZombieTrain;

public class Hud implements Disposable {
    private final Vector2 SCORE_POS = new Vector2(ZombieTrain.V_WIDTH / 2 - 12, ZombieTrain.V_HEIGHT - 125);
    private static Integer localHighScore = 0;

    public Stage stage;
    private Viewport viewport;

    private Float score = 0f;
    private Label scoreLabel;

    public Hud(SpriteBatch sb) {
        viewport = new FitViewport(ZombieTrain.V_WIDTH, ZombieTrain.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Label.LabelStyle deannaLabelStyle = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("*.fnt"));
        deannaLabelStyle.font = myFont;
        deannaLabelStyle.fontColor = Color.GOLD;

        scoreLabel = new Label(String.valueOf(score.intValue()), deannaLabelStyle);
        scoreLabel.setPosition(SCORE_POS.x, SCORE_POS.y);
        scoreLabel.setFontScale(0.25f);

        stage.addActor(scoreLabel);
    }

    public void update(float dt) {
        scoreLabel.setText(String.valueOf(score.intValue()));

        if (score > localHighScore) {
            localHighScore = score.intValue();
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public static int getLocalHighScore() {
        return localHighScore.intValue();
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
