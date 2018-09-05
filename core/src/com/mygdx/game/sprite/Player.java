package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.screen.PlayScreen;

public class Player extends Agent {
    private  PlayScreen playScreen;

    public Player(PlayScreen playScreen) {
        super(playScreen, "player.png");
        this.playScreen = playScreen;
    }

    @Override
    public void update(float dt) {
        setTargetPos(playScreen.getInputHandler().getX(), playScreen.getInputHandler().getY());
        moveTowardsTarget(dt);
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }
}
