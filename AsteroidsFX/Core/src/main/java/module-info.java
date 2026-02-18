module Core {
    requires javafx.controls;

    requires Common;
    requires Player;
    requires Bullet;
    requires Enemy;

    uses dk.sdu.cbse.common.services.IGamePluginService;
    uses dk.sdu.cbse.common.services.IEntityProcessorService;
    uses dk.sdu.cbse.common.services.IPostEntityProcessorService;

    exports dk.sdu.cbse;
}