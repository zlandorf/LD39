package com.pmilian.ld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GameOverScreen implements Screen {

    private LD39 game;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private String text;

    public GameOverScreen(LD39 game, String deathCause, float score) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.text = "Score: " + (int) score + "\n\n" + deathCause + "\n\nClick to try again";
        this.viewport = new ExtendViewport(250, 160, camera);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.font.setColor(Color.WHITE);

        game.batch.begin();
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(game.font, text);
        game.font.draw(game.batch, text, camera.viewportWidth / 2f - glyphLayout.width / 2f, camera.viewportHeight / 2f + glyphLayout.height / 2f);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
        }
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
    }

}
