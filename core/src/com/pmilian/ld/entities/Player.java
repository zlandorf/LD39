package com.pmilian.ld.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.pmilian.ld.World;

public class Player extends Entity {

    private Jerrycan jerrycan;

    public Player(World world, float x, float y) {
        super(world);
        this.sprite = world.atlas.createSprite("player");
        setPosition(x, y);
    }

    public void collideWithJerrycan(Jerrycan jerrycan) {
        if (this.jerrycan == null) {
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
            generator.power += 20;
            jerrycan.empty();
        }
    }

    public boolean isInSafeZone() {
        return world.safeZone.contains(sprite.getBoundingRectangle());
    }

    public void collideWithCar(Car car) {
        if (jerrycan != null && !jerrycan.isFull() && car.isFull()) {
            jerrycan.fillUp();
            car.empty();
        }
    }
}
