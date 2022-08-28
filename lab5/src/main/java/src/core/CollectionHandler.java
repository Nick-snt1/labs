package src.core;

import java.time.ZonedDateTime;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.*;
import java.io.*;

import src.commands.*;
import src.elements.*;

/**
    Class, used to store PriorityQueue collection of HumanBeings,
    and operate whith it.
    @see PriorityQueue
*/
public class CollectionHandler {

    /** Name of xml file, from which programme reads HumanBeing objects */
    private String fileName;

    /** Collection, used to store HumanBeing objects */
    private PriorityQueue<HumanBeing> queue;

    /** Date, when collection was created */
    private final java.time.ZonedDateTime creationDate;

    /** Names of files, which was already executed */
    private static List<String> executedFiles = new ArrayList<>();

    /**
        Creates new CollectionHandler object
        @param queue collection to operate with
        @param fileName name of xml file
    */
    public CollectionHandler(PriorityQueue<HumanBeing> queue, String fileName) {
        this.creationDate = ZonedDateTime.now();
        this.fileName = fileName;
        this.queue = queue;

    }

    /**
        Adds new element to the collection
        @param element HumanBeing objet
    */
    public void add(HumanBeing element) {
      queue.add(element);
      System.out.println("Add Complete succesfully");
    }

    /**
        Updates the value of a collection element, which id is equals to given one
        @param  id unic human's positiv id
        @param human HumanBeing objet to put in collection
    */
    public void updateById(long id, HumanBeing human) {
        HumanBeing[] array = queue.toArray(new HumanBeing[0]);
        boolean flag = false;
        for (int i = 0; i < array.length; i++) {
          if (array[i].getId() == id) {
            array[i] = human;
            flag = true;
          }
        }
        queue = new PriorityQueue<HumanBeing>(Arrays.asList(array));
        System.out.println(flag ? "UpdateById Complete succesfully" : "No element with id " + id );
    }

    /**
        Removes from the collection all elements, that are lower than given one
        @param human HumanBeing object to compare with
    */
    public void removeLower(HumanBeing human) {
      boolean flag = false;
        while (queue.peek() != null && queue.peek().compareTo(human) < 0) {
            queue.poll();
            flag = true;
        }
        System.out.println(flag ? "RemoveLower Complete succesfully" : "No elements, lower that given one" );
    }

    /**
        Removes from the collection all elements, which are bigger than given one
        @param human HumanBeing object to compare with
    */
    public void removeGreater(HumanBeing human) {
        PriorityQueue<HumanBeing> queue1
            = new PriorityQueue<>(queue.stream().filter(x -> x.compareTo(human) < 0).collect(Collectors.toList()));
        System.out.println(queue.size() != queue1.size() ? "There are no elemets bigger that the given" : "RemoveGreater complete succesfully");
    }

    /**
        Removes element from the collection by it's id
        @param id value to compare collection objects
    */
    public void removeById(long id) {
      HumanBeing[] array = queue.toArray(new HumanBeing[0]);
      boolean flag = false;
      for (int i = 0; i < array.length; i++) if (array[i].getId() == id) flag = true;
      queue = new PriorityQueue<HumanBeing>(
          queue.stream().filter(x -> x.getId() != id).collect(Collectors.toList()));

      System.out.println(flag ? "RemoveById complete succesfully" : "No element with id " + id );

    }

    /**
        Adds new element to the collection if it's value is lower than the value of the least element
        @param human HumanBeings object to insert into collection
    */
    public void addIfMin(HumanBeing human) {
        boolean flag = false;
        if (queue.peek() != null && queue.peek().compareTo(human) > 0) {
            queue.add(human);
            flag = true;
        }
        System.out.println(flag ? "AddIfMin complete succesfully" : "Given element is bigger than the least at the collection" );
    }

    /**
        Prints elements, which value of the field name contains given substring
        @param name substring to comapre with
    */
    public void filterContainsName(String name) {
        queue.stream().filter(x -> x.getName().contains(name)).forEach(x -> System.out.println(x.toString()));
    }

