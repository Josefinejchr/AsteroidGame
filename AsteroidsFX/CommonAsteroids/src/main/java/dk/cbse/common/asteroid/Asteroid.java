package dk.sdu.cbse.common.asteroid;

import dk.sdu.cbse.common.data.Entity;

public class Asteroid extends Entity{

    //drives splitting logic
    private int size; //3=large/2=medium/1=small

    public int getSize() { return size;}
    public void setSize(int size) {this.size = size;}
}