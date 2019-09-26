package com.joshuawyllie.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.joshuawyllie.game.ZombieTrain;

public class Hud implements Disposable {
    private final Vector2 SCORE_POS = new Vector2(ZombieTrain.V_INIT_WIDTH / 2 - 12, ZombieTrain.V_INIT_HEIGHT - 125);
    private static Integer localHighScore = 0;

    public Stage stage;
    private Viewport gameCam;

    private Float score = 0f;
    private Label scoreLabel;

    public Hud(SpriteBatch sb, Viewport gameCam) {
        stage = new Stage(gameCam, sb);

        Label.LabelStyle deannaLabelStyle = new Label.LabelStyle();
        deannaLabelStyle.font = new BitmapFont(Gdx.files.internal("*.fnt"));
        deannaLabelStyle.fontColor = Color.GOLDENROD;

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
        return localHighScore;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
