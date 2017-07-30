package com.pmilian.ld.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.pmilian.ld.World;

public class Jerrycan extends Entity {

    private boolean full = true;

    private Sprite fullSprite;
    private Sprite emptySprite;

    public Jerrycan(World world, TextureAtlas atlas, float x, float y) {
        super(world);
        fullSprite = atlas.createSprite("jerrycan", 0);
        emptySprite = atlas.createSprite("jerrycan", 1);
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
