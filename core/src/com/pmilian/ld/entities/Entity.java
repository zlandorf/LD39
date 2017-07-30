package com.pmilian.ld.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pmilian.ld.World;

import java.util.stream.Stream;

public class Entity {

    public Sprite sprite;

    public float prevX, prevY;
    public float x, y;
    public float dx, dy;

    public World world;

    public Entity(World world) {
        this.world = world;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        sprite.setPosition(x, y);
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
        sprite.setPosition(x, y);
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

    public void collideWithZombie(Zombie other) {
        Vector2 dir = new Vector2(x, y).add(-other.x, -other.y).nor().scl(.3f);

        Rectangle rect = new Rectangle(sprite.getBoundingRectangle());
        rect.setX(x + dir.x);
        rect.setY(y + dir.y);
        if (Stream.concat(world.obstacles.stream(), world.cars.stream().map(car -> car.sprite.getBoundingRectangle())).noneMatch(rect::overlaps)) {
            x += dir.x;
            y += dir.y;
        }

        rect = new Rectangle(other.sprite.getBoundingRectangle());
        rect.setX(other.x - dir.x);
        rect.setY(other.y - dir.y);

        if (Stream.concat(world.obstacles.stream(), world.cars.stream().map(car -> car.sprite.getBoundingRectangle())).noneMatch(rect::overlaps)) {
            other.x -= dir.x;
            other.y -= dir.y;
        }
    }

}
