package src.core.readers;

import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;


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
        return line.length == 2 ? line : new String[] {line[0], null};
    }

    @Override
    protected String getName() {
        String name = scanner.nextLine().trim();
        if (!CheckInput.checkName(name)) throw new IllegalArgumentException();
        return name;
    }

    @Override
    protected String getX() {
        String x = scanner.nextLine().trim();
        if (!CheckInput.checkInteger(x)) throw new IllegalArgumentException();
        return x;
    }

    @Override
    protected String getY() {
        String y = scanner.nextLine().trim();
        if (!CheckInput.checkInteger(y)) throw new IllegalArgumentException();
        return y;
    }

    @Override
    protected String getRealHero() {
        String realHero = scanner.nextLine().trim();
        if (!CheckInput.checkRealHero(realHero)) throw new IllegalArgumentException();
        return realHero;
    }

    @Override
    protected String getToothpick() {
        String hasToothpick = scanner.nextLine().trim();
        if (!CheckInput.checkHasToothpick(hasToothpick)) throw new IllegalArgumentException();
        return hasToothpick.equals("") ? null : hasToothpick;
    }

    @Override
    protected String getImpactSpeed() {
        String impactSpeed = scanner.nextLine().trim();
        if (!CheckInput.checkInteger(impactSpeed)) throw new IllegalArgumentException();
        return impactSpeed;
    }

    @Override
    protected String getSoundtrackName() {
        String soundtrackName = scanner.nextLine().trim();
        if (!CheckInput.checkName(soundtrackName)) throw new IllegalArgumentException();
        return soundtrackName;
    }

    @Override
    protected String getWeaponType() {
        String weapon = scanner.nextLine().trim();
        if (!CheckInput.checkWeapon(weapon)) throw new IllegalArgumentException();
        return weapon.toUpperCase();
    }

    @Override
    protected String getMood() {
        String mood = scanner.nextLine().trim();
        if (!CheckInput.checkMood(mood)) throw new IllegalArgumentException();
        return mood.equals("") ? null : mood.toUpperCase();
    }

    @Override
    protected String getCar() {
        String car = scanner.nextLine().trim();
        if (!CheckInput.checkHasToothpick(car)) throw new IllegalArgumentException();
        return car.equals("") ? null : car;
    }
}
