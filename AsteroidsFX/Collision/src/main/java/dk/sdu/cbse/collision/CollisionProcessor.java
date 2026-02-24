package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.common.services.IPostEntityProcessorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollisionProcessor implements IPostEntityProcessorService {

    private static final double SPLIT_FACTOR = 0.6;   // new radius = old * 0.6
    private static final double MIN_ASTEROID_RADIUS = 12;
    private static final double SPLIT_PUSH_SPEED = 120;

    private final Random rnd = new Random();

    @Override
    public void process(GameData gameData, World world) {

        List<Entity> toAdd = new ArrayList<>();

        List<Entity> entities = world.getEntities();

        // Pairwise collisions (small N game -> simple O(n^2) is fine)
        for (int i = 0; i < entities.size(); i++) {
            Entity a = entities.get(i);
            if (!a.alive) continue;

            for (int j = i + 1; j < entities.size(); j++) {
                Entity b = entities.get(j);
                if (!b.alive) continue;

                if (!collides(a, b)) continue;

                // Bullet vs Asteroid
                if (isBullet(a) && isAsteroid(b)) {
                    a.alive = false;
                    splitAsteroid(b, toAdd);
                    continue;
                }
                if (isAsteroid(a) && isBullet(b)) {
                    b.alive = false;
                    splitAsteroid(a, toAdd);
                    continue;
                }

                // Bullet vs Enemy
                if (isBullet(a) && isEnemy(b)) { a.alive = false; b.alive = false; continue; }
                if (isEnemy(a) && isBullet(b)) { a.alive = false; b.alive = false; continue; }

                // Bullet vs Player
                if (isBullet(a) && isPlayer(b)) { a.alive = false; b.alive = false; continue; }
                if (isPlayer(a) && isBullet(b)) { a.alive = false; b.alive = false; continue; }

                // Ship vs Asteroid (ship dies)
                if (isShip(a) && isAsteroid(b)) { a.alive = false; continue; }
                if (isAsteroid(a) && isShip(b)) { b.alive = false; continue; }
            }
        }

        // add spawned asteroids after iteration
        for (Entity e : toAdd) {
            world.add(e);
        }
    }

    private boolean collides(Entity a, Entity b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        double r = a.radius + b.radius;
        return dx * dx + dy * dy <= r * r;
    }

    private boolean isBullet(Entity e)   { return "bullet".equals(e.tag); }
    private boolean isAsteroid(Entity e) { return "asteroid".equals(e.tag); }
    private boolean isPlayer(Entity e)   { return "player".equals(e.tag); }
    private boolean isEnemy(Entity e)    { return "enemy".equals(e.tag); }
    private boolean isShip(Entity e)     { return isPlayer(e) || isEnemy(e); }

    private void splitAsteroid(Entity asteroid, List<Entity> toAdd) {
        double newRadius = asteroid.radius * SPLIT_FACTOR;

        // destroy old asteroid
        asteroid.alive = false;

        // if too small -> just die
        if (newRadius < MIN_ASTEROID_RADIUS) return;

        // create 2 new asteroids
        Entity a1 = makeAsteroidChild(asteroid, newRadius, +1);
        Entity a2 = makeAsteroidChild(asteroid, newRadius, -1);

        toAdd.add(a1);
        toAdd.add(a2);
    }

    private Entity makeAsteroidChild(Entity parent, double radius, int dir) {
        Entity a = new Entity();
        a.tag = "asteroid";
        a.alive = true;

        a.x = parent.x;
        a.y = parent.y;
        a.radius = radius;

        // random-ish split directions
        double angle = rnd.nextDouble() * Math.PI * 2;
        double pushX = Math.cos(angle) * SPLIT_PUSH_SPEED * dir;
        double pushY = Math.sin(angle) * SPLIT_PUSH_SPEED * dir;

        a.vx = parent.vx + pushX;
        a.vy = parent.vy + pushY;

        a.rotation = rnd.nextDouble() * 360;

        return a;
    }
}