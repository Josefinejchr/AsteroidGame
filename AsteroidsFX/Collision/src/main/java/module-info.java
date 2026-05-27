module Collision {
    requires Common;

    exports dk.sdu.cbse.collision;

    provides dk.sdu.cbse.common.services.IPostEntityProcessorService
            with dk.sdu.cbse.collision.CollisionProcessor;
}