package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.screen.PlayScreen;

public abstract class Agent extends Sprite {
    private PlayScreen playScreen;
    private Texture agentImage;
    private float moveSpeed = 100;
    private final float hitBoxRadius;
    private short collisionBit = 0;
    float screenWidth = ZombieTrain.V_INIT_WIDTH;
    float screenHeight = ZombieTrain.V_INIT_HEIGHT;
    private Vector2 startPos = new Vector2(ZombieTrain.V_INIT_WIDTH / 2, ZombieTrain.V_INIT_HEIGHT / 2);
    private Vector2 targetPos = new Vector2(ZombieTrain.V_INIT_WIDTH / 2, ZombieTrain.V_INIT_HEIGHT / 2);

    private World world;
    protected Body b2body;
    protected Vector2 velocity;

    public Agent(PlayScreen playScreen, String texturePath, short collisionBit, float hitBoxRadius) {
        this.playScreen = playScreen;
        this.collisionBit = collisionBit;
        this.hitBoxRadius = hitBoxRadius;

        agentImage = new Texture(Gdx.files.internal(texturePath));
        setSize(agentImage.getWidth(), agentImage.getHeight());
        TextureRegion textureRegion = new TextureRegion(agentImage, 0, 0, (int) getWidth(), (int) getWidth());

        setOriginCenter();
        setRegion(textureRegion);

        world = playScreen.getWorld();
        definePhysics();
    }

    public void setup() {
        screenWidth = playScreen.getGameWidth();
        screenHeight = playScreen.getGameHeight();
        startPos = new Vector2(screenWidth / 2, screenHeight / 2);
        targetPos = new Vector2(screenWidth / 2, screenHeight / 2);
    }

    public abstract void update(float dt);

    private void definePhysics() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(startPos);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(hitBoxRadius);

        fdef.shape = shape;
        fdef.filter.categoryBits = collisionBit;

        b2body.createFixture(fdef);
    }

    protected void moveTowardsTarget(float dt) {
        Vector2 toTarget = new Vector2(targetPos.x - getOriginBasedX(), targetPos.y - getOriginBasedY());
        if (toTarget.len() > 0.5) {
            toTarget.nor();
            setOriginBasedPosition(b2body.getPosition().x, b2body.getPosition().y);
            setRotation(toTarget.angle() - 90);
            b2body.setLinearVelocity(toTarget.scl(moveSpeed));
        } else {
            b2body.setLinearVelocity(Vector2.Zero);
        }
    }

    protected void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    protected void setTargetPos(Vector2 targetPos) {
        this.targetPos = targetPos;
    }

    public void setTargetPos(float x, float y) {
        targetPos.x = x;
        targetPos.y = y;
    }

    public float getOriginBasedX() {
        return b2body.getPosition().x;
    }

    public float getOriginBasedY() {
        return b2body.getPosition().y;
    }

    public Vector2 getOriginBasedPos() {
        return new Vector2(getOriginBasedX(), getOriginBasedY());
    }

    /**
     * Initialisation requirement
     *
     * @param startPos
     */
    protected void setStartPos(Vector2 startPos) {
        this.startPos = startPos;
        setBounds(startPos.x, startPos.y, getWidth(), getHeight());
        b2body.setTransform(startPos, 0);
    }

    public void dispose() {
        world.destroyBody(b2body);
    }
}