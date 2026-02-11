package dk.sdu.cbse.common;

import java.util.HashSet;
import java.util.Set;
public class GameData {
    public int width = 900;
    public int height = 650;

    public final Set<String> keysDown = new HashSet<>();
    public double delta; // seconds
}
