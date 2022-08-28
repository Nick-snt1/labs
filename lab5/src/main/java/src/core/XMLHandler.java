package src.core;

import javax.xml.namespace.QName;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.*;
import javax.xml.stream.*;
import java.io.*;

import java.util.function.Consumer;
import java.time.ZonedDateTime;
import java.util.*;

import src.elements.*;

/**
    Class, used to read data from xml file and write collection elements to xml file, using only static methods
*/
public class XMLHandler {

    /**
        Reads data from xml file with fileName to PriorityQueue
        @param fileName file, from HumanBeing objects reads
        @param queue collect to put elements in
        @return collection with HumanBeing objects inside
    */
    public static PriorityQueue<HumanBeing> readXML(String fileName, PriorityQueue<HumanBeing> queue) {

        Map<String, Integer> map = new HashMap<>();
        {
            map.put("name", 1); map.put("x", 2); map.put("y", 3); map.put("creationDate", 4);
            map.put("realHero", 5); map.put("hasToothpick", 6); map.put("impactSpeed", 7);
            map.put("soundtrackName", 8); map.put("weaponType", 9); map.put("mood", 10); map.put("car", 11);
        }

        XMLInputFactory inFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader =
                inFactory.createXMLEventReader(
                    new BufferedReader(new FileReader(fileName)));

            while (reader.hasNext()) {
                String[] fields = new String[12];
                XMLEvent event = reader.nextEvent();
                while (reader.hasNext() && !(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("human"))) {
                    event = reader.nextEvent();
                    if (event.isStartElement()) {
                        String field = event.asStartElement().getName().getLocalPart();
                        if (field.equals("human")) {
                            fields[0] = event.asStartElement().getAttributeByName(new QName("id")).getValue();
                        } else {
                            final XMLEvent current = reader.nextEvent();
                            if (current.isCharacters()) {
                                Optional.ofNullable(map.get(field)).ifPresent(i -> fields[i] = current.asCharacters().getData());
                            }
                        }
                    }
                }
                if (reader.hasNext()) {
                    queue.add(new HumanBeing(
                        Long.parseLong(fields[0]),
                        fields[1],
                        new Coordinates(Integer.parseInt(fields[2]), Long.valueOf(Long.parseLong(fields[3]))),
                        ZonedDateTime.parse(fields[4]),
                        Boolean.parseBoolean(fields[5]),
                        fields[6] == null ? null : Boolean.valueOf(Boolean.parseBoolean(fields[6])),
                        Integer.parseInt(fields[7]),
                        fields[8],
                        WeaponType.valueOf(fields[9]),
                        fields[10]== null ? null : Mood.valueOf(fields[10]),
                        fields[11] == null ? null : new Car(Boolean.parseBoolean(fields[11]))
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return queue;
    }

    /**
        Writes elements from queue to file, if it is empty
        @param fileName file, were HumanBeing objects puts
        @param queue collection, which elements writes to file
    */
    public static void writeXML(String fileName, PriorityQueue<HumanBeing> queue) {
        XMLOutputFactory outFactory = XMLOutputFactory.newInstance();
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        try {
            XMLEventWriter writer =
                outFactory.createXMLEventWriter(new FileOutputStream(fileName));

            writer.add(eventFactory.createStartDocument("utf-8"));
            writer.add(eventFactory.createStartElement("", null, "collection"));
            writeCollection(writer, queue);
            writer.add(eventFactory.createEndElement("", null, "collection"));
        } catch (IOException | XMLStreamException e) {

        }
    }

    /**
        Writes elements from queue to end of the file, if it is not empty
        @param fileName file, were HumanBeing objects puts
        @param queue collection, which elements writes to file
        @throws FileNotFoundException if no file is found
    */
    public static void reWriteXML(String fileName, PriorityQueue<HumanBeing> queue) throws FileNotFoundException {
        XMLInputFactory inFactory = XMLInputFactory.newInstance();
        XMLOutputFactory outFactory = XMLOutputFactory.newInstance();
        try {
            XMLEventReader reader =
                inFactory.createXMLEventReader(
                    new BufferedReader(new FileReader(fileName)));

            XMLEventWriter writer =
                outFactory.createXMLEventWriter(new FileOutputStream(fileName));

            XMLEvent event = null;
            while(reader.hasNext()) {
                event = reader.nextEvent();
                if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("collection"))
                    break;
                writer.add(event);
            }
            writeCollection(writer, queue);
            writer.add(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
        Write current collection queue with XMLEventWriter writer
        @param writer tool, with wich collection elements gonna put ti file
        @param queue human's collection
    */
    private static void writeCollection(XMLEventWriter writer, PriorityQueue<HumanBeing> queue) {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        queue.iterator().forEachRemaining(human -> {
            try {
                writer.add(eventFactory.createStartElement("", null, "human"));
                writer.add(eventFactory.createAttribute("id", "" + human.getId()));

                    writer.add(eventFactory.createStartElement("", null, "name"));
                    writer.add(eventFactory.createCharacters(human.getName()));
                    writer.add(eventFactory.createEndElement("", null, "name"));

                    writer.add(eventFactory.createStartElement("", null, "coordinates"));
                        writer.add(eventFactory.createStartElement("", null, "x"));
                        writer.add(eventFactory.createCharacters("" + human.getCoordinates().getX()));
                        writer.add(eventFactory.createEndElement("", null, "x"));

                        writer.add(eventFactory.createStartElement("", null, "y"));
                        writer.add(eventFactory.createCharacters(human.getCoordinates().getY().toString()));
                        writer.add(eventFactory.createEndElement("", null, "y"));
                    writer.add(eventFactory.createEndElement("", null, "coordinates"));

                    writer.add(eventFactory.createStartElement("", null, "creationDate"));
                    writer.add(eventFactory.createCharacters(human.getCreationDate().toString()));
                    writer.add(eventFactory.createEndElement("", null, "creationDate"));

                    writer.add(eventFactory.createStartElement("", null, "realHero"));
                    writer.add(eventFactory.createCharacters("" + human.isRealHero()));
                    writer.add(eventFactory.createEndElement("", null, "realHero"));

                    writer.add(eventFactory.createStartElement("", null, "hasToothpick"));
                    human.hasToothpick().ifPresent(x -> {
                        try {
                            writer.add(eventFactory.createCharacters("" + x));
                        } catch (XMLStreamException e) {
                            e.printStackTrace();
                        }
                    });
                    writer.add(eventFactory.createEndElement("", null, "hasToothpick"));

                    writer.add(eventFactory.createStartElement("", null, "impactSpeed"));
                    writer.add(eventFactory.createCharacters("" + human.getImpactSpeed()));
                    writer.add(eventFactory.createEndElement("", null, "impactSpeed"));

                    writer.add(eventFactory.createStartElement("", null, "soundtrackName"));
                    writer.add(eventFactory.createCharacters(human.getSoundtrackName()));
                    writer.add(eventFactory.createEndElement("", null, "soundtrackName"));

                    writer.add(eventFactory.createStartElement("", null, "weaponType"));
                    writer.add(eventFactory.createCharacters(human.getWeaponType().name()));
                    writer.add(eventFactory.createEndElement("", null, "weaponType"));

                    writer.add(eventFactory.createStartElement("", null, "mood"));
                    human.getMood().ifPresent(x -> {
                        try {
                            writer.add(eventFactory.createCharacters(x.name()));
                        } catch (XMLStreamException e) {
                            e.printStackTrace();
                        }
                    });
                    writer.add(eventFactory.createEndElement("", null, "mood"));

                    writer.add(eventFactory.createStartElement("", null, "car"));
                    human.getCar().ifPresent(x -> {
                        try {
                            writer.add(eventFactory.createCharacters("" + x.isCool()));
                        } catch (XMLStreamException e) {
                            e.printStackTrace();
                        }
                    });
                    writer.add(eventFactory.createEndElement("", null, "car"));

                writer.add(eventFactory.createEndElement("", null, "human"));
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        });
    }
}
