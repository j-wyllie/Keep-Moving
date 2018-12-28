package com.mygdx.game.sprite;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.screen.PlayScreen;

import java.util.Random;

public class Zombie extends Agent {
    private final int ZOMBIE_POS_VARIANCE = ZombieTrain.V_WIDTH + ZombieTrain.V_HEIGHT;
    private final int MOVE_SPEED_VARIANCE = 60;
    private final int MOVE_SPEED_MIN = 20;

    public Zombie(PlayScreen playScreen) {
        super(playScreen, "zombie.png", ZombieTrain.ZOMBIE_COLLISION_BIT);

        b2body.setUserData(this);

        setMoveSpeed(genRandMoveSpeed());
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

        // randPos lies on the virtual
        if (randPos >= ZombieTrain.V_WIDTH) {
            randPos -= ZombieTrain.V_WIDTH;
            if (isFarSide) {
                pos = new Vector2(ZombieTrain.V_WIDTH, randPos);
            } else {
                pos = new Vector2(0, randPos);
            }
        } else {    // randPos lies on the horizontal
            if (isFarSide) {
                pos = new Vector2(randPos, ZombieTrain.V_HEIGHT);
            } else {
                pos = new Vector2(randPos, 0);
            }
        }
        return pos;
    }

    public void dispose() {
        super.dispose();
    }
}
