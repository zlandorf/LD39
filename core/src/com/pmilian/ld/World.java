package com.pmilian.ld;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.pmilian.ld.controller.PlayerController;
import com.pmilian.ld.entities.Entity;
import com.pmilian.ld.entities.Player;
import com.pmilian.ld.entities.Zombie;

import java.util.ArrayList;
import java.util.List;

public class World {
    private PlayerController controller;
    private Player player;
    private List<Zombie> zombies;
    private Sprite map;

    private List<Rectangle> obstacles;

    public World(TextureAtlas atlas) {
        this.map = atlas.createSprite("map");
        this.player = new Player(atlas, 220, 300);
        this.controller = new PlayerController(player);
        this.zombies = new ArrayList<Zombie>();
        this.zombies.add(new Zombie(player, atlas, 50, 50));
        initObstacles();
    }

    private void initObstacles() {
        obstacles = new ArrayList<Rectangle>();
        obstacles.add(new Rectangle(171, 250, 5, 98));
        obstacles.add(new Rectangle(175, 347, 100, 5));
        obstacles.add(new Rectangle(273, 250, 5, 98));
        obstacles.add(new Rectangle(175, 246, 42, 5));
        obstacles.add(new Rectangle(230, 246, 44, 5));
    }

    public Player getPlayer() {
        return player;
    }

    public void update() {
        controller.update();
        player.update();
        for (Entity zombie : zombies) {
            zombie.update();
        }

        for (Rectangle obstacle: obstacles) {
            if (player.sprite.getBoundingRectangle().overlaps(obstacle)) {
                player.collideWithObstacle(obstacle);
            }

            for (Zombie zombie: zombies) {
                if (zombie.sprite.getBoundingRectangle().overlaps(obstacle)) {
                    zombie.collideWithObstacle(obstacle);
                }
            }
        }

        for (Zombie zombie: zombies) {
            if (player.sprite.getBoundingRectangle().overlaps(zombie.sprite.getBoundingRectangle())) {
                player.collideWithZombie(zombie);
            }
        }
    }

    public void render(Batch batch) {
        map.draw(batch);
        for (Entity zombie : zombies) {
            zombie.render(batch);
        }
        player.render(batch);
    }

}
