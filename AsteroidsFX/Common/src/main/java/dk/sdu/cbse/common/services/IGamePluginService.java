package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;

/**
 * Responsible for adding and removing entities when a game module is started or stopped.
 *
 * A plugin typically creates entities such as Player, Enemy or Asteroids.
 */
public interface IGamePluginService {

    /**
     * Called when the module is started.
     *
     * Preconditions:
     * - gameData != null
     * - world != null
     *
     * Postconditions:
     * - All entities required by the module are added to the world
     * - Entities are initialized in a valid state
     */
    void start(GameData gameData, World world);

    /**
     * Called when the module is stopped.
     *
     * Preconditions:
     * - gameData != null
     * - world != null
     *
     * Postconditions:
     * - All entities created by this module are removed from the world
     */
    void stop(GameData gameData, World world);
}