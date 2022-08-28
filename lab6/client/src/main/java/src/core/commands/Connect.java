package src.core.commands;

import src.core.handlers.ConnectionHandler;
import util.*;

public class Connect implements Command {

    private ConnectionHandler handler;

    private DTO dto;

    public Connect(ConnectionHandler handler, DTO dto) {
        this.dto = dto;
        this.handler = handler;
    }

    @Override
    public void execute() {
        handler.connect(dto);
    }
}
