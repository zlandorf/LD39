package com.pmilian.ld;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.pmilian.ld.controller.PlayerController;
import com.pmilian.ld.entities.Generator;
import com.pmilian.ld.entities.Jerrycan;
import com.pmilian.ld.entities.Player;
import com.pmilian.ld.entities.Zombie;

import java.util.ArrayList;
import java.util.List;

public class World {

    public static final int WIDTH = 512;
    public static final int HEIGHT = 512;

    public TextureAtlas atlas;
    public List<Jerrycan> jerrycans;
    public List<Jerrycan> jerrycansToRemove;
    public Generator generator;
    public Rectangle safeZone;
    public Player player;
    public List<Rectangle> obstacles;
    public List<Zombie> zombies;

    private PlayerController controller;
    private Sprite map;

    World(TextureAtlas atlas) {
        this.atlas = atlas;
        this.map = atlas.createSprite("map");
        this.player = new Player(this, 220, 300);
        this.controller = new PlayerController(player);
        initZombies();
        initJerrycans();
        initObstacles();
        initSafeZone();
        initGenerator();
    }

    private void initGenerator() {
        generator = new Generator(atlas, 219, 325, 10, 6);
    }

    private void initJerrycans() {
        this.jerrycansToRemove = new ArrayList<>();
        this.jerrycans = new ArrayList<>();
        this.jerrycans.add(new Jerrycan(this, atlas, 220, 250));
    }

    private void initZombies() {
        this.zombies = new ArrayList<>();
        this.zombies.add(new Zombie(this, atlas, 100, 100));
        this.zombies.add(new Zombie(this, atlas, 200, 50));
        this.zombies.add(new Zombie(this, atlas, 400, 70));
        this.zombies.add(new Zombie(this, atlas, 100, 360));
        this.zombies.add(new Zombie(this, atlas, 130, 470));
        this.zombies.add(new Zombie(this, atlas, 400, 400));
        this.zombies.add(new Zombie(this, atlas, 445, 470));
        this.zombies.add(new Zombie(this, atlas, 300, 300));

        this.zombies.add(new Zombie(this, atlas, 300, 300));
        this.zombies.add(new Zombie(this, atlas, 300, 300));
        this.zombies.add(new Zombie(this, atlas, 300, 300));
        this.zombies.add(new Zombie(this, atlas, 300, 300));
        this.zombies.add(new Zombie(this, atlas, 300, 300));
        this.zombies.add(new Zombie(this, atlas, 300, 300));
        this.zombies.add(new Zombie(this, atlas, 300, 300));
        this.zombies.add(new Zombie(this, atlas, 300, 300));


    }

    private void initSafeZone() {
        safeZone = new Rectangle(176, 251, 97, 96);
    }

    private void initObstacles() {
        obstacles = new ArrayList<>();
        obstacles.add(new Rectangle(171, 250, 5, 98));
        obstacles.add(new Rectangle(175, 347, 100, 5));
        obstacles.add(new Rectangle(273, 250, 5, 98));
        obstacles.add(new Rectangle(175, 246, 42, 5));
        obstacles.add(new Rectangle(230, 246, 44, 5));
    }

    Player getPlayer() {
        return player;
    }

    void update() {
        generator.update();
        controller.update();
        player.update();
        zombies.forEach(Zombie::update);
        jerrycans.forEach(Jerrycan::update);

        computeCollisions();
        removeEntities();
    }

    private void removeEntities() {
        jerrycans.removeAll(jerrycansToRemove);
        jerrycansToRemove.clear();
    }

    void computeCollisions() {
        collideWithObstacles();
        collideZombies();
        collideWithZombies();
        collideWithJerryCans();
        collideWithGenerator();
    }

    private void collideWithGenerator() {
        if (player.sprite.getBoundingRectangle().overlaps(generator.boudingBox)) {
            player.collideWithGenerator(generator);
        }
    }

    private void collideWithJerryCans() {
        for (Jerrycan jerrycan : jerrycans) {
            if (jerrycan.sprite.getBoundingRectangle().overlaps(player.sprite.getBoundingRectangle())) {
                player.collideWithJerrycan(jerrycan);
            }
        }
    }

    void collideWithObstacles() {
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
    }

    void collideZombies() {
        zombies.forEach(
            zombie -> zombies
                .stream()
                .filter(z -> !z.equals(zombie))
                .filter(other -> zombie.sprite.getBoundingRectangle().overlaps(other.sprite.getBoundingRectangle()))
                .forEach(other -> zombie.collideWithZombie(other)));
    }

    void collideWithZombies() {
        zombies.stream()
            .filter(zombie -> safeZone.overlaps(zombie.sprite.getBoundingRectangle()))
            .forEach(zombie -> zombie.collideWithObstacle(safeZone));

        zombies.stream()
            .filter(zombie -> player.sprite.getBoundingRectangle().overlaps(zombie.sprite.getBoundingRectangle()))
            .forEach(zombie -> player.collideWithZombie(zombie));
    }

    void render(Batch batch) {
        map.draw(batch);
        generator.render(batch);
        jerrycans.forEach(jerrycan -> jerrycan.render(batch));
        zombies.forEach(zombie -> zombie.render(batch));
        player.render(batch);
    }

}
