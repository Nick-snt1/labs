package src.core.handlers;

import src.core.gui.*;
import src.util.*;

import java.util.*;

public class UpdateHandler implements Runnable {

    ConnectionHandler connection;

    private static HashSet<String> add = new HashSet<>(Arrays.asList(
        "Add Complete succesfully", "AddIfMin complete succesfully" ));

    private static HashSet<String> remove = new HashSet<>(Arrays.asList(
        "RemoveLower Complete succesfully", "RemoveGreater complete succesfully",
        "RemoveById complete succesfully", "Your Collection is empty now"
    ));

    private static HashSet<String> update = new HashSet<>(Arrays.asList(
        "UpdateById Complete succesfully"
    ));



    public UpdateHandler(ConnectionHandler connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        connection.setUpdateSocket(GUI.UPDATE_PORT);
        while (true) {
                Respond respond = connection.receiveUpdate();
                if      (add.contains(respond.getRespond()))    GUI.addHuman(respond.getData());
                else if (remove.contains(respond.getRespond())) GUI.tablePanel.removeHumans(respond.getData());
                else if (update.contains(respond.getRespond())) GUI.updateHuman(respond.getData());

        }
    }
}
