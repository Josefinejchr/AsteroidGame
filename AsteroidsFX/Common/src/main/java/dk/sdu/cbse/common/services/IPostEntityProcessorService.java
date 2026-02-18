package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;

/**
 * Performs logic after all normal entity processors have executed.
 * Used for collision detection and cross-entity interactions.
 */
public interface IPostEntityProcessorService {

    /**
     * Performs post-processing.
     *
     * Preconditions:
     * - gameData != null
     * - world != null
     * - All IEntityProcessorService have already run this frame
     *
     * Postconditions:
     * - Collisions and interactions are resolved
     * - Destroyed entities are removed from world
     */
    void process(GameData gameData, World world);
}