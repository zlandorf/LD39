package com.pmilian.ld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GameScreen implements Screen {

    private LD39 game;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private TextureAtlas atlas;

    private World world;

    public GameScreen(LD39 game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(250, 160, camera);
        this.atlas = new TextureAtlas("sprites.txt");

        this.world = new World(atlas);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        world.update();

        camera.position.set(world.getPlayer().x, world.getPlayer().y, 0);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        world.render(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        game.batch.setProjectionMatrix(camera.combined);
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
        atlas.dispose();
    }
}
