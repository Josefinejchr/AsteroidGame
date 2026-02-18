package dk.sdu.cbse;

import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IEntityProcessorService;
import dk.sdu.cbse.common.services.IPostEntityProcessorService;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.ServiceLoader;

public class App extends Application {

    private final GameData game = new GameData();
    private final World world = new World();

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(game.width, game.height);
        GraphicsContext g = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(e -> game.keysDown.add(mapKey(e.getCode())));
        scene.setOnKeyReleased(e -> game.keysDown.remove(mapKey(e.getCode())));

        stage.setTitle("AsteroidsFX - Component Based");
        stage.setScene(scene);
        stage.show();

        // Start all plugins once (Player, Enemy, Asteroids, ...)
        for (IGamePluginService plugin : ServiceLoader.load(IGamePluginService.class)) {
            plugin.start(game, world);
        }

        new AnimationTimer() {
            long last = System.nanoTime();

            @Override
            public void handle(long now) {
                game.delta = (now - last) / 1_000_000_000.0;
                last = now;

                update();
                render(g);
            }
        }.start();
    }

    private void update() {
        // Run normal processors
        for (IEntityProcessorService processor : ServiceLoader.load(IEntityProcessorService.class)) {
            processor.process(game, world);
        }

        // Run post processors (collisions etc.)
        for (IPostEntityProcessorService post : ServiceLoader.load(IPostEntityProcessorService.class)) {
            post.process(game, world);
        }

        // Remove dead entities
        Iterator<Entity> it = world.getEntities().iterator();
        while (it.hasNext()) {
            if (!it.next().alive) it.remove();
        }
    }

    private String mapKey(KeyCode code) {
        if (code == KeyCode.LEFT) return "LEFT";
        if (code == KeyCode.RIGHT) return "RIGHT";
        if (code == KeyCode.UP) return "UP";
        if (code == KeyCode.SPACE) return "SPACE";
        return code.toString();
    }

    private void render(GraphicsContext g) {
        g.clearRect(0, 0, game.width, game.height);

        for (Entity e : world.getEntities()) {
            if ("player".equals(e.tag)) {
                g.setStroke(Color.LIMEGREEN);
                drawTriangle(g, e.x, e.y, e.rotation, 14);

            } else if ("enemy".equals(e.tag)) {
                g.setStroke(Color.RED);
                drawTriangle(g, e.x, e.y, e.rotation, 16);

            } else if ("asteroid".equals(e.tag)) {
                g.setStroke(Color.LIGHTGRAY);
                g.strokeOval(e.x - e.radius, e.y - e.radius, e.radius * 2, e.radius * 2);

            } else if ("bullet".equals(e.tag)) {
                g.setFill(Color.WHITE);
                g.fillOval(e.x - e.radius, e.y - e.radius, e.radius * 2, e.radius * 2);

            } else {
                g.setFill(Color.YELLOW);
                g.fillOval(e.x - e.radius, e.y - e.radius, e.radius * 2, e.radius * 2);
            }
        }
    }

    private void drawTriangle(GraphicsContext g, double x, double y, double rotDeg, double size) {
        double a = Math.toRadians(rotDeg);

        double x1 = x + Math.cos(a) * size;
        double y1 = y + Math.sin(a) * size;

        double x2 = x + Math.cos(a + Math.toRadians(140)) * size * 0.8;
        double y2 = y + Math.sin(a + Math.toRadians(140)) * size * 0.8;

        double x3 = x + Math.cos(a - Math.toRadians(140)) * size * 0.8;
        double y3 = y + Math.sin(a - Math.toRadians(140)) * size * 0.8;

        g.strokePolygon(new double[]{x1, x2, x3}, new double[]{y1, y2, y3}, 3);
    }

    public static void main(String[] args) {
        launch();
    }
}