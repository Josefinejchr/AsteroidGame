module Bullet {
    requires Common;

    exports dk.sdu.cbse.bulletsystem;

    provides dk.sdu.cbse.common.services.IEntityProcessorService
            with dk.sdu.cbse.bulletsystem.BulletProcessor;
}