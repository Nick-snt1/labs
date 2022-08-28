package src.core.model.elements;


public class Car {

    private boolean cool;

    public Car(boolean cool) {
        this.cool = cool;
    }

    public boolean isCool() { return cool; }

    @Override
    public String toString() {
        return "Car [cool=" + cool + "]";
    }

    @Override
    public int hashCode() { return 29 * 41 + (cool ? 1 : 0); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Car) return cool == ((Car) o).isCool();
        return false;
    }
}
