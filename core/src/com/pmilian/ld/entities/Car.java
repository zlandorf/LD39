package com.pmilian.ld.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.pmilian.ld.World;

public class Car extends Entity {

    private boolean full = true;

    private Sprite fullSprite;
    private Sprite emptySprite;

    public Car(World world, TextureAtlas atlas, float x, float y) {
        super(world);
        fullSprite = atlas.createSprite("car", 0);
        emptySprite = atlas.createSprite("car", 1);
        fillUp();
        setPosition(x, y);
    }

    public boolean isFull() {
        return full;
    }

    public void empty() {
        full = false;
        sprite = emptySprite;
    }

    public void fillUp() {
        full = true;
        sprite = fullSprite;
    }


}
