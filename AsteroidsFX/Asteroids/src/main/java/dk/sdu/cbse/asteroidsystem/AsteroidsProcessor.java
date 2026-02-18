package dk.sdu.mmmi.cbse.asteroidssystem;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.common.services.IEntityProcessorService;

public class AsteroidsProcessor implements IEntityProcessorService {

    @Override
    public void process(GameData gameData, World world) {
        double dt = gameData.delta;
        double w = gameData.width;
        double h = gameData.height;

        for (Entity e : world.getEntities()) {
            if (!"asteroid".equals(e.tag) || !e.alive) continue;

            e.x += e.vx * dt;
            e.y += e.vy * dt;

            // wrap-around
            if (e.x < 0) e.x += w;
            if (e.x > w) e.x -= w;
            if (e.y < 0) e.y += h;
            if (e.y > h) e.y -= h;
        }
    }
}