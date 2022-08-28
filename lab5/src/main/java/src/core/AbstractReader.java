package src.core;

import java.util.Scanner;

import src.elements.*;

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
        Reads x and y coordinates of HumanBeing
        @return Coordinates object, consists of int x and Long y
    */
    protected abstract Coordinates getCoordinates();

    /**
        Reads realHero field of HumanBeing
        @return is human a real hero
    */
    protected abstract boolean getRealHero();

    /**
        Reads toothpick field of HumanBeing
        @return is human has a toothpick (can be null)
    */
    protected abstract Boolean getToothpick();

    /**
        Reads impactSpeed field of HumanBeing
        @return speed of current human
    */
    protected abstract int getImpactSpeed();

    /**
        Reads soundtrackName field of HumanBeing
        @return name of track, which human listens to (must not be null)
    */
    protected abstract String getSoundtrackName();

    /**
        Reads weaponType field of HumanBeing
        @return type of weapon, which human carry (must not be null)
    */
    protected abstract WeaponType getWeaponType();

    /**
        Reads mood field of HumanBeing
        @return current human's mood (can be null)
    */
    protected abstract Mood getMood();

    /**
        Reads car field of HumanBeing
        @return current human's car (can be null)
    */
    protected abstract Car getCar();

    /**
        Reads all fields, which required to create HumanBeing object
        @return HumanBeing object
    */
    public HumanBeing getHumanBeing() {
        return new HumanBeing(getName(), getCoordinates(), getRealHero(), getToothpick(), getImpactSpeed(),
            getSoundtrackName(), getWeaponType(), getMood(), getCar());
    }
}
