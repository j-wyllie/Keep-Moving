package com.mygdx.game.sprite;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.screen.PlayScreen;

import java.util.Random;

public class Zombie extends Agent {
    private Random random;
    private final int ZOMBIE_POS_VARIANCE = ZombieTrain.V_WIDTH + ZombieTrain.V_HEIGHT;
    private final int MOVE_SPEED_VARIANCE = 25;
    private final int MOVE_SPEED_MIN = 45;

    public Zombie(PlayScreen playScreen) {
        super(playScreen, "zombie.png", ZombieTrain.ZOMBIE_COLLISION_BIT);

        random = new Random();
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
        return MOVE_SPEED_MIN + random.nextInt(MOVE_SPEED_VARIANCE);
    }

    protected Vector2 genStartPos() {
        Vector2 pos;
        int randPos = random.nextInt(random.nextInt(ZOMBIE_POS_VARIANCE));
        boolean isFarSide = random.nextBoolean();
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
}
