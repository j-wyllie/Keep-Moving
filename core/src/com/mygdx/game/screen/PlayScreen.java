package com.mygdx.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ZombieTrain;
import com.mygdx.game.tool.B2WorldCreator;
import com.mygdx.game.tool.InputHandler;
import com.mygdx.game.sprite.Player;
import com.mygdx.game.sprite.Zombie;
import com.mygdx.game.tool.WorldContactListener;

import java.util.ArrayList;

public class PlayScreen implements Screen {
    public ZombieTrain game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private  InputHandler inputHandler;
    public World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    private Player mainPlayer;
    private ArrayList<Zombie> zombies;

    public PlayScreen(ZombieTrain game) {
        this.game = game;

        // create cam used to follow the player through cam world
        gamecam = new OrthographicCamera();

        // create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(ZombieTrain.V_WIDTH, ZombieTrain.V_HEIGHT, gamecam);

        // initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // initialise input handler
        inputHandler = new InputHandler(gamecam);

        // initialise box2d
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        world.setContactListener(new WorldContactListener());

        // generate sprites
        this.mainPlayer = new Player(this);
        this.zombies = new ArrayList<Zombie>();
        for (int i = 0; i < 10; i++) {
            zombies.add(new Zombie(this));
        }

    }

    @Override
    public void show() {

    }

    private void update(float dt) {
        //update our gamecam with correct coordinates after changes
        gamecam.update();

        mainPlayer.update(dt);
        for (Zombie zombie : zombies) {
            zombie.update(dt, mainPlayer.getOriginBasedPos());
        }
    }

    @Override
    public void render(float dt) {
        update(dt);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
            mainPlayer.draw(game.batch);
            for (Zombie zombie : zombies) {
                zombie.draw(game.batch);
            }
        game.batch.end();
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

    }

    public InputHandler getInputHandler() { return inputHandler; }
}
