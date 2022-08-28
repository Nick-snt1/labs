package src.core.model.handlers;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.namespace.QName;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.*;
import javax.xml.stream.*;
import java.io.*;
import java.nio.file.*;

import java.util.function.Consumer;
import java.util.logging.*;
import java.util.*;

import java.time.ZonedDateTime;

import src.core.model.elements.*;

public class XMLHandler {

    private static final Logger LOGGER = Logger.getLogger(XMLHandler.class.getName());

    public static PriorityQueue<HumanBeing> readXML(String fileName, PriorityQueue<HumanBeing> queue) {
        LOGGER.info("Method readXML started with fileName " + fileName);
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
                    queue.add(new HumanBeing( Long.parseLong(fields[0]), fields[1],
                        new Coordinates(Integer.parseInt(fields[2]), Long.valueOf(Long.parseLong(fields[3]))),
                        ZonedDateTime.parse(fields[4]), Boolean.parseBoolean(fields[5]),
                        fields[6] == null ? null : Boolean.valueOf(Boolean.parseBoolean(fields[6])),
                        Integer.parseInt(fields[7]), fields[8], WeaponType.valueOf(fields[9]),
                        fields[10]== null ? null : Mood.valueOf(fields[10]),
                        fields[11] == null ? null : new Car(Boolean.parseBoolean(fields[11])),
                        null
                    ));
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            System.out.println("No file with name " + fileName);
        }
        return queue;
    }

    public static void writeXMLNew(String fileName, PriorityQueue<HumanBeing> queue) {
        LOGGER.log(Level.INFO, "Method writeXMLNew executed with fileName {0}", fileName);
        try {
            Files.createFile(Paths.get(fileName));
            String result =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><collection>" + collectionToString(queue) + "</collection>";
            writeStringToFile(fileName, prettyFormat(result));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    public static void reWriteXMLNew(String fileName, PriorityQueue<HumanBeing> queue) throws FileNotFoundException, IOException, XMLStreamException {
        LOGGER.log(Level.INFO, "Method reWriteXMLNew executed with fileName {0}", fileName);
        String result = XMLToString(fileName).replace("</collection>", "") + collectionToString(queue) + "</collection>";
        writeStringToFile(fileName, prettyFormat(result));
    }

    private static void writeStringToFile(String fileName, String input) throws IOException {
        LOGGER.log(Level.INFO, "Method writeStringToFile executed with fileName {0}", fileName);
        try(FileWriter writer = new FileWriter(fileName, false)) { writer.write(input); }
    }

    private static String collectionToString(PriorityQueue<HumanBeing> queue) throws XMLStreamException {
        LOGGER.log(Level.INFO, "Method collectionToString executed");
        StringWriter stringWriter = new StringWriter();
        writeCollection(XMLOutputFactory.newInstance().createXMLEventWriter(stringWriter), queue);
        return stringWriter.toString();
    }

    private static String XMLToString(String fileName) throws FileNotFoundException, IOException {
        LOGGER.log(Level.INFO, "Method XMLToString executed with fileName {0}", fileName);
        StringBuilder result = new StringBuilder();
        FileReader reader = new FileReader(fileName);
        int c;
        while((c=reader.read())!=-1) {
            result.append((char) c);
            if (c == '\n') {
                while ((c = reader.read()) != '<' && c != -1) {}
                if (c != -1) result.append((char) c);
            }
        }
        return result.toString().replaceAll(System.lineSeparator(), "");
    }

    private static String prettyFormat(String input) {
        LOGGER.log(Level.INFO, "Method prettyFormat executed");
        String result = "";
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(xmlInput, xmlOutput);
            result = xmlOutput.getWriter().toString();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return result;
    }

    private static void writeCollection(XMLEventWriter writer, PriorityQueue<HumanBeing> queue) throws XMLStreamException {
        LOGGER.log(Level.INFO, "Method writeCollection executed");
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        for (HumanBeing human: queue) {
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
                Boolean hasToothpick = null;
                try {
                    hasToothpick = human.hasToothpick().get();
                } catch (NoSuchElementException e) {}
                writer.add(eventFactory.createCharacters(hasToothpick == null ? "" : "" + hasToothpick));
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
                Mood mood = null;
                try { mood = human.getMood().get();
                } catch (NoSuchElementException e) {}
                writer.add(eventFactory.createCharacters(mood == null ? "" : mood.name()));
                writer.add(eventFactory.createEndElement("", null, "mood"));

                writer.add(eventFactory.createStartElement("", null, "car"));
                Car car = null;
                try {
                    car = human.getCar().get();
                } catch (NoSuchElementException e) {}
                writer.add(eventFactory.createCharacters(car == null ? "" : "" + car.isCool()));
                writer.add(eventFactory.createEndElement("", null, "car"));

            writer.add(eventFactory.createEndElement("", null, "human"));
        }
    }
}
/**
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
        System.out.println("Something went wring with writing to file " + fileName);
    }

}

private static void prettyWrite(String fileName) {
     StringBuilder result = new StringBuilder();
     try (FileReader reader = new FileReader(fileName)) {
         int c;
         while((c=reader.read())!=-1) {
             result.append((char) c);
             if (c == '\n') {
                 while ((c = reader.read()) != '<' && c != -1) {}
                 if (c != -1) result.append((char) c);
             }
         }
     } catch (Exception e) {
         e.printStackTrace();
     }

    try(FileWriter writer = new FileWriter(fileName, false)) {
        writer.write(prettyFormat(result.toString().replaceAll(System.lineSeparator(), "")));
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private static void writeCollection(XMLEventWriter writer, PriorityQueue<HumanBeing> queue) {
    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    for (HumanBeing human: queue) {

    }
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
                    } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    });
}
*/
