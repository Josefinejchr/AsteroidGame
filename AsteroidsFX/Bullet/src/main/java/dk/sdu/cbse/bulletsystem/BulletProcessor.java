package dk.sdu.cbse.bulletsystem;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.common.services.IEntityProcessorService;

public class BulletProcessor implements IEntityProcessorService {

    @Override
    public void process(GameData gameData, World world) {
        double dt = gameData.delta;

        for (Entity e : world.getEntities()) {
            if (!"bullet".equals(e.tag) || !e.alive) continue;

            // move
            e.x += e.vx * dt;
            e.y += e.vy * dt;

            // kill at edges
            if (e.x < 0 || e.x > gameData.width ||
                    e.y < 0 || e.y > gameData.height) {
                e.alive = false;
            }
        }
    }
}