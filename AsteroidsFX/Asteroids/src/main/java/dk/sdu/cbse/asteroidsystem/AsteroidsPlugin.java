package dk.sdu.mmmi.cbse.asteroidssystem;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.common.services.IGamePluginService;

import java.util.Random;

public class AsteroidsPlugin implements IGamePluginService {

    private final Random rnd = new Random();

    @Override
    public void start(GameData gameData, World world) {
        // spawn e.g. 8 asteroids
        for (int i = 0; i < 8; i++) {
            Entity a = new Entity();
            a.tag = "asteroid";
            a.alive = true;

            a.radius = 20 + rnd.nextInt(25); // size 20..44
            a.x = rnd.nextDouble() * gameData.width;
            a.y = rnd.nextDouble() * gameData.height;

            // random velocity
            a.vx = rnd.nextDouble() * 120 - 60; // -60..60
            a.vy = rnd.nextDouble() * 120 - 60;

            // optional random rotation (not used unless you render rotation)
            a.rotation = rnd.nextDouble() * 360;

            world.add(a);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities().stream().toList()) {
            if ("asteroid".equals(e.tag)) {
                world.remove(e);
            }
        }
    }
}