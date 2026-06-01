module Collision {
    requires Common;
    requires CommonBullet;
    requires CommonAsteroids;
    requires CommonEnemy;

    provides dk.sdu.cbse.common.services.IPostEntityProcessingService
            with dk.sdu.cbse.collisionsystem.CollisionSystem;
}