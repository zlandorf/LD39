package com.pmilian.ld.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Generator extends Entity {

    float power = 50;

    public Generator(TextureAtlas atlas, float x, float y) {
        sprite = atlas.createSprite("generator");
        setPosition(x, y);
    }

    @Override
    public void render(Batch batch) {
        super.render(batch);
    }
}
