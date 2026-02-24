package dk.sdu.cbse.enemysystem;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.common.services.IGamePluginService;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;
    private final Random rnd = new Random();

    @Override
    public void start(GameData gameData, World world) {
        enemy = new Entity();
        enemy.tag = "enemy";
        enemy.alive = true;

        enemy.x = rnd.nextDouble() * gameData.width;
        enemy.y = rnd.nextDouble() * gameData.height;
        enemy.rotation = rnd.nextDouble() * 360;

        enemy.radius = 12;
        enemy.vx = rnd.nextDouble() * 120 - 60;
        enemy.vy = rnd.nextDouble() * 120 - 60;

        world.add(enemy);
    }

    @Override
    public void stop(GameData gameData, World world) {
        if (enemy != null) {
            world.remove(enemy);
            enemy = null;
        }
    }
}