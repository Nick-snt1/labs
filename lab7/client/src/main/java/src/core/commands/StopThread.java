package src.core.commands;

import src.core.handlers.*;
import src.core.CoreThread;

public class StopThread implements Command<String> {

    private ProgrammHandler programm;

    private CoreThread t;

    public StopThread(ProgrammHandler programm, CoreThread t) {
        this.programm = programm;
        this.t = t;
    }

    @Override
    public String execute() {
        return programm.stopThread(t);
    }
}
