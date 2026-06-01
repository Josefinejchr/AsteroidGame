module Enemy {
    requires Common;
    requires CommonBullet;
    requires CommonEnemy;

    uses dk.sdu.cbse.common.bullet.BulletSPI;

    provides dk.sdu.cbse.common.services.IGamePluginService
            with dk.sdu.cbse.enemysystem.EnemyPlugin;
    provides dk.sdu.cbse.common.services.IEntityProcessingService
            with dk.sdu.cbse.enemysystem.EnemyControlSystem;
    provides dk.sdu.cbse.common.enemy.EnemySPI
            with dk.sdu.cbse.enemysystem.EnemyPlugin;
}