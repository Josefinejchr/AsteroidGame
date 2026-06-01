package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

//Performs logic after all normal entity processors have executed.
//Used for collision detection and cross-entity interactions.

public interface IPostEntityProcessingService {
    void process(GameData gameData, World world);
}