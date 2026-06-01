package dk.sdu.cbse.common.asteroid;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

public interface AsteroidSPI {

    //takes world and adds entities internally.
    void createAsteroid(GameData gameData, World world);
}
