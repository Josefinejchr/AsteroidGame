package dk.sdu.cbse.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class World {
    private final List<Entity> entities = new ArrayList<>();
    public void add(Entity e) { entities.add(e); }
    public void remove(Entity e) { entities.remove(e); }
    public List<Entity> getEntities() { return entities; }
    public <T extends Entity> List<T> getEntities(Class<T> type) {
        return entities.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }
}
