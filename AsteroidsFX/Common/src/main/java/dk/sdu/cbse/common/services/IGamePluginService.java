package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

 //Implementations are discovered at runtime via the Java ServiceLoader mechanism.
 //Each plugin is responsible for creating and removing its own entities.

public interface IGamePluginService {

    /**
     * Called once when the game starts.
     * Implementations should create and register their entities into the world.
     * @param gameData
     * @param world
     * @pre  gameData and world are fully initialised and non-null
     * @post at least one entity has been added to the world by this plugin
     */
    void start(GameData gameData, World world);

    /**
     * Called once when the game stops. Implementations should remove all entities they created from the world to avoid memory leaks.
     * @param gameData
     * @param world
     * @pre  start has previously been called with the same world instance
     * @post all entities created by this plugin have been removed from world
     */
    void stop(GameData gameData, World world);
}