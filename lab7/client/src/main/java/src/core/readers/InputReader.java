package src.core.readers;

import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import java.io.Console;


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
        return line.length == 2 ? line : new String[] {line[0], null};
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
    protected String getX() {
        String x;
        do {
            System.out.print("Pleace, enter coprdinate x>> ");
            if ((x = console.readLine()) != null) x = x.trim();
        } while(!CheckInput.checkInteger(x));

        return x;
    }

    @Override
    protected String getY() {
        String y;
        do {
            System.out.print("Pleace, enter coprdinate y>> ");
            if ((y = console.readLine()) != null) y = y.trim();
        } while(!CheckInput.checkLong(y));

        return y;
    }

    @Override
    protected String getRealHero() {
        String realHero;
        do {
            System.out.print("Enter field realHero (true/false)>> ");
            if ((realHero = console.readLine()) != null) realHero = realHero.trim();
        } while (!CheckInput.checkRealHero(realHero));

        return realHero;
    }

    @Override
    protected String getToothpick() {
        String hasToothpick;
        do {
            System.out.print("Enter field hasToothpick (true/false)>> ");
            if ((hasToothpick = console.readLine()) != null) hasToothpick = hasToothpick.trim();
        } while(!CheckInput.checkHasToothpick(hasToothpick));

        return hasToothpick.equals("") ? null : hasToothpick;
    }

    @Override
    protected String getImpactSpeed() {
        String impactSpeed;
        do {
            System.out.print("Enter field impactSpeed>> ");
            if ((impactSpeed = console.readLine()) != null) impactSpeed = impactSpeed.trim();
        } while(!CheckInput.checkInteger(impactSpeed));

        return impactSpeed;
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
    protected String getWeaponType() {
        String weapon;
        do {
            System.out.print("Enter one of available weapon type: HAMMER, PISTOL, MACHINE_GUN, BAT "
                + System.lineSeparator() + ">> " );
            if ((weapon  = console.readLine()) != null) weapon = weapon.trim();
        } while(!CheckInput.checkWeapon(weapon));

        return weapon.toUpperCase();
    }

    @Override
    protected String getMood() {
        String mood;
        do {
            System.out.print("Enter one of available mood: SADNESS, LONGING, APATHY, FRENZY "
                + System.lineSeparator() + ">> " );
            if ((mood  = console.readLine()) != null) mood = mood.trim();
        } while(!CheckInput.checkMood(mood));

        return mood.equals("") ? null : mood.toUpperCase();
    }

    @Override
    protected String getCar() {
        String car;
        do {
            System.out.print("Enter field cool car (true/false)>> ");
            if ((car  = console.readLine()) != null) car = car.trim();
        } while(!CheckInput.checkHasToothpick(car));

        return car.equals("") ? null : car;
    }
}
