package com.joshuawyllie.game.sprite;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.joshuawyllie.game.ZombieTrain;
import com.joshuawyllie.game.screen.PlayScreen;

public class SuperZombie extends Agent {
    private static final float HIT_BOX_RADIUS = 10;
    private final int ZOMBIE_POS_VARIANCE = ZombieTrain.V_INIT_WIDTH + ZombieTrain.V_INIT_HEIGHT;
    private final int MOVE_SPEED_VARIANCE = 22;
    private final int MOVE_SPEED_MIN = 8;

    public SuperZombie(PlayScreen playScreen) {
        super(playScreen, "super_zombie.png", ZombieTrain.ZOMBIE_COLLISION_BIT, HIT_BOX_RADIUS);
        b2body.setUserData(this);

        setMoveSpeed(genRandMoveSpeed());
        setStartPos(genStartPos());
    }

    @Override
    public void setup() {
        super.setup();
        setStartPos(genStartPos());
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
        int randPos = MathUtils.random(0, ZOMBIE_POS_VARIANCE);
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
