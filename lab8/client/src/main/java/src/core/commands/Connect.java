package src.core.commands;

import src.core.handlers.ConnectionHandler;
import src.util.*;

public class Connect implements Command<Respond> {

    private ConnectionHandler handler;

    private DTO dto;

    public Connect(ConnectionHandler handler, DTO dto) {
        this.dto = dto;
        this.handler = handler;
    }

    @Override
    public Respond execute() {
        return handler.connect(dto);
    }
}
