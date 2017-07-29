package com.pmilian.ld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.pmilian.ld.controller.PlayerController;
import com.pmilian.ld.entities.Entity;
import com.pmilian.ld.entities.Player;
import com.pmilian.ld.entities.Zombie;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private LD39 game;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private TextureAtlas atlas;

    private PlayerController controller;

    private Player player;
    private List<Entity> zombies;

    private Sprite map;

    public GameScreen(LD39 game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(120, 80, camera);

        this.atlas = new TextureAtlas("sprites.txt");
        this.map = atlas.createSprite("map");
        this.player = new Player(atlas, 0, 0);
        this.controller = new PlayerController(player);
        this.zombies = new ArrayList<Entity>();
        this.zombies.add(new Zombie(player, atlas, 50, 50));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        controller.update();
        player.update();
        for (Entity zombie : zombies) {
            zombie.update();
        }

        camera.position.set(player.x, player.y, 0);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        map.draw(game.batch);
        for (Entity zombie : zombies) {
            zombie.render(game.batch);
        }
        player.render(game.batch);
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
