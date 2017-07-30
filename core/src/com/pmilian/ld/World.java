package com.pmilian.ld;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.pmilian.ld.controller.PlayerController;
import com.pmilian.ld.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

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
    public List<Zombie> zombiesToRemove;
    public List<Car> cars;

    private PlayerController controller;
    private Sprite map;
    private Tv tv;

    private Random random = new Random();

    World(TextureAtlas atlas) {
        this.atlas = atlas;
        this.map = atlas.createSprite("map");
        this.player = new Player(this, 220, 300);
        this.controller = new PlayerController(player);
        this.tv = new Tv(atlas, 229, 322);
        initSafeZone();
        initZombies();
        initJerrycans();
        initGenerator();
        initCars();
        initObstacles();
    }

    private void initCars() {
        cars = new ArrayList<>();
        Rectangle rectangle = new Rectangle(100, 100, 330, 320);
        IntStream.range(0, 4).forEach(value -> {
            while (true) {
                Car car = new Car(this, atlas, random.nextInt(WIDTH), random.nextInt(HEIGHT));
                if (!rectangle.overlaps(car.sprite.getBoundingRectangle())) {
                    cars.add(car);
                    break;
                }
            }
        });
    }

    private void initGenerator() {
        generator = new Generator(atlas, 219, 325, 10, 6);
    }

    private void initJerrycans() {
        jerrycansToRemove = new ArrayList<>();
        jerrycans = new ArrayList<>();
        jerrycans.add(new Jerrycan(this, atlas, 220, 270));

        IntStream.range(0, 12).forEach(value -> {
            while (true) {
                Jerrycan jerrycan = new Jerrycan(this, atlas, random.nextInt(WIDTH), random.nextInt(HEIGHT));
                if (!safeZone.overlaps(jerrycan.sprite.getBoundingRectangle())) {
                    jerrycans.add(jerrycan);
                    break;
                }
            }
        });
    }

    private void initZombies() {
        zombies = new ArrayList<>();
        zombiesToRemove = new ArrayList<>();
        IntStream.range(0, 40).forEach(value -> {
            while (true) {
                Zombie zombie = new Zombie(this, atlas, random.nextInt(WIDTH), random.nextInt(HEIGHT));
                if (!safeZone.overlaps(zombie.sprite.getBoundingRectangle())) {
                    zombies.add(zombie);
                    break;
                }
            }
        });

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

        cars.forEach(car -> obstacles.add(new Rectangle(car.sprite.getBoundingRectangle())));
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
        cars.forEach(Car::update);

        computeCollisions();
        removeEntities();
    }

    private void removeEntities() {
        jerrycans.removeAll(jerrycansToRemove);
        jerrycansToRemove.clear();
        zombies.removeAll(zombiesToRemove);
        zombiesToRemove.clear();
    }

    void computeCollisions() {
        collideZombies();
        collideWithZombies();
        collideWithJerryCans();
        collideWithCars();
        collideWithGenerator();
        collideWithObstacles();
    }

    private void collideWithCars() {
        cars.stream()
            .filter(car -> car.sprite.getBoundingRectangle().overlaps(player.sprite.getBoundingRectangle()))
            .forEach(car -> player.collideWithCar(car));
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
        obstacles.stream()
            .filter(obstacle -> player.sprite.getBoundingRectangle().overlaps(obstacle))
            .forEach(obstacle -> player.collideWithObstacle(obstacle));

        obstacles.forEach(obstacle ->
            zombies.stream()
                .filter(zombie -> zombie.sprite.getBoundingRectangle().overlaps(obstacle))
                .forEach(zombie -> zombie.collideWithObstacle(obstacle))
        );
    }

    void collideZombies() {
        zombies.forEach(
            zombie -> zombies
                .stream()
                .filter(z -> !z.equals(zombie))
                .filter(other -> zombie.sprite.getBoundingRectangle().overlaps(other.sprite.getBoundingRectangle()))
                .forEach(zombie::collideWithZombie));
    }

    void collideWithZombies() {
        zombies.stream()
            .filter(zombie -> safeZone.overlaps(zombie.sprite.getBoundingRectangle()))
            .forEach(Zombie::collideWithSafeZone);

        zombies.stream()
            .filter(zombie -> player.sprite.getBoundingRectangle().overlaps(zombie.sprite.getBoundingRectangle()))
            .forEach(zombie -> player.collideWithZombie(zombie));
    }

    void render(Batch batch) {
        map.draw(batch);
        tv.render(batch);
        generator.render(batch);
        cars.forEach(car -> car.render(batch));
        jerrycans.forEach(jerrycan -> jerrycan.render(batch));
        zombies.forEach(zombie -> zombie.render(batch));
        player.render(batch);
    }

}
