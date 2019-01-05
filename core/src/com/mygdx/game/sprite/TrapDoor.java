package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.screen.PlayScreen;

public class TrapDoor extends Sprite {
    private final static float HIDE_TIME_MILLIS = 4000;
    private final static float SPAWN_TIME_MILLIS = 4000;
    private final static float OPEN_TIME_MILLIS = 400;
    private final static float SPAWN_POSITION_MARGIN = 7;
    private final static Integer FRAME_COLS = 4;
    private final static Integer FRAME_ROWS = 2;
    private final static float FRAME_DURATION = 0.1f;
    private static float ANIMATION_TIME;

    private PlayScreen playScreen;
    private Texture spriteSheet;
    private Animation<TextureRegion> openAnimation;
    private float stateTime;
    private float moveSpeed = 100;
    private final int HIT_BOX_WIDTH = 8;
    private Vector2 position = new Vector2(-50, -50);
    private float lastSpawnTime;
    private float delayTime;
    private Boolean isOpen = false;
    private Boolean isOpening = false;
    private Boolean isClosing = false;

    private World world;
    private Body b2body;

    public TrapDoor(PlayScreen playScreen) {
        this.playScreen = playScreen;

        spriteSheet = new Texture(Gdx.files.internal("trap_door_spritesheet.png"));
        setSize(spriteSheet.getWidth() / FRAME_COLS, spriteSheet.getHeight() / FRAME_ROWS);
        TextureRegion textureRegion = new TextureRegion(spriteSheet, 0, 0, (int) getWidth(), (int) getWidth());

        setOriginCenter();
        setRegion(textureRegion);

        world = playScreen.getWorld();
        definePhysics();

        lastSpawnTime = delayTime = TimeUtils.nanoTime();


        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
                spriteSheet.getWidth() / FRAME_COLS,
                spriteSheet.getHeight() / FRAME_ROWS);



        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        openAnimation = new Animation<TextureRegion>(FRAME_DURATION, walkFrames);

        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0
        stateTime = 0f;

        ANIMATION_TIME = (float) FRAME_ROWS * (float) FRAME_COLS * FRAME_DURATION;
    }

    public void update(float dt) {
        updatePosition();

        // Get current frame of animation for the current stateTime
        float timeSinceLastSpawn = (TimeUtils.nanoTime() - lastSpawnTime) / 1000000000;
        // closing
        if (timeSinceLastSpawn > (SPAWN_TIME_MILLIS / 1000) - ANIMATION_TIME  && !isClosing) {
            stateTime = ANIMATION_TIME;     // start at end of animation
            isClosing = true;
        // opening
        } else if (timeSinceLastSpawn < (SPAWN_TIME_MILLIS / 1000) - ANIMATION_TIME && isClosing) {
            stateTime = 0;
            isClosing = false;
        }

        if (timeSinceLastSpawn > SPAWN_TIME_MILLIS / 1000) {
            stateTime = 0;
        }

        if (isClosing) {
            stateTime -= dt;
        } else {
            stateTime += dt;
        }

        TextureRegion currentFrame = openAnimation.getKeyFrame(stateTime, false);
        setRegion(currentFrame);

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
            if (TimeUtils.nanoTime() - lastSpawnTime < (HIDE_TIME_MILLIS + SPAWN_TIME_MILLIS) * 1000000) {
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
        stateTime = 0;      // reset animation
        return new Vector2(MathUtils.random(ZombieTrain.V_WIDTH - 2 * SPAWN_POSITION_MARGIN) + SPAWN_POSITION_MARGIN, MathUtils.random(ZombieTrain.V_HEIGHT - 2 * SPAWN_POSITION_MARGIN) + SPAWN_POSITION_MARGIN);
    }

    public Boolean isOpen() {
        return isOpen;
    }
}
