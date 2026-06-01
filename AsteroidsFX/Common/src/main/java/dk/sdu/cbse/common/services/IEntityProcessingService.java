package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

//Service interface for systems that update entity state each frame.
//Called once per game loop iteration for every active entity of the relevant type.
// Implementations handle movement, input, AI behaviour,and similar per-frame logic.
public interface IEntityProcessingService {
    void process(GameData gameData, World world);
}