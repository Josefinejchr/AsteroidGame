package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.common.services.IEntityProcessorService;

import java.util.ArrayList;
import java.util.List;

public class PlayerProcessor implements IEntityProcessorService {

    private static final double ROTATE_SPEED = 180; // degrees/sec
    private static final double ACCEL = 200;        // px/sec^2
    private static final double DAMPING = 0.99;

    @Override
    public void process(GameData gameData, World world) {
        double dt = gameData.delta;

        List<Entity> bulletsToAdd = new ArrayList<>();

        for (Player p : world.getEntities(Player.class)) {
            if (!p.alive) continue;

            // cooldown ticks down
            if (p.shootCooldown > 0) p.shootCooldown -= dt;

            boolean left  = gameData.keysDown.contains("LEFT");
            boolean right = gameData.keysDown.contains("RIGHT");
            boolean up    = gameData.keysDown.contains("UP");

            if (left)  p.rotation -= ROTATE_SPEED * dt;
            if (right) p.rotation += ROTATE_SPEED * dt;

            if (up) {
                double rad = Math.toRadians(p.rotation);
                p.vx += Math.cos(rad) * ACCEL * dt;
                p.vy += Math.sin(rad) * ACCEL * dt;
            }

            // ---- Shooting (hold SPACE to fire every 0.3s) ----
            boolean spaceDown = gameData.keysDown.contains("SPACE");
            if (spaceDown && p.shootCooldown <= 0) {
                bulletsToAdd.add(createBulletFrom(p, 380));
                p.shootCooldown = 0.2;
            }

            // ---- Move ----
            p.x += p.vx * dt;
            p.y += p.vy * dt;

            p.vx *= DAMPING;
            p.vy *= DAMPING;

            // ---- Wrap ----
            double w = gameData.width;
            double h = gameData.height;

            if (p.x < 0) p.x += w;
            if (p.x > w) p.x -= w;
            if (p.y < 0) p.y += h;
            if (p.y > h) p.y -= h;
        }

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