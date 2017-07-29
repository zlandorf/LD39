package com.pmilian.ld.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Generator  {

    private static final float FUEL_CONSUMPTION = .01f;

    float x, y;
    public Rectangle boudingBox;

    Animation<TextureRegion> animation;
    float stateTime = 0;

    float power = 50;

    public Generator(TextureAtlas atlas, float x, float y, float width, float height) {
        animation = new Animation<>(0.13f, atlas.findRegions("generator"), Animation.PlayMode.LOOP);
        this.x = x;
        this.y = y;
        boudingBox = new Rectangle(x, y, width, height);
    }

    public void update() {
        power -= FUEL_CONSUMPTION;
        power = Math.max(0, power);
        System.out.println(power);
    }

    public void render(Batch batch) {
        if (power > 0) {
            stateTime += Gdx.graphics.getDeltaTime();
        } else {
            stateTime = 0;
        }
        TextureRegion frame = animation.getKeyFrame(stateTime);
        batch.draw(frame, x, y);
    }
}
