package src.core.handlers;

import java.util.*;
import java.util.Scanner;
import java.io.*;

import src.core.readers.*;

public class ProgrammHandler {

    private static List<String> executedFiles = new ArrayList<>();

    public ProgrammHandler() {
    }

    public void exit(ConnectionHandler connection) {
        try {
            connection.close();
            System.out.println("Programm terminated!");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeScript(String fileName, CommandHandler handler) {
        if (executedFiles.contains(fileName)) {
            System.out.println("This file was already executed!!!");//rewrite message
            executedFiles.clear();
        } else {
            executedFiles.add(fileName);
            try (Scanner scanner = new Scanner(new File(fileName))) {
                AbstractReader reader = new ScriptReader(scanner);
                String[] line;
                try {
                    while (scanner.hasNext()) {
                        line = reader.getFirstLine();
                        handler.switchCommand(
                            line[0], line[1], CheckInput.checkElemArg(line[0]) ? reader.getHumanBeing() : null);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Wrong data in the script");
                }
            } catch (Exception e) {
                System.out.println("No script with name " + fileName + ", or you can't read from it.");
            }
        }
        executedFiles.clear();
        System.out.println("ExecuteScript complited!");
    }

}
