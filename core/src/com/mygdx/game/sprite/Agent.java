package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.screen.PlayScreen;

public abstract class Agent extends Sprite {
    private PlayScreen playScreen;
    private Texture agentImage;
    private float moveSpeed = 100;
    private Vector2 startPos = new Vector2(ZombieTrain.V_WIDTH / 2, ZombieTrain.V_HEIGHT / 2);
    private Vector2 targetPos = new Vector2(ZombieTrain.V_WIDTH / 2, ZombieTrain.V_HEIGHT / 2);

    public Agent(PlayScreen playScreen, String texturePath) {
        this.playScreen = playScreen;

        agentImage = new Texture(Gdx.files.internal(texturePath));
        setSize(agentImage.getWidth(), agentImage.getHeight());
        TextureRegion textureRegion = new TextureRegion(agentImage, 0, 0, (int) getWidth(), (int) getWidth());
        setBounds(startPos.x, startPos.y, getWidth(), getHeight());
        setOriginCenter();
        setRegion(textureRegion);
    }

    public abstract void update(float dt);

    protected void moveTowardsTarget(float dt) {
        Vector2 toTarget = new Vector2(targetPos.x - getOriginBasedX(), targetPos.y - getOriginBasedY());
        if (toTarget.len() > 0.5) {
            toTarget.nor();
            float newX = getOriginBasedX()  + toTarget.x * dt * moveSpeed;
            float newY = getOriginBasedY() + toTarget.y * dt * moveSpeed;
            setOriginBasedPosition(newX, newY);
            setRotation(toTarget.angle() - 90);
        }
    }

    protected void setMoveSpeed(float moveSpeed) { this.moveSpeed = moveSpeed; }
    protected void setTargetPos(Vector2 targetPos) { this.targetPos = targetPos; }
    public void setTargetPos(float x, float y) {
        targetPos.x = x;
        targetPos.y = y;
    }
    public float getOriginBasedX() { return getX() + getOriginX(); }
    public float getOriginBasedY() { return getY() + getOriginY(); }

    public Vector2 getOriginBasedPos() { return new Vector2(getOriginBasedX(), getOriginBasedY()); }

    public Vector2 getStartPos() { return startPos; }
    protected void setStartPos(Vector2 startPos) {
        this.startPos = startPos;
        setBounds(startPos.x, startPos.y, getWidth(), getHeight());
    }
}
