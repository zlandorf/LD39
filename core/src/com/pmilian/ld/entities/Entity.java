package com.pmilian.ld.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Entity {

    private static final float MIN_X = 0;
    private static final float MAX_X = 512;
    private static final float MIN_Y = 0;
    private static final float MAX_Y = 512;

    public Sprite sprite;

    public float x, y;
    public float dx, dy;

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        updateX();
        updateY();
    }

    private void updateX() {
        if (dx > 0) {
            sprite.setFlip(false, false);
        } else if (dx < 0){
            sprite.setFlip(true, false);
        }
        x += dx;
        dx *= 0.95;

        x = Math.max(MIN_X, Math.min(MAX_X - sprite.getWidth(), x));
    }

    private void updateY() {
        y += dy;
        dy *= 0.95;
        y = Math.max(MIN_Y, Math.min(MAX_Y - sprite.getHeight(), y));
    }

    public void render(Batch batch) {
        sprite.setPosition(x, y);
        sprite.draw(batch);
    }

}
