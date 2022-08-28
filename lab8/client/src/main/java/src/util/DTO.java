package src.util;

import java.io.Serializable;

public class DTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String general;
    private final User user;
    private String arg = null;
    private String[] humanFields = null;

    public DTO(String general, User user) {
        this(general, user, null, null);
    }

    public DTO(String general, User user, String arg, String[] humanFields) {
        this.general = general;
        this.user = user;
        this.arg = arg;
        this.humanFields = humanFields;
    }

    public String getGeneral() {
        return general;
    }

    public User getUser() {
        return user;
    }

    public String getArg() {
        return arg;
    }

    public String[] getHumanFields() {
        return humanFields;
    }
}
