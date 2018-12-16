package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.screen.PlayScreen;

public abstract class TrapDoor extends Sprite {
    private PlayScreen playScreen;
    private Texture agentImage;
    private float moveSpeed = 100;
    private final int HIT_BOX_RADIUS = 5;
    private short collisionBit = 0;
    private Vector2 position = new Vector2(ZombieTrain.V_WIDTH / 2, ZombieTrain.V_HEIGHT / 2);

    private World world;
    private Body b2body;

    public TrapDoor(PlayScreen playScreen, short collisionBit) {
        this.playScreen = playScreen;
        this.collisionBit = collisionBit;

        agentImage = new Texture(Gdx.files.internal("trap_door.png"));
        setSize(agentImage.getWidth(), agentImage.getHeight());
        TextureRegion textureRegion = new TextureRegion(agentImage, 0, 0, (int) getWidth(), (int) getWidth());

        setOriginCenter();
        setRegion(textureRegion);

        world = playScreen.getWorld();
        definePhysics();
    }

    public abstract void update(float dt);
    private void definePhysics() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(HIT_BOX_RADIUS);

        fdef.shape = shape;
        fdef.filter.categoryBits = collisionBit;

        b2body.createFixture(fdef).setUserData(this);
    }
}
