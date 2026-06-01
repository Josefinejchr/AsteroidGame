package dk.sdu.cbse.playersystem;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.Destructible;
public class Player extends Entity implements Destructible{
    //Like Enemy player gets health, so collision can deal damage instead of instant death.
    private int health = 3;

    public int getHealth() {return health;}
    public void setHealth(int health) {this.health = health;}
}
