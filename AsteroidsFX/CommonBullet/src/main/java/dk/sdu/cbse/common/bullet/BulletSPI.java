package dk.sdu.cbse.common.bullet;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;

//Takes Entity as the shooter, meaning both player and enemy can call the interface for shooting
//That helps reusability and loose coupling.
public interface BulletSPI {
    Bullet createBullet(Entity shooter, GameData gameData);
}