package com.pmilian.ld.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pmilian.ld.World;

import java.util.Random;

public class Zombie extends Entity {

    private enum State {
        PURSUING,
        ROAMING
    }
    private static final float ZOMBIE_MAX_SPEED_PURSUING = 0.7f;

    private static final float ZOMBIE_MAX_SPEED_ROAMING = 0.2f;
    private static final float PURSUE_DISTANCE = 3000;


    private static final float RANDOM_DELAY = 50;
    private static final Random random = new Random();

    private State state = State.ROAMING;
    private Vector2 randomTarget;
    private float nextRandom = 0;

    public Zombie(World world, TextureAtlas atlas, float x, float y) {
        super(world);
        this.sprite = atlas.createSprite("zombie");
        setPosition(x, y);
    }

    @Override
    public void update() {
        updateState();
        dx = 0;
        dy = 0;

        Vector2 target = getTarget();
        Vector2 toTarget = target.cpy().add(-x, -y);

        if (toTarget.len2() > 50) {
            toTarget = toTarget.nor().scl(getSpeed());

            Rectangle rect = new Rectangle(sprite.getBoundingRectangle());
            rect.setX(x + dx);
            if (
                world.zombies.stream()
                    .filter(z -> !z.equals(this))
                    .map(z -> z.sprite.getBoundingRectangle())
                    .noneMatch(rect::overlaps)
                ) {
                dx = toTarget.x;
            }

            rect = new Rectangle(sprite.getBoundingRectangle());
            rect.setY(y + dy);
            if (
                world.zombies.stream()
                    .filter(z -> !z.equals(this))
                    .map(z -> z.sprite.getBoundingRectangle())
                    .noneMatch(rect::overlaps)
                ) {
                dy = toTarget.y;
            }
        }

        super.update();
    }

    private void updateState() {
        if (world.player.isInSafeZone()) {
            state = State.ROAMING;
        } else if (new Vector2(world.player.x, world.player.y).add(-x, -y).len2() < PURSUE_DISTANCE) {
            state = State.PURSUING;
        }
    }


    private float getSpeed() {
        if (state.equals(State.PURSUING)) {
            return ZOMBIE_MAX_SPEED_PURSUING;
        }
        return ZOMBIE_MAX_SPEED_ROAMING;
    }

    private Vector2 getTarget() {
        if (state.equals(State.PURSUING)) {
            return new Vector2(world.player.x, world.player.y);
        }

        if (nextRandom <= 0) {
            randomTarget = new Vector2(random.nextInt(World.WIDTH), random.nextInt(World.HEIGHT));
            nextRandom = random.nextFloat() * RANDOM_DELAY;
        }
        nextRandom -= Gdx.graphics.getDeltaTime();
        return randomTarget;
    }
}
