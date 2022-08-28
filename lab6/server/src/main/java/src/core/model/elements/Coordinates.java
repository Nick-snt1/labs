package src.core.model.elements;

import java.util.Optional;
import java.util.Objects;

public class Coordinates {

    private int x;

    private Long y;

    public Coordinates(int x, Long y) {
        this.x = x;
        this.y = Objects.requireNonNull(y);
    }

    public int getX() { return x; }

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
