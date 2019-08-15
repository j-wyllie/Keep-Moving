package com.mygdx.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Scene.Hud;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.sprite.Player;
import com.mygdx.game.sprite.TrapDoor;
import com.mygdx.game.sprite.Zombie;
import com.mygdx.game.tool.B2WorldCreator;
import com.mygdx.game.tool.InputHandler;
import com.mygdx.game.tool.WorldContactListener;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayScreen implements Screen {
    private static final long SPAWN_TIME_MILLIS = 1000;
    private int NUM_START_ZOMBIES = 10;

    private Float gameWidth = (float) ZombieTrain.V_INIT_WIDTH;
    private Float gameHeight = (float) ZombieTrain.V_INIT_HEIGHT;

    public ZombieTrain game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private InputHandler inputHandler;

    private Hud hud;

    // box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    private ArrayList<Zombie> zombiesToDispose;

    // sprite variables
    private Player mainPlayer;
    private TrapDoor trapDoor;
    private ArrayList<Zombie> zombies;
    private Iterator<Zombie> zombieIter;
    private long lastSpawnTime;
    private Texture backGround;

    private Float totalNumberOfZombiesSpawned = (float) NUM_START_ZOMBIES;

    PlayScreen(ZombieTrain game) {
        this.game = game;

        // create cam used to follow the player through cam world
        gameCam = new OrthographicCamera();

        // create a FitViewport to maintain virtual aspect ratio despite screen size
        //gamePort = new FitViewport(ZombieTrain.V_WIDTH, ZombieTrain.V_HEIGHT, gameCam);
        gamePort = new ExtendViewport(gameWidth, gameHeight, gameCam);

        // initially set our gamcam to be centered correctly at the start of of map
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // initialise input handler
        inputHandler = new InputHandler(gameCam);

        // create time HUD
        hud = new Hud(game.batch, gamePort);

        // initialise box2d
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        world.setContactListener(new WorldContactListener(game, this));
        zombiesToDispose = new ArrayList<Zombie>();

        // generate sprites
        this.mainPlayer = new Player(this);
        this.trapDoor = new TrapDoor(this);
        this.zombies = new ArrayList<Zombie>();
        this.zombieIter = this.zombies.iterator();
        for (int i = 0; i < NUM_START_ZOMBIES; i++) {
            zombies.add(new Zombie(this));
        }
        lastSpawnTime = TimeUtils.nanoTime();

        // load background
        // backGround = new Texture("plain_blue.png");
    }

    public void setup() {
        mainPlayer.setup();
        trapDoor.setup();
        for (Zombie zombie : zombies) {
            zombie.setup();
        }
    }

    @Override
    public void show() {
//        ZombieTrain.adHandler.showAds(false);
    }

    private void update(float dt) {
        // update our gameCam with correct coordinates after changes
        gameCam.update();
        world.step(1 / 60f, 6, 2);
        gameWidth = gamePort.getWorldWidth();
        gameHeight = gamePort.getWorldHeight();

        // dispose of box2d objects
        for (Zombie zombie : zombiesToDispose) {
            zombie.dispose();
        }
        zombiesToDispose.clear();

        // update game timer
        hud.setScore(totalNumberOfZombiesSpawned - NUM_START_ZOMBIES);
        hud.update(dt);

        // spawning new zombies
        if (TimeUtils.nanoTime() - lastSpawnTime > SPAWN_TIME_MILLIS * 1000000) {
            lastSpawnTime = TimeUtils.nanoTime();
            zombies.add(new Zombie(this));
            totalNumberOfZombiesSpawned++;
        }

        mainPlayer.update(dt);
        trapDoor.update(dt);
        for (Zombie zombie : zombies) {
            zombie.update(dt, mainPlayer.getOriginBasedPos());
        }
    }

    public void removeZombie(Zombie zombie) {
        zombies.remove(zombie);
        zombiesToDispose.add(zombie);
    }

    @Override
    public void render(float dt) {
        update(dt);

        // renderer our Box2DDebugLines
        //  b2dr.render(world, gameCam.combined);

        // draw sprites
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        mainPlayer.draw(game.batch);
        trapDoor.draw(game.batch);
        for (Zombie zombie : zombies) {
            zombie.draw(game.batch);
        }
        game.batch.end();

        // draw score Heads Up Display
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public World getWorld() {
        return world;
    }

    public Float getGameWidth() {
        return gameWidth;
    }

    public Float getGameHeight() {
        return gameHeight;
    }
}
