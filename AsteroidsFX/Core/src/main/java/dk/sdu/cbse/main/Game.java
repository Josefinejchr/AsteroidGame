package dk.sdu.cbse.main;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Game {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private final Text scoreText = new Text(10, 20, "Score: 0");

    private final List<IGamePluginService> pluginServices;
    private final List<IEntityProcessingService> entityProcessingServices;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;

    Game(List<IGamePluginService> pluginServices,
         List<IEntityProcessingService> entityProcessingServices,
         List<IPostEntityProcessingService> postEntityProcessingServices) {
        this.pluginServices = pluginServices;
        this.entityProcessingServices = entityProcessingServices;
        this.postEntityProcessingServices = postEntityProcessingServices;
    }

    public void start(Stage window) throws Exception {
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.setStyle("-fx-background-color: black;");
        scoreText.setStyle("-fx-fill: white;");
        gameWindow.getChildren().add(scoreText);

        Scene scene = new Scene(gameWindow);
        setupInput(scene);

        for (IGamePluginService plugin : pluginServices) {
            plugin.start(gameData, world);
        }
        for (Entity entity : world.getEntities()) {
            addPolygon(entity);
        }

        window.setScene(scene);
        window.setTitle("Asteroids");
        window.show();
    }

    public void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
                gameData.getKeys().update();
            }
        }.start();
    }

    private void update() {
        for (IEntityProcessingService processor : entityProcessingServices) {
            processor.process(gameData, world);
        }
        for (IPostEntityProcessingService postProcessor : postEntityProcessingServices) {
            postProcessor.process(gameData, world);
        }
        removeInactiveEntities();
    }

    //uses isActive. marks entities as dead rather than removing than mid loop
    private void removeInactiveEntities() {
        for (Entity entity : world.getEntities()) {
            if (!entity.isActive()) {
                world.removeEntity(entity);
            }
        }
    }

    private void draw() {
        for (Entity entity : polygons.keySet()) {
            if (!world.getEntities().contains(entity)) {
                gameWindow.getChildren().remove(polygons.get(entity));
                polygons.remove(entity);
            }
        }
        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                addPolygon(entity);
            } else {
                polygon.setTranslateX(entity.getX());
                polygon.setTranslateY(entity.getY());
                polygon.setRotate(entity.getRotation());
            }
        }
        scoreText.setText("Score: " + gameData.getScore());
    }

    private void addPolygon(Entity entity) {
        Polygon polygon = new Polygon(entity.getPolygonCoordinates());
        polygon.setStyle("-fx-stroke: white; -fx-fill: black;");
        polygon.setTranslateX(entity.getX());
        polygon.setTranslateY(entity.getY());
        polygons.put(entity, polygon);
        gameWindow.getChildren().add(polygon);
    }

    private void setupInput(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT)  gameData.getKeys().setKey(0, true);
            if (event.getCode() == KeyCode.RIGHT) gameData.getKeys().setKey(1, true);
            if (event.getCode() == KeyCode.UP)    gameData.getKeys().setKey(2, true);
            if (event.getCode() == KeyCode.SPACE) gameData.getKeys().setKey(3, true);
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT)  gameData.getKeys().setKey(0, false);
            if (event.getCode() == KeyCode.RIGHT) gameData.getKeys().setKey(1, false);
            if (event.getCode() == KeyCode.UP)    gameData.getKeys().setKey(2, false);
            if (event.getCode() == KeyCode.SPACE) gameData.getKeys().setKey(3, false);
        });
    }
}