package src.core.readers;

import java.util.Scanner;

/**
    AbstractReader is the abstract base class for InputReader and ScriptReader, which will read commands,
    their arguments and fields required to create HumanBeing object. Uses <b>scanner</b> to read input.
*/
public abstract class AbstractReader {

    /**
        Reads first line from the input, which contains
        command name and additional arguments, depending on command
        @return array of strings, first element - command name, second - argument, if present
    */
    public abstract String[] getFirstLine();

    /**
        Reads HumanBeing's name
        @return non null and non empty string
    */
    protected abstract String getName();

    /**
        Reads x coordinate of HumanBeing
        @return Coordinate object, consists of int x
    */
    protected abstract String getX();

    /**
        Reads y coordinate of HumanBeing
        @return Coordinate object, consists of Long y
    */
    protected abstract String getY();

    /**
        Reads realHero field of HumanBeing
        @return is human a real hero
    */
    protected abstract String getRealHero();

    /**
        Reads toothpick field of HumanBeing
        @return is human has a toothpick (can be null)
    */
    protected abstract String getToothpick();

    /**
        Reads impactSpeed field of HumanBeing
        @return speed of current human
    */
    protected abstract String getImpactSpeed();

    /**
        Reads soundtrackName field of HumanBeing
        @return name of track, which human listens to (must not be null)
    */
    protected abstract String getSoundtrackName();

    /**
        Reads weaponType field of HumanBeing
        @return type of weapon, which human carry (must not be null)
    */
    protected abstract String getWeaponType();

    /**
        Reads mood field of HumanBeing
        @return current human's mood (can be null)
    */
    protected abstract String getMood();

    /**
        Reads car field of HumanBeing
        @return current human's car (can be null)
    */
    protected abstract String getCar();

    /**
        Reads all fields, which required to create HumanBeing object
        @return HumanBeing object
    */
    public String[] getHumanBeing() {
        return new String[]{ getName(), getX(), getY(), getRealHero(), getToothpick(),
            getImpactSpeed(), getSoundtrackName(), getWeaponType(), getMood(), getCar() };
    }
}