    /** Prints all elements of the collection in ascending order */
    public void printAscending() { show(); }

    /** Prints all elements of the collection in descending order */
    public void printDescending() {
        HumanBeing[] array = queue.toArray(new HumanBeing[0]);
        for (int i = array.length-1; i >= 0; i--)
            System.out.println(array[i].toString());
    }

    /** Removes all elements from the collection */
    public void clear() { queue.clear(); }

    //public int getSize() { return queue.size(); }

    /** Saves collection to the file */
    public void save() {
        try {
            XMLHandler.reWriteXML(fileName, queue);
        } catch (Exception e) {
            XMLHandler.writeXML(fileName, queue);
        }
    }

    /** Terminates programm without saving the collection */
    public void exit() {
        System.exit(0);
    }

    /**
        Reads and execute script from the given file, use executedFiles field to stop execution
        if current script name were in previous script
        @param fileName name of script
        @param invoker object, which will execute commands
    */
    public void executeScript(String fileName, Invoker invoker) {
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
                        invoker.switchCommand(line, CheckInput.checkElemArg(line[0]) ? reader.getHumanBeing() : null);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Wrong data in the script");
                }
            } catch (FileNotFoundException e) {
                System.out.println("No script with name " + fileName);
            }
        }

    }

    /** Prints all elements of the collection to standard output */
    public void show() {
        queue.iterator().forEachRemaining(
            x -> System.out.println(
                "HumanBeing with id " + x.getId() +
                "with name " + x.getName() +
                System.lineSeparator() +
                "Now at the x=" + x.getCoordinates().getX() + " and y=" + x.getCoordinates().getY() +
                System.lineSeparator() +
                "Was created at " + x.getCreationDate().toLocalDateTime() +
                System.lineSeparator() +
                "He is " + (x.isRealHero() ? "a real hero": "not a real hero") +
                System.lineSeparator() +
                "He " + (x.hasToothpick().isPresent() && x.hasToothpick().get() == true ? "has a toothpick" : "doesn't has a toothpick") +
                System.lineSeparator() +
                "His impact speed is " + x.getImpactSpeed() + ", and he listens to " + x.getSoundtrackName() +
                System.lineSeparator() +
                "He got a weapon " + x.getWeaponType() +", and his mood is " + x.getMood() +
                System.lineSeparator() +
                "He " + (x.getCar().isPresent() ? ( x.getCar().get().isCool() ? "have a cool car" : "have a not cool car") : "don't have a car") +
                System.lineSeparator()
            )
        );
    }

    /** Prints information about the collection to standard output */
    public void info() {
        String[] out = {
            "Collection type: PriorityQueue - the elements of this collection are ordered according to their natural ordering",
            "Collection elements type: HumanBeing - person with the gun in a not very nice mood, riding somewere",
            "Creation date: " + creationDate.toString(),
            "Size of the collection: " + queue.size() + (queue.size() != 1 ?  " elements" : " element")
        };
        System.out.println(String.join(System.lineSeparator(), out));
    }

    /** Prints all available commands to standard input */
    public void help() {
        String[] out = {"This is the list of all available commands:",
            "info: print information about the collection to standard output",
            "show: print all elements of the collection to standard output",
            "add {element}: add new element to the collection",
            "update id {element}: update the value of a collection element, which id is equals to given one",
            "remove_by_id id: remove element from the collection by it's id",
            "clear: remove all elements from the collection",
            "save: save collection to file",
            "execute_script file_name: read and execute script from the given file",
            "exit: terminate programm without saving the collection",
            "add_if_min {element}: add new element to the collection if it's value is lower, than the value of the least element",
            "remove_greater {element}: remove from the collection all elements, that are bigger than given one",
            "remove_lower {element}: remove from the collection all elements, that are lower than given one",
            "filter_contains_name name: print elements, which value of the field name contains given substring",
            "print_ascending: print all elements of the collection in ascending order",
            "print_descending: print all elements of the collection in descending order",
            "And of course use command help to see all available commands, but you already know about it"
        };
        System.out.println(String.join(System.lineSeparator() + System.lineSeparator(), out));
    }

}
