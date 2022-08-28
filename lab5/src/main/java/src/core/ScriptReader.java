package src.core;

import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

import src.elements.*;

/**
    Realization of AbstractReader, used for reading data from text files
    @see AbstractReader
*/
public class ScriptReader extends AbstractReader {

    private Scanner scanner;

    /**
        Creates new ScriptReader object
        @param scanner tool, to read data
        @see Scanner
    */
    public ScriptReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String[] getFirstLine() {
        String[] line = Arrays.asList(scanner.nextLine().split(" ")).stream().map(x -> x.trim()).filter(x -> !x.equals("")).toArray(String[]::new);
        if (!CheckInput.checkFirstLine(line)) throw new IllegalArgumentException();
        return line;
    }

    @Override
    protected String getName() {
        String name = scanner.nextLine().trim();
        if (!CheckInput.checkName(name)) throw new IllegalArgumentException();
        return name;
    }

    @Override
    protected Coordinates getCoordinates() {
        String[] coordinates = Arrays.asList(scanner.nextLine().split(" ")).stream().map(x -> x.trim()).filter(x -> !x.equals("")).toArray(String[]::new);
        if (!CheckInput.checkCoordinates(coordinates)) throw new IllegalArgumentException();
        return new Coordinates(Integer.parseInt(coordinates[0]), Long.parseLong(coordinates[1]));
    }

    @Override
    protected boolean getRealHero() {
        String realHero = scanner.nextLine().trim();
        if (!CheckInput.checkRealHero(realHero)) throw new IllegalArgumentException();
        return Boolean.parseBoolean(realHero);
    }

    @Override
    protected Boolean getToothpick() {
        String hasToothpick = scanner.nextLine().trim();
        if (!CheckInput.checkHasToothpick(hasToothpick)) throw new IllegalArgumentException();
        return hasToothpick.equals("") ? null : Boolean.parseBoolean(hasToothpick);
    }

    @Override
    protected int getImpactSpeed() {
        String impactSpeed = scanner.nextLine().trim();
        if (!CheckInput.checkInteger(impactSpeed)) throw new IllegalArgumentException();
        return Integer.parseInt(impactSpeed);
    }

    @Override
    protected String getSoundtrackName() {
        String soundtrackName = scanner.nextLine().trim();
        if (!CheckInput.checkName(soundtrackName)) throw new IllegalArgumentException();
        return soundtrackName;
    }

    @Override
    protected WeaponType getWeaponType() {
        String weapon = scanner.nextLine().trim();
        if (!CheckInput.checkWeapon(weapon)) throw new IllegalArgumentException();
        return WeaponType.valueOf(weapon.toUpperCase());
    }

    @Override
    protected Mood getMood() {
        String mood = scanner.nextLine().trim();
        if (!CheckInput.checkMood(mood)) throw new IllegalArgumentException();
        return mood.equals("") ? null : Mood.valueOf(mood.toUpperCase());
    }

    @Override
    protected Car getCar() {
        String car = scanner.nextLine().trim();
        if (!CheckInput.checkHasToothpick(car)) throw new IllegalArgumentException();
        return car.equals("") ? null : new Car(Boolean.parseBoolean(car));
    }
}
