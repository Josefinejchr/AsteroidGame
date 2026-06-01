package dk.sdu.cbse.bulletsystem;

import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService {

    private static final double BULLET_SPEED = 6;

    @Override
    public void process(GameData gameData, World world) {
        for (Bullet bullet : world.getEntities(Bullet.class)) {
            double radians = Math.toRadians(bullet.getRotation());
            bullet.setX(bullet.getX() + Math.cos(radians) * BULLET_SPEED);
            bullet.setY(bullet.getY() + Math.sin(radians) * BULLET_SPEED);
            bullet.setLifetime(bullet.getLifetime() - 1);
            if (bullet.getLifetime() <= 0) {
                bullet.setActive(false);
            }
        }
    }
}