package com.pmilian.ld.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Jerrycan extends Entity {

    public Jerrycan(TextureAtlas atlas, float x, float y) {
        sprite = atlas.createSprite("jerrycan");
        setPosition(x, y);
    }

    public void collideWithPlayer(Player player) {

    }

}
