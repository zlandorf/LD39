package com.pmilian.ld.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Zombie extends Entity {

    private static final float MOVE_DISTANCE = 1f;
    private static final float ZOMBIE_MAX_SPEED = 0.7f;

    private Player player;

    public Zombie(Player player, TextureAtlas atlas, float x, float y) {
        this.player = player;
        this.sprite = atlas.createSprite("zombie");
        setPosition(x, y);
    }

    @Override
    public void update() {

        dx = 0;
        dy = 0;

        if (Math.abs(player.x - this.x) > MOVE_DISTANCE) {
            dx = Math.max(-ZOMBIE_MAX_SPEED, Math.min(ZOMBIE_MAX_SPEED, player.x - this.x));
        }
        if (Math.abs(player.y - this.y) > MOVE_DISTANCE) {
            dy = Math.max(-ZOMBIE_MAX_SPEED, Math.min(ZOMBIE_MAX_SPEED, player.y - this.y));
        }

        super.update();
    }
}
