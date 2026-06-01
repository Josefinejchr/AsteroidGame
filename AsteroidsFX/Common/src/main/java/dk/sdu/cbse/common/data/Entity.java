package dk.sdu.cbse.common.data;

import java.util.UUID;

//Base class for every game object
public abstract class Entity {

    private final String id = UUID.randomUUID().toString();
    private double x;
    private double y;
    private double rotation; // degrees
    private double radius;
    private double[] polygonCoordinates;
    private boolean active = true;


    public String getId() { return id;}

    public double getX() { return x;}
    public void setX(double x) {this.x = x;}

    public double getY() { return y;}
    public void setY(double y) { this.y = y;}

    public double getRotation() {return rotation;}
    public void setRotation(double rotation) {this.rotation = rotation;}

    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }

    public double[] getPolygonCoordinates() {return polygonCoordinates;}
    public void setPolygonCoordinates( double... polygonCoordinates) {this.polygonCoordinates = polygonCoordinates;}

    public boolean isActive() {return active;}
    public void setActive(boolean active) {this.active = active;}
}
