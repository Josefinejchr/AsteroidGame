package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

//Performs logic after all normal entity processors have executed.
//Used for collision detection and cross-entity interactions.

public interface IPostEntityProcessingService {
/**
 * @param gameData stores game metadata and current score
 * @param world    contains entities with finalised positions
 *
 * @pre  gameData must not be null
 * @pre  world must not be null
 * @pre  all IEntityProcessingService.process() calls have
 *       completed for the current frame
 *
 * @post the post-processing effect has been applied
 *       (collisions resolved, score reported)
 * @post world reflects all post-processing changes
 * @post gameData score may have increased; no other
 *       structural modifications
 */
    void process(GameData gameData, World world);
}