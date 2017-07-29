package com.pmilian.ld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LD39 extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    TextureAtlas atlas;
    Sprite player;
    
    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        atlas = new TextureAtlas("sprites.txt");
        player = atlas.createSprite("player");
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        player.draw(batch);
        batch.end();
    }
    
    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
        atlas.dispose();
    }
}
