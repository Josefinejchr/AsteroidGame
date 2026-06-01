package dk.sdu.cbse.enemysystem;

import dk.sdu.cbse.common.enemy.Enemy;
import dk.sdu.cbse.common.enemy.EnemySPI;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService, EnemySPI {

    @Override
    public void start(GameData gameData, World world) {
        createEnemy(gameData, world);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Enemy.class).forEach(world::removeEntity);
    }

    @Override
    public void createEnemy(GameData gameData, World world) {
        Enemy enemy = new Enemy();
        enemy.setPolygonCoordinates(-8, -5, 8, 0, -8, 5);
        enemy.setX(Math.random() * gameData.getDisplayWidth());
        enemy.setY(Math.random() * gameData.getDisplayHeight());
        enemy.setRotation(Math.random() * 360);
        enemy.setRadius(8);
        world.addEntity(enemy);
    }
}