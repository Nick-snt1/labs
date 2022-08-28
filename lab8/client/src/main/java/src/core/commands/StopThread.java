package src.core.commands;

import src.core.handlers.*;
import src.core.CoreThread;
import src.util.*;

public class StopThread implements Command<Respond> {

    private ProgrammHandler programm;

    private CoreThread t;

    public StopThread(ProgrammHandler programm, CoreThread t) {
        this.programm = programm;
        this.t = t;
    }

    @Override
    public Respond execute() {
        return programm.stopThread(t);
    }
}
