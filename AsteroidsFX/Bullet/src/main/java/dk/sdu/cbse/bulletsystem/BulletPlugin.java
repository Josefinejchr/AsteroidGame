package dk.sdu.cbse.bulletsystem;

import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService, BulletSPI {
    @Override
    public void start(GameData gameData, World world) {
        //Nothing to spawn at start because bullets are created dynamically.
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Bullet.class).forEach(world::removeEntity);
    }

    public Bullet createBullet(Entity shooter, GameData gameData) {
        Bullet bullet = new Bullet();
        double radians = Math.toRadians(shooter.getRotation());
        bullet.setX(shooter.getX() + Math.cos(radians) * (shooter.getRadius() + 5));
        bullet.setY(shooter.getY() + Math.sin(radians) * (shooter.getRadius() + 5));
        bullet.setRotation(shooter.getRotation());
        bullet.setRadius(3);
        bullet.setPolygonCoordinates(-3, -1, 3, -1, 3, 1, -3, 1);
        bullet.setLifetime(120);
        return bullet;
    }
}