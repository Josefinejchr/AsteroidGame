package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;

/**
 * Updates entities each frame.
 *
 * This is called once per frame in the game loop.
 */
public interface IEntityProcessorService {

    /**
     * Processes relevant entities for this frame.
     *
     * Preconditions:
     * - gameData != null
     * - world != null
     * - Delta time is valid
     *
     * Postconditions:
     * - Entities have updated state (position, rotation, etc.)
     * - No entity is left in an invalid state
     */
    void process(GameData gameData, World world);
}