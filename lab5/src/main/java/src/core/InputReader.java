package src.core;

import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import java.io.Console;

import src.elements.*;

/**
    Realization of AbstractReader, used for reading data from standard input
    @see AbstractReader
*/
public class InputReader extends AbstractReader {

    private Console console = System.console();

    @Override
    public String[] getFirstLine() {
        String[] line = {""};
        do {
            System.out.print("Pleace, input command>> ");
            String l = console.readLine();
            if (l  != null)
                line = Arrays.asList(l.split(" ")).stream().map(x -> x.trim()).filter(x -> !x.equals("")).toArray(String[]::new);
        } while (!CheckInput.checkFirstLine(line));
        return line;
    }

    @Override
    protected String getName() {
        String name;
        do {
            System.out.print("Pleace, enter name>> ");
            if ((name = console.readLine()) != null) name = name.trim();
        } while (!CheckInput.checkName(name));
        return name;
    }

    @Override
    protected Coordinates getCoordinates() {
        String[] coordinates = {""};
        do {
            System.out.print("Pleace, enter coprdinates x and y>> ");
            String c = console.readLine();
            if (c != null)
                coordinates = Arrays.asList(c.split(" ")).stream().map(x -> x.trim()).filter(x -> !x.equals("")).toArray(String[]::new);
        } while (!CheckInput.checkCoordinates(coordinates));

        return new Coordinates(Integer.parseInt(coordinates[0]), Long.parseLong(coordinates[1]));
    }

    @Override
    protected boolean getRealHero() {
        String realHero;
        do {
            System.out.print("Enter field realHero (true/false)>> ");
            if ((realHero = console.readLine()) != null) realHero = realHero.trim();
        } while (!CheckInput.checkRealHero(realHero));

        return Boolean.parseBoolean(realHero);
    }

    @Override
    protected Boolean getToothpick() {
        String hasToothpick;
        do {
            System.out.print("Enter field hasToothpick (true/false)>> ");
            if ((hasToothpick = console.readLine()) != null) hasToothpick = hasToothpick.trim();
        } while(!CheckInput.checkHasToothpick(hasToothpick));

        return hasToothpick.equals("") ? null : Boolean.parseBoolean(hasToothpick);
    }

    @Override
    protected int getImpactSpeed() {
        String impactSpeed;
        do {
            System.out.print("Enter field impactSpeed>> ");
            if ((impactSpeed = console.readLine()) != null) impactSpeed = impactSpeed.trim();
        } while(!CheckInput.checkInteger(impactSpeed));

        return Integer.parseInt(impactSpeed);
    }

    @Override
    protected String getSoundtrackName() {
        String soundtrackName;
        do {
            System.out.print("Pleace, enter soundtrack name>> ");
            if ((soundtrackName  = console.readLine()) != null) soundtrackName = soundtrackName.trim();
        } while (!CheckInput.checkName(soundtrackName));

        return soundtrackName;
    }

    @Override
    protected WeaponType getWeaponType() {
        String weapon;
        String weaponType = Arrays.toString(WeaponType.values());
        do {
            System.out.print("Enter one of available weapon type: " + weaponType.substring(1,weaponType.length()-1)
                            + System.lineSeparator() + ">> ");
            if ((weapon  = console.readLine()) != null) weapon = weapon.trim();
        } while(!CheckInput.checkWeapon(weapon));

        return WeaponType.valueOf(weapon.toUpperCase());
    }

    @Override
    protected Mood getMood() {
        String m;
        String mood = Arrays.toString(Mood.values());
        do {
            System.out.print("Enter one of available mood: " + mood.substring(1,mood.length()-1)
                             + System.lineSeparator() + ">> ");
            if ((m  = console.readLine()) != null) m = m.trim();
        } while(!CheckInput.checkMood(m));

        return m.equals("") ? null : Mood.valueOf(m.toUpperCase());
    }

    @Override
    protected Car getCar() {
        String car;
        do {
            System.out.print("Enter field cool car (true/false)>> ");
            if ((car  = console.readLine()) != null) car = car.trim();
        } while(!CheckInput.checkHasToothpick(car));

        return car.equals("") ? null : new Car(Boolean.parseBoolean(car));
    }
}
