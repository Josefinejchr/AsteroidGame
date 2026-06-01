module Core {
    requires Common;
    requires CommonBullet;
    requires CommonAsteroids;
    requires CommonEnemy;
    requires javafx.graphics;
    requires javafx.controls;
    requires spring.context;
    requires spring.core;
    requires spring.beans;

    exports dk.sdu.cbse.main;
    opens dk.sdu.cbse.main to javafx.graphics, spring.core;

    //Lets serviceLoader discover implementations at runtime
    uses dk.sdu.cbse.common.services.IGamePluginService;
    uses dk.sdu.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.cbse.common.services.IPostEntityProcessingService;
}