package com.pmilian.ld.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.pmilian.ld.Scores;
import com.pmilian.ld.World;

public class Player extends Entity {

    public static final float MAX_HITPOINTS = 100;
    private static final float ZOMBIE_DAMAGE = .3f;

    private Jerrycan jerrycan;
    public float hitpoints = MAX_HITPOINTS;
    public float score = 0;

    public Player(World world, float x, float y) {
        super(world);
        this.sprite = world.atlas.createSprite("player");
        setPosition(x, y);
    }

    @Override
    public void update() {
        super.update();
        score += Scores.Survival * Gdx.graphics.getDeltaTime();
    }

    public void collideWithJerrycan(Jerrycan jerrycan) {
        if (this.jerrycan == null || !this.jerrycan.isFull()) {
            score += Scores.JerrycanPickup;
            this.jerrycan = jerrycan;
            world.jerrycansToRemove.add(jerrycan);
        }
    }

    @Override
    public void render(Batch batch) {
        super.render(batch);
        if (jerrycan != null) {
            jerrycan.sprite.setPosition(x + (sprite.isFlipX() ? 0 : 3), y);
            jerrycan.sprite.setFlip(sprite.isFlipX(), sprite.isFlipY());
            jerrycan.sprite.draw(batch);
        }
    }

    public void collideWithGenerator(Generator generator) {
        if (jerrycan != null && jerrycan.isFull()) {
            generator.power = Math.min(Generator.MAX_POWER, generator.power + 25);

            jerrycan.empty();
            score += Scores.GeneratorFillUp;
        }
    }

    @Override
    public void collideWithZombie(Zombie other) {
        super.collideWithZombie(other);
        hitpoints -= ZOMBIE_DAMAGE;
    }

    public boolean isInSafeZone() {
        return world.safeZone.contains(sprite.getBoundingRectangle());
    }

    public void collideWithCar(Car car) {
        collideWithObstacle(car.sprite.getBoundingRectangle());
        if (jerrycan != null && !jerrycan.isFull() && car.isFull()) {
            score += Scores.CarSiphon;
            jerrycan.fillUp();
            car.empty();
        }
    }
}
