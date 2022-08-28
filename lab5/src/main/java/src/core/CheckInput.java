package src.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import src.elements.*;

/**
   Сlass, consisting of only static methods,
   using to check if the input is appropriate
   to use in the programme, and to print a message if it doesn't
*/
public class CheckInput {

    /** Set of commands, wich do not takes arguments */
    private static Set<String> noArgCommands = new HashSet<>(Arrays.asList(
        "help", "info", "show", "clear", "save", "exit",
        "print_ascending", "print_descending","add",
        "add_if_min", "remove_greater", "remove_lower"
    ));

    /** Set of commands, wich takes String type argument */
    private static Set<String> oneStringCommands = new HashSet<>(Arrays.asList(
        "execute_script", "filter_contains_name"
    ));

    /** Set of commands, wich takes Integer type argument */
    private static Set<String> oneIntCommands = new HashSet<>(Arrays.asList(
        "remove_by_id", "update"
    ));

    /** Set of commands, wich takes HumanBeing type argument */
    private static Set<String> elemArgCommands = new HashSet<>(Arrays.asList(
        "add", "add_if_min", "remove_greater", "remove_lower", "update"
    ));

    /**
        Prints message, telling about mistake, which arises  during reading input
    */
    private static void printMsg(String msg) {
        System.out.println("Incorrect input," + msg);
    }

    /**
        Check if str is positiv long value
        @param str input string
        @return if string is positiv long value
    */
    private static boolean isPositivLong(String str) {
        try {
            return Long.parseLong(str) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
        Check if str is long value
        @param str input string
        @return if string is long value
    */
    private static boolean isLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
        Check if str is integer value
        @param str input string
        @return if string is integer value
    */
    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
        Check if input is name of command without arguments, prints message if input is incorrect
        @param input array, which first element is command name
        @return if input is name of command without arguments
    */
    private static boolean checkNoArgLine(String[] input) {
        if (input.length != 1) {
            printMsg("this command does not have args");
            return false;
        } else {
            return true;
        }
    }

    /**
        Check if input is name of command with one long argument, prints message if input is incorrect
        @param input array, which first element is command name and the second is argumnet
        @return if input is name of command with one long argumnet
    */
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

    /**
        Check if input is name of command with one long argument, prints message if input is incorrect
        @param input array, which first element is command name and the second is argumnet
        @return if input is name of command with one long argumnet
    */
    private static boolean checkOneStringLine(String[] input) {
        if (input.length != 2) {
            printMsg("this command needs 1 arg");
            return false;
        } else {
            return true;
        }
    }

    /**
        Check if the first line of user's input contains command name,
        and optionally arguments, prints message if input is incorrect
        @param input array, which first element is command name and the second is null or argumnet
        @return if input is name of command with optional argument
    */
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

    /**
        Check if input string is not empty, prints message if input is incorrect
        @param input user's input
        @return if string is not empty
    */
    public static boolean checkName(String input) {
        if (input == null || input.equals("")) {
            printMsg("this field must not be null");
            return false;
        } else {
            return true;
        }
    }

    /**
        Check if input is x and y coordinates, prints message if input is incorrect,
        prints message if input is incorrect
        @param input array, which first element is x-coordinate and second is y-coordinate
        @return if input is appropriate x and y value
    */
    public static boolean checkCoordinates(String[] input) {
        if (input == null) {
            printMsg("filed coordinates must not be null");
            return false;
        } else if (input.length != 2) {
            printMsg("please, enter two parametrs");
            return false;
        } else if (!isInteger(input[0])) {
            printMsg("coordinate x must be integer");
            return false;
        } else if (!isLong(input[1])) {
            printMsg("coordinate y must be long integer");
            return false;
        } else {
            return true;
        }
    }

    /**
        Check if input string is boolean value, prints message if input is incorrect
        @param str user's input
        @return if string is boolean value
    */
    public static boolean checkRealHero(String str) {
        if (str != null && (str.equals("true") || str.equals("false"))) {
            return true;
        } else {
            printMsg("not a boolean");
            return false;
        }
    }

    /**
        Check if str is boolean or null value, prints message if input is incorrect
        @param str user's input
        @return if string is boolean or null value
    */
    public static boolean checkHasToothpick(String str) {
        if (str != null && (str.equals("true") || str.equals("false") || str.equals(""))) {
            return true;
        } else {
            printMsg("not a Boolean");
            return false;
        }
    }

    /**
        Check if str is integer value, prints message if input is incorrect
        @param str input string
        @return if string is integer value
    */
    public static boolean checkInteger(String str) {
        if (str != null && isInteger(str)) {
            return true;
        } else {
            printMsg("not an integer");
            return false;
        }
    }

    /**
        Check if command takes HumanBeing object as an argument
        @param command user's input
        @return if command takes HumanBeing object as an argument
    */
    public static boolean checkElemArg(String command) {
        return elemArgCommands.contains(command);
    }

    /**
        Check if input string is appropriate weaponType value, prints message if input is incorrect
        @param weapon user's input
        @return if weapon is appropriate weaponType value
    */
    public static boolean checkWeapon(String weapon) {
        try {
            WeaponType w = WeaponType.valueOf(weapon.toUpperCase());
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            printMsg("not a weapon type value");
            return false;
        }
    }

    /**
        Check if input string is appropriate Mood value, prints message if input is incorrect
        @param mood user's input
        @return if mood is appropriate Mood value
    */
    public static boolean checkMood(String mood) {
        if (mood.equals("")) return true;
        try {
            Mood w = Mood.valueOf(mood.toUpperCase());
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            printMsg("not a mood value");
            return false;
        }
    }
}
