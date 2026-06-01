package dk.sdu.cbse.common.data;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class World {
    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();

    public void addEntity(Entity entity) {
        entityMap.put(entity.getId(), entity);
    }

    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getId());
    }

    public void removeEntity(String entityId) {
        entityMap.remove(entityId);
    }

    //No system can mutate the map directly. controlled access.
    public Collection<Entity> getEntities() {
        return Collections.unmodifiableCollection(entityMap.values());
    }

    //Generic method for writing clean loops without casting.
    public <T extends Entity> Collection<T> getEntities(Class<T> type) {
        return entityMap.values().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();
    }
}