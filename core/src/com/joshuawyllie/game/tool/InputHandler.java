package com.joshuawyllie.game.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputHandler implements InputProcessor {
    private Camera camera;
    private TouchListener touchListener = null;
    private Vector3 tp;
    private boolean dragging;

    public InputHandler(Camera camera) {
        this.camera = camera;
        Gdx.input.setInputProcessor(this);
        tp = new Vector3(0, 0, 0);
    }

    public InputHandler(Camera camera, TouchListener touchListener) {
        this.camera = camera;
        this.touchListener = touchListener;
        Gdx.input.setInputProcessor(this);
        tp = new Vector3(0, 0, 0);
    }


    public InputHandler(Camera camera, Vector2 startPos) {
        this.camera = camera;
        Gdx.input.setInputProcessor(this);
        tp = new Vector3(startPos.x, startPos.y, 0);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // ignore if its not left mouse button or first touch pointer
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        dragging = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        if (touchListener != null) {
            touchListener.onTouchUp();
        }
        dragging = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!dragging) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public Vector3 getTouchPoint() {
        return tp;
    }

    public float getX() {
        return tp.x;
    }

    public float getY() {
        return tp.y;
    }

    public static boolean isTouched() {
        return Gdx.input.isTouched();
    }
}
