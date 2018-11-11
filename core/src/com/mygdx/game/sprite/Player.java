package com.mygdx.game.sprite;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.screen.PlayScreen;

public class Player extends Agent {
    private  PlayScreen playScreen;

    public Player(PlayScreen playScreen) {
        super(playScreen, "player.png", ZombieTrain.PLAYER_COLLISION_BIT);
        this.playScreen = playScreen;

        setStartPos(genStartPos());
    }

    @Override
    public void update(float dt) {
        setTargetPos(playScreen.getInputHandler().getX(), playScreen.getInputHandler().getY());
        moveTowardsTarget(dt);
    }

    protected Vector2 genStartPos() {
        return new Vector2(ZombieTrain.V_WIDTH / 2, ZombieTrain.V_HEIGHT / 2);
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }
}