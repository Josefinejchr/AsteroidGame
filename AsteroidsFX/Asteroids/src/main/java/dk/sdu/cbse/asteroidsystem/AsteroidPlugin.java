package dk.sdu.cbse.asteroidsystem;

import dk.sdu.cbse.common.asteroid.Asteroid;
import dk.sdu.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

public class AsteroidPlugin implements IGamePluginService, AsteroidSPI {

    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < 5; i++) {
            createAsteroid(gameData, world);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Asteroid.class).forEach(world::removeEntity);
    }

    @Override
    public void createAsteroid(GameData gameData, World world) {
        Asteroid asteroid = new Asteroid();
        asteroid.setSize(3);
        asteroid.setRadius(30);
        asteroid.setPolygonCoordinates(-20, -20, 0, -30, 20, -20, 30, 0, 20, 20, 0, 30, -20, 20, -30, 0);
        asteroid.setX(Math.random() * gameData.getDisplayWidth());
        asteroid.setY(Math.random() * gameData.getDisplayHeight());
        asteroid.setRotation(Math.random() * 360);
        world.addEntity(asteroid);
    }
}