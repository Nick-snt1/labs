package src.run;

import java.util.PriorityQueue;
import java.io.Console;

import src.commands.*;
import src.elements.*;
import src.core.*;

/** Class, that run programme*/
public class Main {

    /**
        Run programme
        @param args command line arguments
    */
    public static void main(String[] args) {
        String fileName = System.getenv().get("FILENAME");

        CollectionHandler handler =
            new CollectionHandler(
                XMLHandler.readXML(fileName, new PriorityQueue<HumanBeing>()), fileName);

        AbstractReader reader = new InputReader();

        Invoker invoker = new Invoker(handler);

        System.out.println("Hello, now you can operate with collection of Human beings! Feel Power!!!");
        String[] line;
        while (true) {
            line = reader.getFirstLine();
            invoker.switchCommand(line, CheckInput.checkElemArg(line[0]) ? reader.getHumanBeing() : null);
        }
    }
}
