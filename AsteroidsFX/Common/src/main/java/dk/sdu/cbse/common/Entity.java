package dk.sdu.cbse.common;
public class Entity {
    public double x, y;
    public double rotation;      // degrees
    public double radius = 8;

    public double vx, vy;        // velocity
    public boolean alive = true;

    public String tag = "";      // "player", "enemy", "bullet" (optional)
}
