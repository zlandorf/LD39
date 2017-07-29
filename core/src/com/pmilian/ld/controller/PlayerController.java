package com.pmilian.ld.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.pmilian.ld.entities.Player;

public class PlayerController {

    private static final float MOVE_AMOUNT = .8f;

    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void update() {
        player.dx = 0;
        player.dy = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.dy += MOVE_AMOUNT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.dy += -MOVE_AMOUNT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.dx += MOVE_AMOUNT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.dx += -MOVE_AMOUNT;
        }
    }

}
