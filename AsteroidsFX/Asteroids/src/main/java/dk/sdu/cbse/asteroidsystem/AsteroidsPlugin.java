package dk.sdu.cbse.asteroidsystem;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.common.services.IGamePluginService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsteroidsPlugin implements IGamePluginService {

    private final List<Entity> spawned = new ArrayList<>();
    private final Random rnd = new Random();

    @Override
    public void start(GameData gameData, World world) {
        // spawn fx 6 asteroids
        for (int i = 0; i < 6; i++) {
            Entity a = new Entity();
            a.tag = "asteroid";
            a.alive = true;

            a.x = rnd.nextDouble() * gameData.width;
            a.y = rnd.nextDouble() * gameData.height;

            a.radius = 22 + rnd.nextDouble() * 18; // 22..40

            // random drift
            a.vx = rnd.nextDouble() * 140 - 70; // -70..70
            a.vy = rnd.nextDouble() * 140 - 70;

            a.rotation = rnd.nextDouble() * 360;

            world.add(a);
            spawned.add(a);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity a : spawned) {
            world.remove(a);
        }
        spawned.clear();
    }
}