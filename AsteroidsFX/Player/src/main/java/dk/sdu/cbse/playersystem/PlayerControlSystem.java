package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameKeys;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

import java.util.ServiceLoader;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Player player : world.getEntities(Player.class)) {
            handleRotation(player, gameData);
            handleMovement(player, gameData);
            handleShooting(player, gameData, world);
            wrapAround(player, gameData);
        }
    }

    private void handleRotation(Player player, GameData gameData) {
        if (gameData.getKeys().isDown(GameKeys.LEFT)) {
            player.setRotation(player.getRotation() - 5);
        }
        if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
            player.setRotation(player.getRotation() + 5);
        }
    }

    private void handleMovement(Player player, GameData gameData) {
        if (gameData.getKeys().isDown(GameKeys.UP)) {
            double radians = Math.toRadians(player.getRotation());
            player.setX(player.getX() + Math.cos(radians) * 3);
            player.setY(player.getY() + Math.sin(radians) * 3);
        }
    }

    //uses isPressed. fires one bullet at a time
    private void handleShooting(Player player, GameData gameData, World world) {
        if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
            ServiceLoader.load(BulletSPI.class)
                    .stream()
                    .map(ServiceLoader.Provider::get)
                    .findFirst()
                    .ifPresent(spi -> world.addEntity(spi.createBullet(player, gameData)));
        }
    }

    //If player flies off to one side and reappers on the other.
    private void wrapAround(Player player, GameData gameData) {
        if (player.getX() < 0) player.setX(gameData.getDisplayWidth());
        if (player.getX() > gameData.getDisplayWidth()) player.setX(0);
        if (player.getY() < 0) player.setY(gameData.getDisplayHeight());
        if (player.getY() > gameData.getDisplayHeight()) player.setY(0);
    }
}