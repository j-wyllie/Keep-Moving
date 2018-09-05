package com.mygdx.game.sprite;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.screen.PlayScreen;

import java.util.Random;

public class Zombie extends Agent {
    private Random random;
    private final int FLUCTUATION = ZombieTrain.V_WIDTH + ZombieTrain.V_HEIGHT;
    private final float MOVE_SPEED = 60;

    public Zombie(PlayScreen playScreen) {
        super(playScreen, "zombie.png");

        setMoveSpeed(MOVE_SPEED);
        random = new Random();
        setStartPos(generateRandomPosition());
    }

    @Override
    public void update(float dt) {
        moveTowardsTarget(dt);
    }

    public void update(float dt, Vector2 targetPos) {
        setTargetPos(targetPos);
        moveTowardsTarget(dt);
    }

    private Vector2 generateRandomPosition() {
        Vector2 pos;
        int randPos = random.nextInt(random.nextInt(FLUCTUATION));
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
