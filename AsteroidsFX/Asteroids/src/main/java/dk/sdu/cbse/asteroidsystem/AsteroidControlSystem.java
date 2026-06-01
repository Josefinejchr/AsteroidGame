package dk.sdu.cbse.asteroidsystem;

import dk.sdu.cbse.common.asteroid.Asteroid;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

public class AsteroidControlSystem implements IEntityProcessingService {

    private static final double SPEED = 1.5;

    @Override
    public void process(GameData gameData, World world) {
        for (Asteroid asteroid : world.getEntities(Asteroid.class)) {
            double radians = Math.toRadians(asteroid.getRotation());
            asteroid.setX(asteroid.getX() + Math.cos(radians) * SPEED);
            asteroid.setY(asteroid.getY() + Math.sin(radians) * SPEED);
            wrapAround(asteroid, gameData);
        }
    }

    private void wrapAround(Asteroid asteroid, GameData gameData) {
        if (asteroid.getX() < 0) asteroid.setX(gameData.getDisplayWidth());
        if (asteroid.getX() > gameData.getDisplayWidth()) asteroid.setX(0);
        if (asteroid.getY() < 0) asteroid.setY(gameData.getDisplayHeight());
        if (asteroid.getY() > gameData.getDisplayHeight()) asteroid.setY(0);
    }
}