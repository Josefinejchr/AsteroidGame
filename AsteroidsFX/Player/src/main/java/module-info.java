module Player {
    requires Common;

    exports dk.sdu.cbse.playersystem;

    provides dk.sdu.cbse.common.services.IGamePluginService
            with dk.sdu.cbse.playersystem.PlayerPlugin;

    provides dk.sdu.cbse.common.services.IEntityProcessorService
            with dk.sdu.cbse.playersystem.PlayerProcessor;
}