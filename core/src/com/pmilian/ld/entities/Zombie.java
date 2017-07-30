package com.pmilian.ld.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

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

        Vector2 toPlayer = new Vector2(player.x, player.y).add(-x, -y);
        toPlayer = toPlayer.nor().scl(ZOMBIE_MAX_SPEED);

        dx = toPlayer.x;
        dy = toPlayer.y;

        super.update();
    }
}
