package com.pmilian.ld.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.pmilian.ld.World;

public class Entity {

    public Sprite sprite;

    public float prevX, prevY;
    public float x, y;
    public float dx, dy;

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        prevX = x;
        prevY = y;

        if (dx > 0) {
            sprite.setFlip(false, false);
        } else if (dx < 0){
            sprite.setFlip(true, false);
        }
        x += dx;
        y += dy;

        x = Math.max(0, Math.min(World.WIDTH - sprite.getWidth(), x));
        y = Math.max(0, Math.min(World.HEIGHT - sprite.getHeight(), y));
        sprite.setPosition(x, y);
    }

    public void render(Batch batch) {
        sprite.draw(batch);
    }

    public void collideWithObstacle(Rectangle obstacle) {
        Rectangle rect = new Rectangle(sprite.getBoundingRectangle());
        rect.setX(prevX);
        if (!obstacle.overlaps(rect)) {
            x = prevX;
        } else {
            rect.setX(x);
            rect.setY(prevY);
            if (!obstacle.overlaps(rect)) {
                y = prevY;
            } else {
                x = prevX;
                y = prevY;
            }
        }
        sprite.setPosition(x, y);
    }

}
