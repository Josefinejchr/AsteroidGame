package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.common.services.IEntityProcessorService;

public class PlayerProcessor implements IEntityProcessorService {

    private static final double ROTATE_SPEED = 180; // degrees/sec
    private static final double ACCEL = 200;        // px/sec^2
    private static final double DAMPING = 0.99;

    @Override
    public void process(GameData gameData, World world) {
        double dt = gameData.delta;

        for (Entity e : world.getEntities()) {
            if (!"player".equals(e.tag) || !e.alive) continue;

            boolean left  = gameData.keysDown.contains("LEFT");
            boolean right = gameData.keysDown.contains("RIGHT");
            boolean up    = gameData.keysDown.contains("UP");

            if (left)  e.rotation -= ROTATE_SPEED * dt;
            if (right) e.rotation += ROTATE_SPEED * dt;

            if (up) {
                double rad = Math.toRadians(e.rotation);
                e.vx += Math.cos(rad) * ACCEL * dt;
                e.vy += Math.sin(rad) * ACCEL * dt;
            }

            e.x += e.vx * dt;
            e.y += e.vy * dt;

            e.vx *= DAMPING;
            e.vy *= DAMPING;

            // wrap-around
            double w = gameData.width;
            double h = gameData.height;

            if (e.x < 0) e.x += w;
            if (e.x > w) e.x -= w;
            if (e.y < 0) e.y += h;
            if (e.y > h) e.y -= h;
        }
    }
}