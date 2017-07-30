package com.pmilian.ld;

import com.badlogic.gdx.math.Vector2;
import com.pmilian.ld.entities.Zombie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static com.pmilian.ld.World.HEIGHT;
import static com.pmilian.ld.World.WIDTH;

public class ZombieSpawner {

    private World world;

    private List<Vector2> spawnPoints;
    private Random random = new Random();

    public ZombieSpawner(World world) {
        this.world = world;
        this.spawnPoints = new ArrayList<>();
        this.spawnPoints.add(new Vector2(75, 60));
        this.spawnPoints.add(new Vector2(50, 250));
        this.spawnPoints.add(new Vector2(80, 450));
        this.spawnPoints.add(new Vector2(250, 60));
        this.spawnPoints.add(new Vector2(400, 100));
        this.spawnPoints.add(new Vector2(430, 270));
    }

    public void initZombies(int zombieCount) {
        IntStream.range(0, zombieCount).forEach(value -> {
            while (true) {
                Zombie zombie = new Zombie(world, world.atlas, random.nextInt(WIDTH), random.nextInt(HEIGHT));
                if (isZombiePositionValid(zombie)) {
                    world.zombies.add(zombie);
                    break;
                }
            }
        });
    }


    public void spawnZombie() {
        while (true) {
            Vector2 spawnPoint = spawnPoints.get(random.nextInt(spawnPoints.size()));

            Zombie zombie = new Zombie(world, world.atlas, spawnPoint.x, spawnPoint.y);
            if (isZombiePositionValid(zombie)) {
                world.zombies.add(zombie);
                return;
            }
        }
    }

    private boolean isZombiePositionValid(Zombie zombie) {
        return !isInSafeZone(zombie) && notOnCar(zombie) && notOnObstacle(zombie);
    }

    private boolean isInSafeZone(Zombie zombie) {
        return world.safeZone.overlaps(zombie.sprite.getBoundingRectangle());
    }

    private boolean notOnCar(Zombie zombie) {
        return world.cars.stream().noneMatch(car -> car.sprite.getBoundingRectangle().overlaps(zombie.sprite.getBoundingRectangle()));
    }

    private boolean notOnObstacle(Zombie zombie) {
        return world.obstacles.stream().noneMatch(obstacle -> obstacle.overlaps(zombie.sprite.getBoundingRectangle()));
    }
}
