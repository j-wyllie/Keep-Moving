package com.joshuawyllie.game.sprite;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.joshuawyllie.game.ZombieTrain;
import com.joshuawyllie.game.screen.PlayScreen;

public class Player extends Agent {
    private static final float HIT_BOX_RADIUS = 5;
    private PlayScreen playScreen;

    public Player(PlayScreen playScreen) {
        super(playScreen, "brain.png", ZombieTrain.PLAYER_COLLISION_BIT, HIT_BOX_RADIUS);
        this.playScreen = playScreen;

        setStartPos(genStartPos());
    }

    @Override
    public void setup() {
        super.setup();
        setStartPos(genStartPos());
    }

    @Override
    public void update(float dt) {

        setTargetPos(playScreen.getInputHandler().getX(), playScreen.getInputHandler().getY());
        moveTowardsTarget(dt);
    }

    protected Vector2 genStartPos() {
        return new Vector2(playScreen.getGameWidth() / 2, playScreen.getGameHeight() / 2);
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }
}