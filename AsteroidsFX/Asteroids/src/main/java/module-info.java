module Asteroids {
    requires Common;
    requires CommonAsteroids;

    provides dk.sdu.cbse.common.services.IGamePluginService
            with dk.sdu.cbse.asteroidsystem.AsteroidPlugin;
    provides dk.sdu.cbse.common.services.IEntityProcessingService
            with dk.sdu.cbse.asteroidsystem.AsteroidControlSystem;
    provides dk.sdu.cbse.common.asteroid.AsteroidSPI
            with dk.sdu.cbse.asteroidsystem.AsteroidPlugin;
}