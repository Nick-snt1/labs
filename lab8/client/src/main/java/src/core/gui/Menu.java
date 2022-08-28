package src.core.gui;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import src.util.*;

public class Menu extends JMenuBar {
    JMenu edit, settings, language, remove, sortBy;

    JMenuItem add, addIfMin, byId, lower, greater, clear, filter, help, exit, executeScript;

    JMenuItem id, name, creationDate, x, y, realHero, hasToothpick,
            impactSpeed, soundtrackName, weaponType, mood, car;

    JRadioButtonMenuItem english, russian;

    public Menu() {
        super();
        setBorder(BorderFactory.createMatteBorder(0,0,3,0, Color.BLACK));

        createMenus(); createMenuItems(); createRadioButtonMenuItem();
        buildMenu(); buildEdit(); buildSettings();

        try { setMenuFont(GUI.getMyFont(17)); } catch (Exception e) {
            e.printStackTrace(); }

        addMenuBarListeners();

    }

    public void addMenuBarListeners() {
        JMenuItem[] array = {id, name, creationDate, x, y, realHero,
            hasToothpick, impactSpeed, soundtrackName, weaponType, mood, car};
        for (int i = 0; i < 12; i++) {
            int finalI = i;
            array[i].addActionListener(e ->  GUI.tablePanel.table.getRowSorter().setSortKeys(Collections.singletonList(
                    new RowSorter.SortKey(finalI, SortOrder.ASCENDING))) );
        }



        filter.addActionListener(e -> GUI.tablePanel.toolFilter.doClick());

        add.addActionListener(e -> GUI.tablePanel.toolAdd.doClick());

        addIfMin.addActionListener(e -> GUI.tablePanel.toolAddIfMin.doClick());

        byId.addActionListener(e -> GUI.tablePanel.toolRemove.doClick());

        lower.addActionListener(e -> GUI.tablePanel.toolRemoveLower.doClick());

        greater.addActionListener(e -> GUI.tablePanel.toolRemoveGreater.doClick());

        JLabel label = new JLabel("");
        label.setFont(GUI.getMyFont(15).deriveFont(Font.BOLD));

        clear.addActionListener(e -> {
            label.setText("You defenetly want to delete ALL humans?");
            if(JOptionPane.showConfirmDialog(new JFrame(), label) == JOptionPane.YES_OPTION) {
                label.setText(GUI.handler.switchCommand("clear", GUI.user, null, null).getRespond());
                JOptionPane.showMessageDialog(new JFrame(), label, "", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        exit.addActionListener(e -> {
            label.setText("Want to leave us?");
            if(JOptionPane.showConfirmDialog(new JFrame(), label) == JOptionPane.YES_OPTION) {
                GUI.mainFrame.dispose();
                GUI.handler.switchCommand("guiExit", GUI.user, "" + GUI.UPDATE_PORT, null);
                WelcomeFrame.createRegistrationWindow();
            }
        });

        executeScript.addActionListener(e -> {
            label.setText("Please, enter root to script");
            String fileName = JOptionPane.showInputDialog(new JFrame(), label);
            if (null != fileName) {
                ExecutorService scriptService = Executors.newSingleThreadExecutor();
                Future<Respond> future = scriptService.submit(() ->
                    GUI.handler.switchCommand("gui_execute_script", GUI.user, fileName, null));

                while (!future.isDone()) { }
                try {
                    label.setText(future.get().getRespond());
                    JOptionPane.showMessageDialog(new JFrame(), label, "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception j) {}

            }
        });
    }


    public void createMenus() {
        edit = new JMenu("Edit"); help = new JMenu("Help"); settings = new JMenu("Settings");
        language = new JMenu("Language"); remove = new JMenu("Remove"); sortBy = new JMenu("Sort by");
    }

    public void setMenuFont(Font font) throws ClassNotFoundException, IllegalAccessException {
        final Class<?> menu  = Class.forName("javax.swing.JMenuItem"),
                       item  = Class.forName("javax.swing.JMenu"),
                       check = Class.forName("javax.swing.JCheckBoxMenuItem"),
                       radio = Class.forName("javax.swing.JRadioButtonMenuItem");

        for (Field f : Class.forName("src.core.gui.Menu").getDeclaredFields())
            if (f.getType() == menu || f.getType() == item || f.getType() == check || f.getType() == radio)
                ((JComponent) f.get(this)).setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
    }

    public void createMenuItems() {
        add = new JMenuItem("Add"); addIfMin = new JMenuItem("Add if min"); byId = new JMenuItem("Remove");
        lower = new JMenuItem("Remove lower"); greater = new JMenuItem("Remove greater");
        filter = new JMenuItem("Filter"); executeScript = new JMenuItem("Execute script");
        clear = new JMenuItem("Clear");
        exit = new JMenuItem("Exit");
        exit.setBorder(BorderFactory.createMatteBorder(0,3,0,0, Color.BLACK));
        exit.setMinimumSize(new Dimension(50, 35));
        exit.setMaximumSize(new Dimension(50, 35));
        exit.setPreferredSize(new Dimension(50, 35));

        id = new JMenuItem("id"); name = new JMenuItem("name"); creationDate = new JMenuItem("creation date");
        x = new JMenuItem("x"); y = new JMenuItem("y"); realHero = new JMenuItem("real hero");
        hasToothpick = new JMenuItem("has toothpick"); impactSpeed = new JMenuItem("impact speed");
        soundtrackName = new JMenuItem("soundtrack name"); weaponType = new JMenuItem("weapon type");
        mood = new JMenuItem("mood"); car = new JMenuItem("car");
    }

    public void createRadioButtonMenuItem() {
        english = new JRadioButtonMenuItem("English", true); russian = new JRadioButtonMenuItem("Russian");
    }

    public void buildMenu() {
        add(edit); add(settings); add(help); add(Box.createHorizontalGlue()); add(exit);
    }

    public void buildSettings() {
        ButtonGroup bg = new ButtonGroup();
        bg.add(english); bg.add(russian);
        settings.add(language); language.add(english); language.add(russian);
    }

    public void buildEdit() {
        edit.add(add); edit.add(addIfMin); edit.add(remove); edit.add(sortBy); edit.add(filter);
        edit.add(executeScript);

        remove.add(greater); remove.add(lower); remove.add(byId); remove.add(clear);

        sortBy.add(id); sortBy.add(name); sortBy.add(creationDate); sortBy.add(x); sortBy.add(y);
        sortBy.add(realHero); sortBy.add(hasToothpick); sortBy.add(impactSpeed);
        sortBy.add(soundtrackName); sortBy.add(weaponType); sortBy.add(mood); sortBy.add(car);

    }
}
