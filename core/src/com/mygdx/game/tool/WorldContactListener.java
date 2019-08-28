package com.mygdx.game.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.screen.MenuScreen;
import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.sprite.TrapDoor;
import com.mygdx.game.sprite.Zombie;

public class WorldContactListener implements ContactListener {
    private ZombieTrain game;
    private PlayScreen playScreen;

    Sound deadSound = Gdx.audio.newSound(Gdx.files.internal("Random 20.wav"));


    public WorldContactListener(ZombieTrain game, PlayScreen playScreen) {
        this.game = game;
        this.playScreen = playScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case ZombieTrain.PLAYER_COLLISION_BIT | ZombieTrain.ZOMBIE_COLLISION_BIT:
                deadSound.play(1.0f);
                game.setScreen(new MenuScreen(game));
                break;
            case ZombieTrain.ZOMBIE_COLLISION_BIT | ZombieTrain.TRAP_COLLISION_BIT:
                handleZombieTrapDoorCollision(fixA, fixB);
                break;
        }
    }

    private void handleZombieTrapDoorCollision(Fixture fixA, Fixture fixB) {
        // Get Zombie and TrapDoor objects
        Zombie zombie;
        TrapDoor trapDoor;
        if (fixA.getFilterData().categoryBits == ZombieTrain.ZOMBIE_COLLISION_BIT) {
            zombie = (Zombie) fixA.getBody().getUserData();
            // trapDoor = (TrapDoor) fixB.getBody().getUserData();
        } else {
            zombie = (Zombie) fixB.getBody().getUserData();
            // trapDoor = (TrapDoor) fixA.getBody().getUserData();
        }

        //if (trapDoor.isOpen()) {
        playScreen.removeZombie(zombie);
        // }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
