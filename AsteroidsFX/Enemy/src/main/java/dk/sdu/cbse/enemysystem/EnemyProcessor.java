package dk.sdu.cbse.enemysystem;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.common.services.IEntityProcessorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyProcessor implements IEntityProcessorService {

    private final Random rnd = new Random();

    @Override
    public void process(GameData gameData, World world) {
        double dt = gameData.delta;

        List<Entity> bulletsToAdd = new ArrayList<>();

        for (Entity e : world.getEntities()) {
            if (!"enemy".equals(e.tag) || !e.alive) continue;

            // Simple wander
            e.rotation += (rnd.nextDouble() * 80 - 40) * dt;

            if (rnd.nextDouble() < 0.02) {
                e.vx = rnd.nextDouble() * 140 - 70;
                e.vy = rnd.nextDouble() * 140 - 70;
            }

            // Move
            e.x += e.vx * dt;
            e.y += e.vy * dt;

            // Wrap
            if (e.x < 0) e.x += gameData.width;
            if (e.x > gameData.width) e.x -= gameData.width;
            if (e.y < 0) e.y += gameData.height;
            if (e.y > gameData.height) e.y -= gameData.height;

            // Random shooting (queue bullet, don’t add directly)
            if (rnd.nextDouble() < 0.02) {
                bulletsToAdd.add(createBulletFrom(e, 360));
            }
        }

        // Add bullets after iteration
        for (Entity b : bulletsToAdd) {
            world.add(b);
        }
    }

    private Entity createBulletFrom(Entity shooter, double speed) {
        Entity b = new Entity();
        b.tag = "bullet";
        b.alive = true;
        b.radius = 3;

        double dx = Math.cos(Math.toRadians(shooter.rotation));
        double dy = Math.sin(Math.toRadians(shooter.rotation));

        b.x = shooter.x + dx * (shooter.radius + 6);
        b.y = shooter.y + dy * (shooter.radius + 6);

        b.vx = shooter.vx + dx * speed;
        b.vy = shooter.vy + dy * speed;

        b.rotation = shooter.rotation;
        return b;
    }
}