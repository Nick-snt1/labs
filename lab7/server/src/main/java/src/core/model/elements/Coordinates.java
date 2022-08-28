package src.core.model.elements;

import java.util.Optional;
import java.util.Objects;

/**
    Class, used to store human's position, x-coordinate and y-coordinate
*/
public class Coordinates {

    /** x-coordinate of human */
    private int x;

    /** y-coordinate of human (must not be null) */
    private Long y;

    /**
        Creates new Coordinates object
        @param x human's x-coordinate
        @param y human's y-coordinate
    */
    public Coordinates(int x, Long y) {
        this.x = x;
        this.y = Objects.requireNonNull(y);
    }

    /**
        Gets value of field x
        @return x-coordinate
    */
    public int getX() { return x; }

    /**
        Gets value of field y
        @return y-coordinate
    */
    public Long getY() { return y; }

    @Override
    public String toString() {
        return "Coordinates [x=" + x + ", y=" + y + "]";
    }

    @Override
    public int hashCode() {
        int result = 13;
        result = 17 * result + x;
        result = 17 * result + y.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Coordinates) {
            int oX = ((Coordinates) o).getX();
            Long oY = ((Coordinates) o).getY();
            return x == oX && y.equals(oY);
        }
        return false;
    }
}
