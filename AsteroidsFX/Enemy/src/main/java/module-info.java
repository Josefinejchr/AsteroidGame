module Enemy {
    requires Common;

    exports dk.sdu.cbse.enemysystem;

    provides dk.sdu.cbse.common.services.IGamePluginService
            with dk.sdu.cbse.enemysystem.EnemyPlugin;

    provides dk.sdu.cbse.common.services.IEntityProcessorService
            with dk.sdu.cbse.enemysystem.EnemyProcessor;
}