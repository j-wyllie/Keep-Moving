package com.joshuawyllie.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SoundIcon extends Sprite {
    private Viewport viewport;
    private Boolean isMute = false;
    private Texture spritesheet = new Texture(Gdx.files.internal("sound_icon_spritesheet.png"));
    public SoundIcon(Viewport viewport) {
        super();
        this.viewport = viewport;
        setTexture(spritesheet);
        System.out.println("width" + spritesheet.getWidth());
        setRegion(0, 0, spritesheet.getWidth() / 2, spritesheet.getHeight());
        setBounds(0, 0, spritesheet.getWidth() / 2 , spritesheet.getHeight());
        setPosition(viewport.getWorldWidth() - getWidth(), viewport.getWorldHeight() - getHeight());
    }

    public void touched() {
        isMute = !isMute;
        if (isMute) {
            setRegion(spritesheet.getWidth() / 2, 0, spritesheet.getWidth(), spritesheet.getHeight());
            setBounds(0, 0, spritesheet.getWidth(), spritesheet.getHeight());
            setPosition(viewport.getWorldWidth() - getWidth() / 2, viewport.getWorldHeight() - getHeight());
        } else {
            setRegion(0, 0, spritesheet.getWidth() / 2, spritesheet.getHeight());
            setBounds(0, 0, spritesheet.getWidth() / 2, spritesheet.getHeight());
            setPosition(viewport.getWorldWidth() - getWidth(), viewport.getWorldHeight() - getHeight());
        }
    }
}
