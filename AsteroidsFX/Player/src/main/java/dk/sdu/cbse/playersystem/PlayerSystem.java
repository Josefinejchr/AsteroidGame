package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
public class PlayerSystem {
    public void update(GameData game, World world) {
        for (Player p : world.getEntities(Player.class)) {
            if (game.keysDown.contains("LEFT"))  p.rotation -= 200 * game.delta;
            if (game.keysDown.contains("RIGHT")) p.rotation += 200 * game.delta;

            if (game.keysDown.contains("UP")) {
                double dx = Math.cos(Math.toRadians(p.rotation));
                double dy = Math.sin(Math.toRadians(p.rotation));
                p.vx += dx * 260 * game.delta;
                p.vy += dy * 260 * game.delta;
            }

            // small friction
            p.vx *= 0.995;
            p.vy *= 0.995;

            p.shootCooldown = Math.max(0, p.shootCooldown - game.delta);
        }
    }
}
