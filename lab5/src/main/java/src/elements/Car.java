package src.elements;


/**
    Class, which is represented only by one field, using to store information
    about human's car
*/
public class Car {

    /** Field, used to indicate whether this car is cool */
    private boolean cool;

    /**
        Creates new Car object
        @param cool if car is cool
    */
    public Car(boolean cool) {
        this.cool = cool;
    }

    /**
        Gets value of the fild cool
        @return if this car is cool
    */
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
