package com.pmilian.ld.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tv {

    private Animation<TextureRegion> animation;
    private float x, y;
    private float stateTime = 0;

    public Tv(TextureAtlas atlas, float x, float y) {
        animation = new Animation<>(0.4f, atlas.findRegions("tv"), Animation.PlayMode.LOOP);
        this.x = x;
        this.y = y;
    }

    public void render(Batch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = animation.getKeyFrame(stateTime);
        batch.draw(frame, x, y);
    }
}
