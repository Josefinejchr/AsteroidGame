module Player {
    requires Common;
    requires CommonBullet;

    uses dk.sdu.cbse.common.bullet.BulletSPI;

    //registers classes with serviceLoader.
    provides dk.sdu.cbse.common.services.IGamePluginService
            with dk.sdu.cbse.playersystem.PlayerPlugin;

    provides dk.sdu.cbse.common.services.IEntityProcessingService
            with dk.sdu.cbse.playersystem.PlayerControlSystem;
}