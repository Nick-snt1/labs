package src.core.controller;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

import src.core.model.elements.*;
import src.util.*;

public class DTOConverter {
    private String command;

    private String arg;

    private User user;

    private HumanBeing human;

    public DTOConverter(DTO dto) {
        command = dto.getGeneral();
        arg     = dto.getArg();
        user    = new User(dto.getUser().getLogin(), hashPassword(dto.getUser().getPassword()));
        human   = parseHumanBeing(dto.getHumanFields());
    }

    public String getCommand() { return command; }

    public String getArg() { return arg; }

    public User getUser() { return user; }

    public HumanBeing getHuman() { return human; }

    private String hashPassword(String password) {
        try {
            return new String(
                MessageDigest.getInstance("SHA-384").digest(password.getBytes(StandardCharsets.UTF_8)),
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    private HumanBeing parseHumanBeing(String[] args) {
        return args == null ? null : new HumanBeing(
            args[0],
            new Coordinates(Integer.parseInt(args[1]), Long.parseLong(args[2])),
            Boolean.parseBoolean(args[3]),
            args[4] == null ? null : Boolean.parseBoolean(args[4]),
            Integer.parseInt(args[5]),
            args[6],
            WeaponType.valueOf(args[7]),
            args[8] == null ? null : Mood.valueOf(args[8].toUpperCase()),
            args[9] == null ? null : new Car(Boolean.parseBoolean(args[9])),
            user
        );
    }
}
