package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

//Service interface for systems that update entity state each frame.
//Called once per game loop iteration for every active entity of the relevant type.
// Implementations handle movement, input, AI behaviour,and similar per-frame logic.

public interface IEntityProcessingService {
    /**
     * @param gameData
     * @param world
     *
     * @pre gameData must not be null
     * @pre world must not be null
     * @pre world contaions entities
     *
     * @post entities have been updated, added or marked inactive
     */
    void process(GameData gameData, World world);
}