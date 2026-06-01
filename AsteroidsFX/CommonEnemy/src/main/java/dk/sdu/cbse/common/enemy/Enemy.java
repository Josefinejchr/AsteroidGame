package dk.sdu.cbse.common.enemy;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.Destructible;

public class Enemy extends Entity implements Destructible {
   //Enemysystem needs speed and health for movement and it takes multiple bullets to destroy for enemy combat
    private double speed = 1.5;
    private int health = 3;

    public double getSpeed() {return speed;}
    public void setSpeed(double speed) {this.speed = speed;}

    public int getHealth() {return health;}
    public void setHealth(int health) {this.health = health;}
}