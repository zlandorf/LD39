package com.pmilian.ld.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.pmilian.ld.World;

public class Jerrycan extends Entity {

    public Jerrycan(World world, TextureAtlas atlas, float x, float y) {
        super(world);
        sprite = atlas.createSprite("jerrycan");
        setPosition(x, y);
    }

}
