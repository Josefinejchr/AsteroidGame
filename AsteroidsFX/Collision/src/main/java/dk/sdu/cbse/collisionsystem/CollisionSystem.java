package dk.sdu.cbse.collisionsystem;

import dk.sdu.cbse.common.asteroid.Asteroid;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.enemy.Enemy;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.common.data.Destructible;

public class CollisionSystem implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity a : world.getEntities()) {
            for (Entity b : world.getEntities()) {
                if (a == b) continue;
                if (!a.isActive() || !b.isActive()) continue;
                if (collides(a, b)) {
                    handleCollision(a, b, gameData, world);
                }
            }
        }
    }

    private boolean collides(Entity a, Entity b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (a.getRadius() + b.getRadius());
    }

    private void handleCollision(Entity a, Entity b, GameData gameData, World world) {
        // bullet hits asteroid
        if (a instanceof Bullet && b instanceof Asteroid) {
            a.setActive(false);
            splitOrDestroy((Asteroid) b, gameData, world);
            gameData.addScore(10);
        }
        // bullet hits destructible (enemy or player)
        if (a instanceof Bullet && b instanceof Destructible) {
            a.setActive(false);
            Destructible target = (Destructible) b;
            target.setHealth(target.getHealth() - 1);
            if (target.getHealth() <= 0) {
                ((Entity) target).setActive(false);
                gameData.addScore(50);
            }
        }
        // asteroid hits destructible (enemy or player)
        if (a instanceof Asteroid && b instanceof Destructible) {
            Destructible target = (Destructible) b;
            target.setHealth(target.getHealth() - 1);
            if (target.getHealth() <= 0) {
                ((Entity) target).setActive(false);
            }
        }
    }

    private void splitOrDestroy(Asteroid asteroid, GameData gameData, World world) {
        asteroid.setActive(false);
        if (asteroid.getSize() <= 1) return;

        for (int i = 0; i < 2; i++) {
            Asteroid fragment = new Asteroid();
            fragment.setSize(asteroid.getSize() - 1);
            double radius = asteroid.getRadius() * 0.6;
            fragment.setRadius(radius);
            fragment.setX(asteroid.getX() + (Math.random() * 20 - 10));
            fragment.setY(asteroid.getY() + (Math.random() * 20 - 10));
            fragment.setRotation(Math.random() * 360);
            fragment.setPolygonCoordinates(
                    -radius * 0.6, -radius * 0.6,
                    0, -radius,
                    radius * 0.6, -radius * 0.6,
                    radius, 0,
                    radius * 0.6, radius * 0.6,
                    0, radius,
                    -radius * 0.6, radius * 0.6,
                    -radius, 0
            );
            world.addEntity(fragment);
        }
    }
}