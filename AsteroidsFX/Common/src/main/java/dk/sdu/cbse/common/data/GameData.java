package dk.sdu.cbse.common.data;

//Global read only state the system need
public class GameData {
    //Display width and height
    public final int displayWidth = 800;
    private final int displayHeight = 600;
    private final GameKeys keys = new GameKeys();

    //Is here because modules that destroy needs to add to it without depending on eachother
    private int score = 0;

    public int getDisplayWidth() {return displayWidth;}
    public int getDisplayHeight() {return displayHeight;}
    public GameKeys getKeys() {return keys;}

    public int getScore() {return score;}
    public void addScore(int points) {score += points;}
}
