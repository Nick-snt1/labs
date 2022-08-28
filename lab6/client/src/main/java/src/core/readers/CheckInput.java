package src.core.readers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CheckInput {

    private static Set<String> noArgCommands = new HashSet<>(Arrays.asList(
        "help", "info", "show", "clear", "exit",
        "print_ascending", "print_descending","add",
        "add_if_min", "remove_greater", "remove_lower"
    ));

    private static Set<String> oneStringCommands = new HashSet<>(Arrays.asList(
        "execute_script", "filter_contains_name"
    ));

    private static Set<String> oneIntCommands = new HashSet<>(Arrays.asList(
        "remove_by_id", "update"
    ));

    private static Set<String> elemArgCommands = new HashSet<>(Arrays.asList(
        "add", "add_if_min", "remove_greater", "remove_lower", "update"
    ));

    private static void printMsg(String msg) {
        System.out.println("Incorrect input," + msg);
    }

    private static boolean isPositivLong(String str) {
        try {
            return Long.parseLong(str) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean checkNoArgLine(String[] input) {
        if (input.length != 1) {
            printMsg("this command does not have args");
            return false;
        } else {
            return true;
        }
    }

    private static boolean checkOneIntLine(String[] input) {
        if (input.length != 2) {
            printMsg("this command needs 1 arg");
            return false;
        } else if(!isPositivLong(input[1])) {
            printMsg("id must be positiv long number");
            return false;
        } else {
            return true;
        }
    }

    private static boolean checkOneStringLine(String[] input) {
        if (input.length != 2) {
            printMsg("this command needs 1 arg");
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkFirstLine(String[] input) {
        if (input == null || input.length == 0) {
            printMsg("command must not be null");
            return false;
        } else if (noArgCommands.contains(input[0])) {
            return checkNoArgLine(input);
        } else if(oneIntCommands.contains(input[0])) {
            return checkOneIntLine(input);
        } else if(oneStringCommands.contains(input[0])) {
            return checkOneStringLine(input);
        } else {
            printMsg("to see available commands enter help");
            return false;
        }
    }

    public static boolean checkName(String input) {
        if (input == null || input.equals("")) {
            printMsg("this field must not be null");
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkRealHero(String str) {
        if (str != null && (str.equals("true") || str.equals("false"))) {
            return true;
        } else {
            printMsg("not a boolean");
            return false;
        }
    }

    public static boolean checkHasToothpick(String str) {
        if (str != null && (str.equals("true") || str.equals("false") || str.equals(""))) {
            return true;
        } else {
            printMsg("not a Boolean");
            return false;
        }
    }

    public static boolean checkInteger(String str) {
        if (str != null && isInteger(str)) {
            return true;
        } else {
            printMsg("not an integer");
            return false;
        }
    }

    public static boolean checkLong(String str) {
        if (str != null && isLong(str)) {
            return true;
        } else {
            printMsg("not a long integer");
            return false;
        }
    }

    public static boolean checkElemArg(String command) {
        return elemArgCommands.contains(command);
    }

    public static boolean checkWeapon(String weapon) {
        Set<String> weapons = new HashSet<>(Arrays.asList("HAMMER", "PISTOL", "MACHINE_GUN", "BAT"));
        if (weapons.contains(weapon.toUpperCase())) {
            return true;
        } else {
            printMsg("not a weapon type value");
            return false;
        }
    }

    public static boolean checkMood(String mood) {
        Set<String> moods = new HashSet<>(Arrays.asList("SADNESS", "LONGING", "APATHY", "FRENZY", "" ));
        if (moods.contains(mood.toUpperCase())) {
            return true;
        } else {
            printMsg("not a mood value");
            return false;
        }
    }
}
