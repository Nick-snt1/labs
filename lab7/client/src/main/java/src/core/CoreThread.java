package src.core;

import src.core.handlers.CommandHandler;
import src.core.readers.*;

import src.util.User;

public class CoreThread extends Thread {
    private CommandHandler handler;

    private User user;

    private boolean isActive = true;

    public CoreThread(CommandHandler handler) {
        this.handler = handler;
    }

    public void setUser(User user) { this.user = user; }

    public void setIsActive(boolean isActive) { this.isActive = isActive; };

    @Override
    public void run() {
        AbstractReader reader = new InputReader();
        System.out.println("Now you can operate with collection of Human beings! Feel Power!!!");
        String[] line;
        while(isActive) {
            line = reader.getFirstLine();
            String result = handler.switchCommand(
                line[0], user, line[1], CheckInput.checkElemArg(line[0]) ? reader.getHumanBeing() : null);
            System.out.println(result);
        }
    }
}
