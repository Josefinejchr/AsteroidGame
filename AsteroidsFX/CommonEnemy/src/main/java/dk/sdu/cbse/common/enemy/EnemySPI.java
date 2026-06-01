package dk.sdu.cbse.common.enemy;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

public interface EnemySPI {
    //Makes sure enemies are spawned into the world directly, like asteroids.
    void createEnemy(GameData gameData, World world);
}