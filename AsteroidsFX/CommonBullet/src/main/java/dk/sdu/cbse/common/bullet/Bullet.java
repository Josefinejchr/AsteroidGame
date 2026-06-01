package dk.sdu.cbse.common.bullet;

import dk.sdu.cbse.common.data.Entity;

//Marker class that extends entity. meaning it gets all position/rotation/radius fields
//Makes so world.getentities(Bullet.class) works in BulletSystem.
public class Bullet extends Entity{
    private int lifetime = 120; //number of frames before removal

    public int getLifetime() {return lifetime;}
    public void setLifetime(int lifetime) {this.lifetime = lifetime;}
}