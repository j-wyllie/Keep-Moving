package com.mygdx.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.screen.PlayScreen;

public class Player extends Sprite {
    private PlayScreen playScreen;
    private Texture playerImage;

    public Player(PlayScreen playScreen) {
        this.playScreen = playScreen;
        playerImage = new Texture(Gdx.files.internal("player.png"));
        setSize(playerImage.getWidth(), playerImage.getHeight());
        TextureRegion textureRegion = new TextureRegion(playerImage, 0, 0, (int) getWidth(), (int) getWidth());
        setBounds(0, 0, getWidth(), getHeight());

        setRegion(textureRegion);
    }

    public void update(float dt) {
        System.out.print(getX() + " " + getY() + "\n");
        setBounds(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }
}
