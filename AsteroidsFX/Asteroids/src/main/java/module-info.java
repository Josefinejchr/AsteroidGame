module Asteroids {
    requires Common;

    exports dk.sdu.cbse.asteroidsystem;

    provides dk.sdu.cbse.common.services.IGamePluginService
            with dk.sdu.cbse.asteroidsystem.AsteroidsPlugin;

    provides dk.sdu.cbse.common.services.IEntityProcessorService
            with dk.sdu.cbse.asteroidsystem.AsteroidsProcessor;
}