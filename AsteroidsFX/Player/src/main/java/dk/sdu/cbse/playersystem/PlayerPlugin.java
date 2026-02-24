package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    @Override
    public void start(GameData gameData, World world) {
        player = new Player();
        player.x = gameData.width / 2.0;
        player.y = gameData.height / 2.0;

        player.rotation = -90;
        player.radius = 10;
        player.vx = 0;
        player.vy = 0;
        player.alive = true;
        player.tag = "player";

        world.add(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        if (player != null) {
            world.remove(player);
            player = null;
        }
    }
}