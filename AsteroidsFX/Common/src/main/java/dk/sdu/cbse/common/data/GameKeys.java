package dk.sdu.cbse.common.data;

public class GameKeys {

    public static final int UP = 2;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int SPACE = 3;

    private static final int NUM_KEYS = 4;
    private final boolean[] keys = new boolean[NUM_KEYS];
    private final boolean[] prevKeys = new boolean[NUM_KEYS];

    public void setKey(int key, boolean value) {
        keys[key] = value;
    }

    public boolean isDown(int key) {
        return keys[key];
    }

    //usefull for shooting, so that bullets are not fired every single frame.
    public boolean isPressed(int key) {
        return keys[key] && !prevKeys[key];
    }

    public void update() {
        System.arraycopy(keys, 0, prevKeys, 0, NUM_KEYS);
    }

}
