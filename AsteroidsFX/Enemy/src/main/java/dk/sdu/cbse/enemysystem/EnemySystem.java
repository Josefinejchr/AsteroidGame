package dk.sdu.cbse.enemysystem;

import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;

import java.util.Random;
public class EnemySystem {
    private final Random rnd = new Random();
    public void update(GameData game, World world) {
        for (Enemy e : world.getEntities(Enemy.class)) {

            e.changeDirTimer -= game.delta;
            if (e.changeDirTimer <= 0) {
                e.rotation = rnd.nextInt(360);

                double speed = 70 + rnd.nextDouble() * 130;
                e.vx = Math.cos(Math.toRadians(e.rotation)) * speed;
                e.vy = Math.sin(Math.toRadians(e.rotation)) * speed;

                e.changeDirTimer = 0.4 + rnd.nextDouble() * 1.2;
            }

            e.shootTimer = Math.max(0, e.shootTimer - game.delta);
        }
    }
}
