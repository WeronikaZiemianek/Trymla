package checkers.core;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int X() {
        return x;
    }

    public int Y() {
        return y;
    }

    public boolean equals(Coordinates c) {
        return x == c.X() && y == c.Y();
    }

}
