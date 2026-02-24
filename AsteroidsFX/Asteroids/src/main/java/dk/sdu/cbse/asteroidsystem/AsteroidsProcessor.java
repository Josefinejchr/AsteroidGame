package dk.sdu.cbse.asteroidsystem;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.common.services.IEntityProcessorService;

public class AsteroidsProcessor implements IEntityProcessorService {

    @Override
    public void process(GameData gameData, World world) {
        double dt = gameData.delta;

        for (Entity a : world.getEntities()) {
            if (!"asteroid".equals(a.tag) || !a.alive) continue;

            a.x += a.vx * dt;
            a.y += a.vy * dt;

            // wrap
            if (a.x < 0) a.x += gameData.width;
            if (a.x > gameData.width) a.x -= gameData.width;
            if (a.y < 0) a.y += gameData.height;
            if (a.y > gameData.height) a.y -= gameData.height;
        }
    }
}