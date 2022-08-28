package src.core.commands;

import src.core.handlers.ConnectionHandler;
import src.util.*;

public class Connect implements Command<String> {

    private ConnectionHandler handler;

    private DTO dto;

    public Connect(ConnectionHandler handler, DTO dto) {
        this.dto = dto;
        this.handler = handler;
    }

    @Override
    public String execute() {
        return handler.connect(dto);
    }
}
