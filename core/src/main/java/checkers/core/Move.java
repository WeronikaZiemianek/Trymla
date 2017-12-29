package checkers.core;

import java.io.Serializable;

public class Move implements Serializable {
    Coordinates location;
    Coordinates destination;
    Checker color;

    public Coordinates getLocation() {
        return location;
    }

    public Coordinates getDestination() {
        return destination;
    }

    public Checker getColor() {
        return color;
    }

    public Move(Coordinates location, Coordinates destination, Checker color) {
        this.color = color;
        this.destination = destination;
        this.location = location;
    }
}
