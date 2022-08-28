package src.core.controller;

import src.core.model.elements.*;
import util.*;

import java.util.logging.*;

public class DTOConverter {
    private String command;

    private String arg;

    private HumanBeing human;

    private static final Logger LOGGER = Logger.getLogger(DTOConverter.class.getName());

    public DTOConverter(DTO dto) {
        LOGGER.info("New DTOConverter created");
        command = dto.getGeneral();
        arg = dto.getArg();
        human = parseHumanBeing(dto.getHumanFields());
    }

    public String getCommand() { return command; }

    public String getArg() { return arg; }

    public HumanBeing getHuman() { return human; }

    private static HumanBeing parseHumanBeing(String[] args) {
        LOGGER.info("Method parseHumanBeing executed");
        return args == null ? null : new HumanBeing(
            args[0],
            new Coordinates(Integer.parseInt(args[1]), Long.parseLong(args[2])),
            Boolean.parseBoolean(args[3]),
            args[4] == null ? null : Boolean.parseBoolean(args[4]),
            Integer.parseInt(args[5]),
            args[6],
            WeaponType.valueOf(args[7]),
            args[8] == null ? null : Mood.valueOf(args[8].toUpperCase()),
            args[9] == null ? null : new Car(Boolean.parseBoolean(args[9]))
        );
    }
}
