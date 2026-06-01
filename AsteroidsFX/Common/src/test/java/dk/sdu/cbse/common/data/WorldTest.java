package dk.sdu.cbse.common.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    private World world;

    // runs before each test, sets up a fresh world
    @BeforeEach
    void setUp() {
        world = new World();
    }

    @Test
    void addEntity_shouldIncreaseEntityCount() {
        TestEntity entity = new TestEntity();
        world.addEntity(entity);
        assertEquals(1, world.getEntities().size());
    }

    @Test
    void removeEntity_shouldDecreaseEntityCount() {
        TestEntity entity = new TestEntity();
        world.addEntity(entity);
        world.removeEntity(entity);
        assertEquals(0, world.getEntities().size());
    }

    @Test
    void getEntitiesByType_shouldOnlyReturnCorrectType() {
        TestEntity testEntity = new TestEntity();
        AnotherEntity anotherEntity = new AnotherEntity();
        world.addEntity(testEntity);
        world.addEntity(anotherEntity);

        assertEquals(1, world.getEntities(TestEntity.class).size());
        assertEquals(1, world.getEntities(AnotherEntity.class).size());
    }

    @Test
    void addEntity_shouldBeRetrievableById() {
        TestEntity entity = new TestEntity();
        world.addEntity(entity);
        assertTrue(world.getEntities().contains(entity));
    }

    // small concrete subclasses just for testing
    // Entity is abstract so we need concrete versions
    static class TestEntity extends Entity {}
    static class AnotherEntity extends Entity {}
}