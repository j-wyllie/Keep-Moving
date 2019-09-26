package com.joshuawyllie.game.sprite;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.joshuawyllie.game.ZombieTrain;
import com.joshuawyllie.game.screen.PlayScreen;

public class Zombie extends Agent {
    private static final float HIT_BOX_RADIUS = 5;
    private final int MOVE_SPEED_VARIANCE = 60;
    private final int MOVE_SPEED_MIN = 20;
    private float zombiePosVariance = ZombieTrain.V_INIT_WIDTH + ZombieTrain.V_INIT_HEIGHT;

    public Zombie(PlayScreen playScreen) {
        super(playScreen, "zombie.png", ZombieTrain.ZOMBIE_COLLISION_BIT, HIT_BOX_RADIUS);

        b2body.setUserData(this);

        setMoveSpeed(genRandMoveSpeed());
        setStartPos(genStartPos());
    }

    @Override
    public void setup() {
        super.setup();
        setStartPos(genStartPos());
        zombiePosVariance = screenWidth + screenHeight;
    }

    @Override
    public void update(float dt) {
        moveTowardsTarget(dt);
    }

    public void update(float dt, Vector2 targetPos) {
        setTargetPos(targetPos);
        moveTowardsTarget(dt);
    }

    private int genRandMoveSpeed() {
        return MOVE_SPEED_MIN + MathUtils.random(0, MOVE_SPEED_VARIANCE);
    }

    protected Vector2 genStartPos() {
        Vector2 pos;
        float randPos = MathUtils.random(0, zombiePosVariance);
        boolean isFarSide = MathUtils.randomBoolean();

        // randPos lies on the vertical
        if (randPos >= screenWidth) {
            randPos -= screenWidth;
            if (isFarSide) {
                pos = new Vector2(screenWidth + getWidth(), randPos);
            } else {
                pos = new Vector2(-getWidth(), randPos);
            }
        } else {    // randPos lies on the horizontal
            if (isFarSide) {
                pos = new Vector2(randPos, screenHeight + getHeight());
            } else {
                pos = new Vector2(randPos, -getHeight());
            }
        }
        return pos;
    }

    public void dispose() {
        super.dispose();
    }
}
