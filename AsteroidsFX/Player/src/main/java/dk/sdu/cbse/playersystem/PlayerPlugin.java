package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {

    private Entity player;
    @Override
    public void start(GameData gameData, World world) {
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        if (player != null) {
            world.removeEntity(player);
        }
    }

    private Entity createPlayer(GameData gameData) {
        Player player = new Player();
        player.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        player.setX(gameData.getDisplayWidth() / 2.0);
        player.setY(gameData.getDisplayHeight() / 2.0);
        player.setRadius(8);
        return player;
    }
}