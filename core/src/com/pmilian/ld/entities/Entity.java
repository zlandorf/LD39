package com.pmilian.ld.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Entity {

    private static final int GRID_SIZE = 16;

    public Sprite sprite;

    public int gridX, gridY;
    public float cellRatioX, cellRatioY;
    public float x, y;

    public float dx, dy;

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        gridX = (int) (this.x / GRID_SIZE);
        gridY = (int) (this.y / GRID_SIZE);
        cellRatioX = (x - gridX * GRID_SIZE) / GRID_SIZE;
        cellRatioY = (y - gridY * GRID_SIZE) / GRID_SIZE;
    }

    public void update() {
        updateX();
        updateY();
    }

    private void updateX() {
        if (dx > 0) {
            sprite.setFlip(false, false);
        } else if (dx < 0){
            sprite.setFlip(true, false);
        }
        cellRatioX += dx;
        dx *= 0.95;

        while (cellRatioX > 1) {
            cellRatioX -= 1;
            gridX ++;
        }
        while (cellRatioX < 0) {
            cellRatioX += 1;
            gridX --;
        }

        x = (gridX + cellRatioX) * GRID_SIZE;
    }

    private void updateY() {
        cellRatioY += dy;
        dy *= 0.95;

        while (cellRatioY > 1) {
            cellRatioY -= 1;
            gridY ++;
        }
        while (cellRatioY < 0) {
            cellRatioY += 1;
            gridY --;
        }
        y = (gridY + cellRatioY) * GRID_SIZE;
    }

    public void render(Batch batch) {
        sprite.setPosition(x, y);
        sprite.draw(batch);
    }

}
