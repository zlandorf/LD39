package com.pmilian.ld.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Player extends Entity {

    public Player(TextureAtlas atlas, float x, float y) {
        sprite = atlas.createSprite("player");
        setPosition(x, y);
    }

    public void collideWithZombie(Zombie zombie) {

    }
}
