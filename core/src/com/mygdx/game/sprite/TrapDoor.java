package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.screen.PlayScreen;

import java.sql.Time;

import javax.swing.text.Position;

public class TrapDoor extends Sprite {
    private final float DELAY_TIME_MILLIS = 4000;
    private final float SPAWN_TIME_MILLIS = 4000;
    private final float OPEN_TIME_MILLIS = 400;
    private final float SPAWN_POSITION_MARGIN = 7;

    private PlayScreen playScreen;
    private Texture agentImage;
    private float moveSpeed = 100;
    private final int HIT_BOX_WIDTH = 8;
    private Vector2 position = new Vector2(-50, -50);
    private float lastSpawnTime;
    private float delayTime;
    private Boolean isOpen = false;

    private World world;
    private Body b2body;

    public TrapDoor(PlayScreen playScreen) {
        this.playScreen = playScreen;

        agentImage = new Texture(Gdx.files.internal("trap_door_2.png"));
        setSize(agentImage.getWidth(), agentImage.getHeight());
        TextureRegion textureRegion = new TextureRegion(agentImage, 0, 0, (int) getWidth(), (int) getWidth());

        setOriginCenter();
        setRegion(textureRegion);

        world = playScreen.getWorld();
        definePhysics();

        lastSpawnTime = delayTime = TimeUtils.nanoTime();
    }

    public void update(float dt) {
        updatePosition();

        // Update whether the door is open ready to sink zombies
        if (TimeUtils.nanoTime() - lastSpawnTime > OPEN_TIME_MILLIS) {
            isOpen = true;
        } else {
            isOpen = false;
        }
    }

    private void definePhysics() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        b2body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape trapBoxShape = new PolygonShape();
        trapBoxShape.setAsBox(HIT_BOX_WIDTH, HIT_BOX_WIDTH);

        fdef.shape = trapBoxShape;
        fdef.filter.categoryBits = ZombieTrain.TRAP_COLLISION_BIT;
        fdef.filter.maskBits = ZombieTrain.ZOMBIE_COLLISION_BIT;

        b2body.createFixture(fdef).setUserData(this);
    }

    private void updatePosition() {
        if (TimeUtils.nanoTime() - lastSpawnTime > SPAWN_TIME_MILLIS * 1000000) {
            if (TimeUtils.nanoTime() - lastSpawnTime < (DELAY_TIME_MILLIS + SPAWN_TIME_MILLIS) * 1000000) {
                position = new Vector2(-50, -50);
            } else {
                position = genNewPosition();
                lastSpawnTime = TimeUtils.nanoTime();
            }
        }

        // update object and box2d position
        setOriginBasedPosition(position.x, position.y);
        b2body.setTransform(position, 0f);
    }

    private Vector2 genNewPosition() {
        return new Vector2(MathUtils.random(ZombieTrain.V_WIDTH - 2 * SPAWN_POSITION_MARGIN) + SPAWN_POSITION_MARGIN, MathUtils.random(ZombieTrain.V_HEIGHT - 2 * SPAWN_POSITION_MARGIN) + SPAWN_POSITION_MARGIN);
    }

    public Boolean isOpen() {
        return isOpen;
    }
}
