package dk.sdu.cbse;

import dk.sdu.cbse.bulletsystem.Bullet;
import dk.sdu.cbse.bulletsystem.BulletSystem;
import dk.sdu.cbse.common.Entity;
import dk.sdu.cbse.common.GameData;
import dk.sdu.cbse.common.World;
import dk.sdu.cbse.enemysystem.Enemy;
import dk.sdu.cbse.enemysystem.EnemySystem;
import dk.sdu.cbse.playersystem.Player;
import dk.sdu.cbse.playersystem.PlayerSystem;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.Random;

import javafx.scene.paint.Color;

public class App extends Application {

    private final GameData game = new GameData();
    private final World world = new World();

    private final PlayerSystem playerSystem = new PlayerSystem();
    private final BulletSystem bulletSystem = new BulletSystem();
    private final EnemySystem enemySystem = new EnemySystem();

    private final Random rnd = new Random();

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(game.width, game.height);
        GraphicsContext g = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(e -> game.keysDown.add(mapKey(e.getCode())));
        scene.setOnKeyReleased(e -> game.keysDown.remove(mapKey(e.getCode())));

        stage.setTitle("AsteroidsFX - Player, Bullets, Enemy");
        stage.setScene(scene);
        stage.show();

        spawnPlayer();
        spawnEnemy();

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

    private void spawnPlayer() {
        Player p = new Player();
        p.tag = "player";
        p.x = game.width / 2.0;
        p.y = game.height / 2.0;
        p.rotation = -90;
        p.radius = 10;
        world.add(p);
    }

    private void spawnEnemy() {
        Enemy e = new Enemy();
        e.tag = "enemy";
        e.x = rnd.nextDouble() * game.width;
        e.y = rnd.nextDouble() * game.height;
        e.rotation = rnd.nextInt(360);
        e.radius = 12;
        world.add(e);
    }

    private void update() {
        // systems
        playerSystem.update(game, world);
        enemySystem.update(game, world);

        // shooting (player)
        for (Player p : world.getEntities(Player.class)) {
            if (game.keysDown.contains("SPACE") && p.shootCooldown <= 0) {
                world.add(createBulletFrom(p, 380));
                p.shootCooldown = 0.18;
            }
        }

        // shooting (enemy random)
        for (Enemy e : world.getEntities(Enemy.class)) {
            if (e.shootTimer <= 0 && rnd.nextDouble() < 0.03) {
                world.add(createBulletFrom(e, 300));
                e.shootTimer = 0.6 + rnd.nextDouble() * 0.8;
            }
        }

        // move all entities
        for (Entity entity : world.getEntities()) {
            entity.x += entity.vx * game.delta;
            entity.y += entity.vy * game.delta;

            // screen wrap
            if (entity.x < 0) entity.x = game.width;
            if (entity.x > game.width) entity.x = 0;
            if (entity.y < 0) entity.y = game.height;
            if (entity.y > game.height) entity.y = 0;
        }

        // bullet lifetime
        bulletSystem.update(game, world);

        // remove dead
        Iterator<Entity> it = world.getEntities().iterator();
        while (it.hasNext()) {
            if (!it.next().alive) it.remove();
        }
    }

    private Bullet createBulletFrom(Entity shooter, double speed) {
        Bullet b = new Bullet();
        b.tag = "bullet";
        b.radius = 3;

        double dx = Math.cos(Math.toRadians(shooter.rotation));
        double dy = Math.sin(Math.toRadians(shooter.rotation));

        b.x = shooter.x + dx * (shooter.radius + 6);
        b.y = shooter.y + dy * (shooter.radius + 6);

        b.vx = shooter.vx + dx * speed;
        b.vy = shooter.vy + dy * speed;

        b.rotation = shooter.rotation;
        return b;
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
            } else {
                // bullet
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