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
import src.util.User;
import src.util.Respond;

public class CollectionHandler {

    private PriorityQueue<HumanBeing> queue;

    private final java.time.ZonedDateTime creationDate;

    public CollectionHandler(PriorityQueue<HumanBeing> queue) {
        this.creationDate = ZonedDateTime.now();
        this.queue = queue;
    }

    public synchronized Respond add(HumanBeing element) {
      queue.add(element);
      return new Respond("Add Complete succesfully", getHumansFields(element));
    }


    public synchronized Respond guiShow() {
        return new Respond("", null, queue.stream().map(x -> getHumansFields(x)).toArray(Object[][]::new));
    }

    private Object[] getHumansFields(HumanBeing h) {
        return new Object[] {
            h.getId(),
            h.getName(),
            h.getCreationDate().toLocalDate(),
            h.getCoordinates().getX(),
            h.getCoordinates().getY().intValue(),
            "" + h.isRealHero(),
            h.hasToothpick().isPresent() ? "" + h.hasToothpick().get() : "null",
            h.getImpactSpeed(),
            h.getSoundtrackName(),
            "" + h.getWeaponType(),
            h.getMood().isPresent() ? "" + h.getMood().get() : "null",
            h.getCar().isPresent() ?  "" + h.getCar().get().isCool() : "null",
            h.getOwner().getLogin() };
    }

    public synchronized Respond removeLower(HumanBeing human) {
        Object[] data = queue.stream().filter(
            x -> !(x.getOwner().equals(human.getOwner())
                && x.compareTo(human) > 0 )).map(x -> x.getId()).toArray();

        queue = new PriorityQueue<>(
            queue.stream().filter(
                x -> !x.getOwner().equals(human.getOwner())
                    || (x.getOwner().equals(human.getOwner())
                        && x.compareTo(human) > 0)).collect(Collectors.toList()));
        return new Respond("RemoveLower Complete succesfully", data);
    }

    public synchronized Respond removeGreater(HumanBeing human) {
        Object[] data = queue.stream().filter(
            x -> !(x.getOwner().equals(human.getOwner())
                && x.compareTo(human) < 0) ).map(x -> x.getId()).toArray();

        queue = new PriorityQueue<>(
            queue.stream().filter(
                x -> !x.getOwner().equals(human.getOwner())
                    || (x.getOwner().equals(human.getOwner())
                        && x.compareTo(human) < 0)).collect(Collectors.toList()));
        return new Respond("RemoveGreater complete succesfully", data);
    }

    public synchronized Respond removeById(long id, User user) {
      queue = new PriorityQueue<>(
          queue.stream().filter(
                x -> !x.getOwner().equals(user)
                    || (x.getOwner().equals(user)
                        && x.getId() != id)).collect(Collectors.toList()));
      return new Respond("RemoveById complete succesfully", new Object[] {id});
    }

    public synchronized Respond addIfMin(HumanBeing human) {
        PriorityQueue<HumanBeing> queue1 = new PriorityQueue<>(
            queue.stream().filter(
                x -> x.getOwner().equals(human.getOwner())
                    && x.compareTo(human) < 0).collect(Collectors.toList()));

        if (queue1.size() == 0) add(human);

        return queue1.size() == 0 ?
            new Respond("AddIfMin complete succesfully", getHumansFields(human)) :
                new Respond("Given element is bigger than the least at the collection");
    }

    public synchronized Respond updateById(long id, HumanBeing human) {
        HumanBeing[] array = queue.toArray(new HumanBeing[0]);

        for (int i = 0; i < array.length; i++) if (array[i].getId() == id) array[i] = human.setId(id);
        queue = new PriorityQueue<HumanBeing>(Arrays.asList(array));

        return new Respond("UpdateById Complete succesfully", getHumansFields(human.setId(id)));
    }

    public synchronized Respond clear(User user) {
        Object[] data = queue.stream().filter(x -> x.getOwner().equals(user)).map(x -> x.getId()).toArray();
         queue = new PriorityQueue<> (
            queue.stream().filter(x -> !x.getOwner().equals(user)).collect(Collectors.toList()));
         return new Respond("Your Collection is empty now", data);
     }

