package com.pmilian.ld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.pmilian.ld.entities.Generator;

import static com.pmilian.ld.entities.Player.MAX_HITPOINTS;

public class GameScreen implements Screen {

    private LD39 game;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private TextureAtlas atlas;

    private World world;

    private ShapeRenderer shapeRenderer;

    public GameScreen(LD39 game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(170, 120, camera);
//        this.viewport = new ExtendViewport(512, 512, camera);
        this.atlas = new TextureAtlas("sprites.txt");
        this.world = new World(atlas);
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        world.update();

        camera.position.set(
            Math.max(camera.viewportWidth / 2, Math.min(World.WIDTH - camera.viewportWidth / 2, world.getPlayer().x)),
            Math.max(camera.viewportHeight / 2, Math.min(World.HEIGHT - camera.viewportHeight / 2, world.getPlayer().y)),
            0
        );
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        world.render(game.batch);
        game.batch.end();

        renderUi();

        if (world.player.hitpoints <= 0) {
            game.setScreen(new GameOverScreen(game, "Zombies ate your brain !", world.player.score));
        } else if (world.generator.power <= 0) {
            game.setScreen(new GameOverScreen(game, "The generator ran out of fuel.\nWithout electricity to power\nyour console, you die of boredom.", world.player.score));
        }
    }

    private void renderUi() {
        Matrix4 uiMatrix = camera.combined.cpy();
        uiMatrix.setToOrtho2D(0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
        shapeRenderer.setProjectionMatrix(uiMatrix);

        renderPowerLeft();
        renderHitpoints();
        renderScore();
    }

    private void renderPowerLeft() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(10, viewport.getScreenHeight() - 40, 200, 24);
        shapeRenderer.rect(5, viewport.getScreenHeight() - 36, 210, 16);

        float ratio = Math.max(0, Math.min(1, world.generator.power / Generator.MAX_POWER));
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(10, viewport.getScreenHeight() - 36, 200 * ratio, 16);
        shapeRenderer.end();
    }

    private void renderHitpoints() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(230, viewport.getScreenHeight() - 40, 200, 24);
        shapeRenderer.rect(225, viewport.getScreenHeight() - 36, 210, 16);

        float ratio = Math.max(0, Math.min(1, world.player.hitpoints / MAX_HITPOINTS));
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(230, viewport.getScreenHeight() - 36, 200 * ratio, 16);
        shapeRenderer.end();
    }

    private void renderScore() {
        Matrix4 scoreMatrix = camera.combined.cpy();
        scoreMatrix.setToOrtho2D(0, 0, viewport.getScreenWidth() / 2, viewport.getScreenHeight() / 2);
        game.batch.setProjectionMatrix(scoreMatrix);

        game.font.setColor(Color.WHITE);
        game.batch.begin();
        GlyphLayout glyphLayout = new GlyphLayout();
        String score = String.valueOf((int)world.player.score);
        glyphLayout.setText(game.font, score);
        game.font.draw(game.batch, score, viewport.getScreenWidth() / 2f - glyphLayout.width - 5, viewport.getScreenHeight() / 2f - glyphLayout.height / 2f - 2);
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
        shapeRenderer.dispose();
    }
}
