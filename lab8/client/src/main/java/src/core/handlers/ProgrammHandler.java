package src.core.handlers;

import java.util.*;
import java.util.Scanner;
import java.io.*;

import src.core.readers.*;
import src.util.*;
import src.core.CoreThread;

public class ProgrammHandler {

    private static List<String> executedFiles = new ArrayList<>();

    private static HashSet<String> executableGuiCommands = new HashSet<>(
        Arrays.asList("add", "add_id_min", "remove_lower", "remove_greater", "remove_by_id", "update", "clear"));

    public Respond stopThread(CoreThread t) {
        try {
            t.setIsActive(false);
            return new Respond("Returning to the authoriztion page");
        } catch (Exception e) {
            return new Respond(e.getMessage());
        }
    }

    public Respond exit(ConnectionHandler connection) {
        try {
            connection.close();
            return new Respond("Programm terminated!");
        } catch (Exception e) {
            return new Respond(e.getMessage());
        } finally {
            System.exit(0);
        }
    }

    public Respond executeScript(String fileName, User user, CommandHandler handler) {
        if (executedFiles.contains(fileName)) {
            executedFiles.clear();
            return new Respond("This file was already executed!!!");
        } else {
            try (Scanner scanner = new Scanner(new File(fileName))) {
                AbstractReader reader = new ScriptReader(scanner);
                String[] line;
                Respond respond;
                try {
                    executedFiles.add(fileName);
                    while (scanner.hasNext()) {
                        line = reader.getFirstLine();
                        respond = handler.switchCommand(
                            line[0], user, line[1], CheckInput.checkElemArg(line[0]) ? reader.getHumanBeing() : null);
                        System.out.println(respond.getRespond());
                    }
                } catch (IllegalArgumentException e) {
                    executedFiles.clear();
                    return new Respond("Wrong data in the script " + fileName);
                }
            } catch (Exception e) {
                executedFiles.clear();
                return new Respond("No script with name " + fileName + ", or you can't read from it.");
            }
        }
        executedFiles.clear();
        return new Respond("ExecuteScript with name " + fileName + " complited!");
    }

    public Respond guiExecuteScript(String fileName, User user, CommandHandler handler) {
        if (executedFiles.contains(fileName)) {
            executedFiles.clear();
            return new Respond("This file was already executed!!!");
        } else {
            try (Scanner scanner = new Scanner(new File(fileName))) {
                AbstractReader reader = new ScriptReader(scanner);
                String[] line;
                Respond respond;
                try {
                    executedFiles.add(fileName);
                    while (scanner.hasNext()) {
                        line = reader.getFirstLine();
                        if (executableGuiCommands.contains(line[0]))
                            handler.switchCommand(
                                line[0], user, line[1], CheckInput.checkElemArg(line[0]) ? reader.getHumanBeing() : null);
                    }
                } catch (IllegalArgumentException e) {
                    executedFiles.clear();
                    return new Respond("Wrong data in the script " + fileName);
                }
            } catch (Exception e) {
                executedFiles.clear();
                return new Respond("No script with name " + fileName + ", or you can't read from it.");
            }
        }
        executedFiles.clear();
        return new Respond("ExecuteScript with name " + fileName + " complited!");
    }

}
