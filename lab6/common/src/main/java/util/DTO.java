package util;

import java.io.Serializable;
import java.util.logging.*;

public class DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String general;
    private String arg = null;
    private String[] humanFields = null;

    public DTO(String general) {
        this.general = general;
    }

    public DTO(String general, String arg, String[] humanFields) {
        this.general = general;
        this.arg = arg;
        this.humanFields = humanFields;
    }

    public String getGeneral() {
        return general;
    }

    public String getArg() {
        return arg;
    }

    public String[] getHumanFields() {
        return humanFields;
    }
}
