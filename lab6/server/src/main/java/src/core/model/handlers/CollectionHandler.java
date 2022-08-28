package src.core.model.handlers;

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
import java.util.Comparator;
import java.util.logging.*;

import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.*;
import java.io.*;

import src.core.model.commands.*;
import src.core.model.elements.*;

public class CollectionHandler {

    private String fileName;

    private PriorityQueue<HumanBeing> queue;

    private final java.time.ZonedDateTime creationDate;

    private static final Logger LOGGER = Logger.getLogger(CollectionHandler.class.getName());

    public CollectionHandler(PriorityQueue<HumanBeing> queue, String fileName) {
        this.creationDate = ZonedDateTime.now();
        this.fileName = fileName;
        this.queue = queue;
        LOGGER.log(Level.INFO, "New CollectionHandler created at {0}", creationDate.toLocalDateTime());
    }

    public String add(HumanBeing element) {
      queue.add(element);
      LOGGER.log(Level.INFO, "HumanBeing with id {0} added to the collection", element.getId());
      return "Add Complete succesfully";
    }

    public String removeLower(HumanBeing human) {
        PriorityQueue<HumanBeing> queue1 =
            new PriorityQueue<>(
                queue.stream().filter(x -> x.compareTo(human) < 0).collect(Collectors.toList()));
        int size = queue.size();
        queue = queue1;
        LOGGER.log(Level.INFO, "Command removeLower executed with HumanBeing {0}", human.getId());
        return queue1.size() == size ? "No elements, lower that given one" : "RemoveLower Complete succesfully";
    }

    public String removeGreater(HumanBeing human) {
        PriorityQueue<HumanBeing> queue1
            = new PriorityQueue<>(
                queue.stream().filter(x -> x.compareTo(human) > 0).collect(Collectors.toList()));
        int size = queue.size();
        queue = queue1;
        LOGGER.log(Level.INFO, "Command removeGreater executed with HumanBeing {0}", human.getId());
        return queue1.size() == size ? "There are no elemets bigger that the given" : "RemoveGreater complete succesfully";
    }

    public String removeById(long id) {
      HumanBeing[] array = queue.toArray(new HumanBeing[0]);
      boolean flag = false;
      for (int i = 0; i < array.length; i++) if (array[i].getId() == id) flag = true;
      queue = new PriorityQueue<HumanBeing>(
          queue.stream().filter(x -> x.getId() != id).collect(Collectors.toList()));
      LOGGER.log(Level.INFO, "Command removeById executed with id {0}", id);
      return flag ? "RemoveById complete succesfully" : "No element with id " + id;

    }

    public String addIfMin(HumanBeing human) {
        boolean flag = false;
        if (queue.peek() != null && queue.peek().compareTo(human) > 0) {
            queue.add(human);
            flag = true;
        }
        LOGGER.log(Level.INFO, "Command addIfMin executed with HumanBeing {0}", human.getId());
        return flag ? "AddIfMin complete succesfully" : "Given element is bigger than the least at the collection";
    }

    public String filterContainsName(String name) {
        String result = queue.stream()
            .filter(x -> x.getName().contains(name))
            .sorted(Comparator.comparing(x -> x.getCoordinates().getX() + x.getCoordinates().getY()))
            .map(x -> System.lineSeparator() + x.toString() + System.lineSeparator())
            .reduce("", (a, b) -> a + b);
        LOGGER.log(Level.INFO, "Command filterContainsName executed with name {0}", name);
        return result.equals("") ? "No humans with substring " + name + " in name" : result;
    }

    public String printAscending() {
        String  result = queue.stream()
            .sorted(Comparator.comparing(x -> x.getCoordinates().getX() + x.getCoordinates().getY()))
            .map(x -> x.toString())
            .reduce("", (a, b) -> a + System.lineSeparator() + b + System.lineSeparator());
        LOGGER.log(Level.INFO, "Command printAscending executed");
        return result.equals("") ? "Collection is empty" : result;
    }

    public String printDescending() {
        HumanBeing[] array = queue.toArray(new HumanBeing[0]);
        HumanBeing[] reverseArray = new HumanBeing[array.length];
        for (int i = 0; i < array.length; i++ ) reverseArray[i] = array[array.length - 1 - i];
        String result = Arrays.asList(reverseArray).stream()
            .sorted(Comparator.comparing(x -> x.getCoordinates().getX() + x.getCoordinates().getY()))
            .map(x ->System.lineSeparator() + x.toString() + System.lineSeparator())
            .reduce("", (a, b) -> a + b);
        LOGGER.log(Level.INFO, "Command printDescending executed");
        return result.equals("") ? "Collection is empty" : result;
    }

    public String updateById(long id, HumanBeing human) {
        HumanBeing[] array = queue.toArray(new HumanBeing[0]);
        boolean flag = false;
        for (int i = 0; i < array.length; i++) {
          if (array[i].getId() == id) {
            array[i] = human;
            flag = true;
          }
        }
        queue = new PriorityQueue<HumanBeing>(Arrays.asList(array));
        LOGGER.log(Level.INFO, "Command updateById executed with id {0}", id);
        return flag ? "UpdateById Complete succesfully" : "No element with id " + id;
    }

    public String clear() {
         queue.clear();
         LOGGER.info("Command clear executed");
         return "Collection is empty now";
     }

    public String save() {
        try {
            XMLHandler.reWriteXMLNew(fileName, queue);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            XMLHandler.writeXMLNew(fileName, queue);
        }
        LOGGER.info("Command save executed");
        return "Saved succesfully";
    }

    public String show() {
        String result = queue.stream()
            .sorted(Comparator.comparing(x -> x.getCoordinates().getX() + x.getCoordinates().getY()))
            .map(x -> System.lineSeparator() + "HumanBeing with id " + x.getId() +
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
        ).reduce("", (a, b) -> a + b);
        LOGGER.info("Command show executed");
        return result.equals("") ? "Collection is empty" : result;
    }

    public String info() {
        String[] out = {
            System.lineSeparator() + "Collection type: PriorityQueue - the elements of this collection are ordered according to their natural ordering",
            "Collection elements type: HumanBeing - person with the gun in a not very nice mood, riding somewere",
            "Creation date: " + creationDate.toString(),
            "Size of the collection: " + queue.size() + (queue.size() != 1 ?  " elements" : " element") + System.lineSeparator()
        };
        LOGGER.info("Command info executed");
        return String.join(System.lineSeparator(), out);
    }

    public String help() {
        String[] out = {
            System.lineSeparator() + "This is the list of all available commands:",
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
            "And of course use command help to see all available commands, but you already know about it" + System.lineSeparator()
        };
        LOGGER.info("Command help executed");
        return String.join(System.lineSeparator(), out);
    }

}
