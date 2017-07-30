package com.pmilian.ld.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.pmilian.ld.World;

public class Car extends Entity {
    public Car(World world, TextureAtlas atlas, float x, float y) {
        super(world);
        sprite = atlas.createSprite("car");
        setPosition(x, y);
    }
}
