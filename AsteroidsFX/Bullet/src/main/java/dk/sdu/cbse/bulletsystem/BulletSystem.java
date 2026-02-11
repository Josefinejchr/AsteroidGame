package dk.sdu.cbse.bulletsystem;

import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
public class BulletSystem {
    public void update(GameData game, World world) {
        for (Bullet b : world.getEntities(Bullet.class)) {
            b.life -= game.delta;
            if (b.life <= 0) b.alive = false;
        }
    }
}
