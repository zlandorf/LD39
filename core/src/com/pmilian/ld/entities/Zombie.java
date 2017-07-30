package com.pmilian.ld.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pmilian.ld.Scores;
import com.pmilian.ld.World;

import java.util.Random;

public class Zombie extends Entity {

    private enum State {
        PURSUING,
        ROAMING,
        ELECTRIFIED
    }
    private static final float ZOMBIE_MAX_SPEED_PURSUING = 0.8f;

    private static final float ZOMBIE_MAX_SPEED_ROAMING = 0.2f;
    private static final float PURSUE_DISTANCE = 3000;
    private static final float LOCK_OFF_DISTANCE = 4500;

    private static final float ZOMBIE_ELETROCUTION_POWER = 3;

    private static final float RANDOM_DELAY = 10;
    private static final Random random = new Random();

    private State state = State.ROAMING;
    private Vector2 randomTarget;
    private float nextRandom = 0;

    private Animation<TextureRegion> animation;
    private float stateTime = 0;
    private float electrocutionTime = 3;

    public Zombie(World world, TextureAtlas atlas, float x, float y) {
        super(world);
        this.sprite = atlas.createSprite("zombie");
        animation = new Animation<>(0.13f, atlas.findRegions("zombie-electrified"), Animation.PlayMode.LOOP);
        setPosition(x, y);
    }

    @Override
    public void update() {
        if (state == State.ELECTRIFIED) {
            if (electrocutionTime < 0) {
                world.zombiesToRemove.add(this);
            }
            electrocutionTime -= Gdx.graphics.getDeltaTime();
        } else {
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
        }

        super.update();
    }

    private void updateState() {
        float playerDistance = new Vector2(world.player.x, world.player.y).add(-x, -y).len2();
        if (world.player.isInSafeZone() || playerDistance > LOCK_OFF_DISTANCE) {
            state = State.ROAMING;
        } else if (playerDistance < PURSUE_DISTANCE) {
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

    public void collideWithSafeZone() {
        dx = dy = 0;
        if (!state.equals(State.ELECTRIFIED)) {
            state = State.ELECTRIFIED;
            world.generator.power -= ZOMBIE_ELETROCUTION_POWER;
            world.player.score += Scores.ZombieDeath;
        }
    }

    public void collideWithCar(Car car) {
        super.collideWithObstacle(car.sprite.getBoundingRectangle());
    }

    @Override
    public void collideWithObstacle(Rectangle obstacle) {
        collideWithSafeZone();
    }

    @Override
    public void render(Batch batch) {
        if (state == State.ELECTRIFIED) {
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion frame = animation.getKeyFrame(stateTime);
            batch.draw(frame, x, y);
        } else {
            sprite.draw(batch);
        }
    }
}
