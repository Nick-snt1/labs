package src.util;

import java.io.Serializable;

public class Respond implements Serializable {

    private static final long serialVersionUID = 3L;

    private final String respond;

    public Respond(String respond) {
        this.respond = respond;
    }

    public String getRespond() { return respond; }
}
