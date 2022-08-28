package src.util;

import java.io.Serializable;

public class Respond implements Serializable {

    private static final long serialVersionUID = 3L;

    private final String respond;

    private final Object[] data;

    private final Object[][] initialData;

    public Respond(String respond) {
        this(respond, null, null);
    }

    public Respond(String respond, Object[] data) {
        this(respond, data, null);
    }

    public Respond(String respond, Object[] data, Object[][] initialData) {
        this.respond = respond;
        this.data = data;
        this.initialData = initialData;
    }

    public String getRespond() { return respond; }

    public Object[] getData() { return data; }

    public Object[][] getInitialData() { return initialData; }
}
