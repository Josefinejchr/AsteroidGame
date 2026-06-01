package dk.sdu.cbse.enemysystem;

import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.enemy.Enemy;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

import java.util.Random;
import java.util.ServiceLoader;

public class EnemyControlSystem implements IEntityProcessingService {

    private static final double SPEED = 1.5;
    private final Random random = new Random();
    private int shootCooldown = 0;

    @Override
    public void process(GameData gameData, World world) {
        for (Enemy enemy : world.getEntities(Enemy.class)) {
            moveEnemy(enemy, gameData);
            handleShooting(enemy, gameData, world);
        }
        if (shootCooldown > 0) shootCooldown--;
    }

    private void moveEnemy(Enemy enemy, GameData gameData) {
        if (random.nextInt(60) == 0) {
            enemy.setRotation(enemy.getRotation() + random.nextInt(90) - 45);
        }
        double radians = Math.toRadians(enemy.getRotation());
        enemy.setX(enemy.getX() + Math.cos(radians) * SPEED);
        enemy.setY(enemy.getY() + Math.sin(radians) * SPEED);
        if (enemy.getX() < 0) enemy.setX(gameData.getDisplayWidth());
        if (enemy.getX() > gameData.getDisplayWidth()) enemy.setX(0);
        if (enemy.getY() < 0) enemy.setY(gameData.getDisplayHeight());
        if (enemy.getY() > gameData.getDisplayHeight()) enemy.setY(0);
    }

    private void handleShooting(Enemy enemy, GameData gameData, World world) {
        if (shootCooldown <= 0 && random.nextInt(120) == 0) {
            ServiceLoader.load(BulletSPI.class)
                    .stream()
                    .map(ServiceLoader.Provider::get)
                    .findFirst()
                    .ifPresent(spi -> world.addEntity(spi.createBullet(enemy, gameData)));
            shootCooldown = 60;
        }
    }
}