package src.core.handlers;

import java.util.*;
import java.util.Scanner;
import java.io.*;

import src.core.readers.*;
import src.util.*;
import src.core.CoreThread;

public class ProgrammHandler {

    private static List<String> executedFiles = new ArrayList<>();

    public String stopThread(CoreThread t) {
        try {
            t.setIsActive(false);
            return "Returning to the authoriztion page";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String exit(ConnectionHandler connection) {
        try {
            connection.close();
            return "Programm terminated!";
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            System.exit(0);
        }
    }

    public String executeScript(String fileName, User user, CommandHandler handler) {
        if (executedFiles.contains(fileName)) {
            executedFiles.clear();
            return "This file was already executed!!!";
        } else {
            try (Scanner scanner = new Scanner(new File(fileName))) {
                AbstractReader reader = new ScriptReader(scanner);
                String[] line;
                String respond;
                try {
                    executedFiles.add(fileName);
                    while (scanner.hasNext()) {
                        line = reader.getFirstLine();
                        respond = handler.switchCommand(
                            line[0], user, line[1], CheckInput.checkElemArg(line[0]) ? reader.getHumanBeing() : null);
                        System.out.println(respond);
                    }
                } catch (IllegalArgumentException e) {
                    executedFiles.clear();
                    return "Wrong data in the script " + fileName;
                }
            } catch (Exception e) {
                executedFiles.clear();
                return "No script with name " + fileName + ", or you can't read from it.";
            }
        }
        executedFiles.clear();
        return "ExecuteScript with name " + fileName + " complited!";
    }

}
