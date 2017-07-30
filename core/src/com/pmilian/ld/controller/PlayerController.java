package com.pmilian.ld.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.pmilian.ld.entities.Player;

public class PlayerController {

    private static final float MOVE_AMOUNT = 1.1f;

    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void update() {

        Vector2 movement = Vector2.Zero.cpy();

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            movement.y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            movement.y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movement.x += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movement.x -= 1;
        }

        movement = movement.nor().scl(MOVE_AMOUNT);

        player.dx = movement.x;
        player.dy = movement.y;
    }

}