     public synchronized Respond info(User user) {
         String[] out = {
             System.lineSeparator() +
             "Your login is " + user.getLogin(),
             "Size of your collection: " + queue.stream().filter(x -> x.getOwner().equals(user)).count(),
             "Collection type: PriorityQueue - the elements of this collection are ordered according to their natural ordering",
             "Collection elements type: HumanBeing - person with the gun in a not very nice mood, riding somewere",
             "Creation date: " + creationDate.toString(),
             "Size of the whole collection: " + queue.size() + (queue.size() != 1 ?  " elements" : " element") + System.lineSeparator()
         };
         return new Respond(String.join(System.lineSeparator(), out));
     }

    public synchronized Respond filterContainsName(String name) {
        StringBuilder result = queue.stream()
            .filter(x -> x.getName().contains(name))
            .map(x -> new StringBuilder(System.lineSeparator()).append(x.toString()).append(System.lineSeparator()))
            .reduce(new StringBuilder(), (a, b) -> a.append(b));
        return new Respond (result.equals("") ? "No humans with substring " + name + " in name" : result.toString());
    }

    public synchronized Respond printAscending() {
        StringBuilder  result = queue.stream()
            .map(x -> new StringBuilder(System.lineSeparator())
                .append(x.toString()).append(System.lineSeparator()))
            .reduce(new StringBuilder(), (a,b) -> a.append(b));

        return new Respond(result.equals("") ? "Collection is empty" : result.toString());
    }

    public synchronized Respond printDescending() {
        HumanBeing[] array = queue.toArray(new HumanBeing[0]);
        HumanBeing[] reverseArray = new HumanBeing[array.length];
        for (int i = 0; i < array.length; i++ ) reverseArray[i] = array[array.length - 1 - i];
        StringBuilder result = Arrays.asList(reverseArray).stream()
            .map(x -> new StringBuilder(System.lineSeparator())
                .append(x.toString())
                .append(System.lineSeparator()))
            .reduce(new StringBuilder(), (a, b) -> a.append(b));
        return new Respond(result.equals("") ? "Collection is empty" : result.toString());
    }

    public Respond save() {
        return new Respond("Saved succesfully");
    }

    public synchronized Respond show() {
        StringBuilder result = queue.stream()
            .map(x -> new StringBuilder(System.lineSeparator()).append("HumanBeing with id ")
                .append(x.getId()).append("with name ").append(x.getName())
                .append(System.lineSeparator()).append("Now at the x=")
                .append(x.getCoordinates().getX()).append(" and y=")
                .append(x.getCoordinates().getY()).append(System.lineSeparator())
                .append("Was created at ").append(x.getCreationDate().toLocalDateTime())
                .append(System.lineSeparator()).append("He is ")
                .append((x.isRealHero() ? "a real hero": "not a real hero"))
                .append(System.lineSeparator()).append("He ")
                .append( (x.hasToothpick().isPresent() && x.hasToothpick().get() == true ? "has a toothpick" : "doesn't has a toothpick"))
                .append(System.lineSeparator()).append("His impact speed is ")
                .append(x.getImpactSpeed()).append(", and he listens to ")
                .append(x.getSoundtrackName()).append(System.lineSeparator()).append("He got a weapon ")
                .append(x.getWeaponType()).append(", and his mood is ").append(x.getMood())
                .append(System.lineSeparator()).append("He ")
                .append((x.getCar().isPresent() ? ( x.getCar().get().isCool() ? "have a cool car" : "have a not cool car") : "don't have a car"))
                .append(System.lineSeparator())
            ).reduce(new StringBuilder(), (a, b) -> a.append(b));
        return new Respond(result.equals("") ? "Collection is empty" : result.toString());
    }

    public Respond help() {
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
        return new Respond(String.join(System.lineSeparator(), out));
    }

}

/**
public String removeLower(HumanBeing human) {
  boolean flag = false;
    while (queue.peek() != null && queue.peek().compareTo(human) < 0) {
        queue.poll();
        flag = true;
    }
    return flag ? "RemoveLower Complete succesfully" : "No elements, lower that given one";
*/
