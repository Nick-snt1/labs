package src.core.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.Stream;

public class AddFrame extends JFrame {

    JTextField name           = GUI.createTextField(" Enter name...", 25),
               soundtrackName = GUI.createTextField(" Enter soundtrack name...", 25);

    JSpinner x = new JSpinner(), y = new JSpinner(), impactSpeed = new JSpinner();

    HashSet<String> defaultValues = new HashSet<>(Arrays.asList(
        "Choose having a toothpick?", "Choose is a real hero?", "Choose weapon type", "Choose mood", "Choose is car cool?"));

    JComboBox<String> hasToothPick = GUI.createComboBox(new String[] {"Choose having a toothpick?", "true", "false"}, 25),
                      realHero     = GUI.createComboBox(new String[] {"Choose is a real hero?", "true", "false"}, 25),
                      weaponType   = GUI.createComboBox(new String[] {"Choose weapon type", "HAMMER", "PISTOL", "BAT", "MACHINE_GUN"}, 25),
                      mood         = GUI.createComboBox(new String[] {"Choose mood", "SADNESS", "APATHY", "LONGING", "FRENZY", "null"}, 25),
                      car          = GUI.createComboBox(new String[] {"Choose is car cool?", "true", "false", "null"}, 25);

    JButton button = GUI.createButton("Submit", new Dimension(160, 70), 27, Component.CENTER_ALIGNMENT);

    String command;

    public AddFrame(String command) {
        super();
        this.command = command;
        setResizable(true);
        setSize(470, 930);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new JButton());//to focus on it
        setIconImage(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB_PRE));
        getRootPane().setBorder(BorderFactory.createMatteBorder(3,1,1,1, Color.BLACK));
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Enter humans parameters");
        label.setFont(GUI.getMyFont(23));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panel = new JPanel();
        panel.setBounds(45,50,360,810);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        x.setFont(GUI.getMyFont(25)); y.setFont(GUI.getMyFont(25)); impactSpeed.setFont(GUI.getMyFont(25));
        x.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));
        y.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));
        impactSpeed.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));

        JPanel panel1 = new JPanel();
        JLabel label1 = new JLabel("Choose x:");
        label1.setFont(GUI.getMyFont(25));
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);
        x.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel1.add(label1); panel1.add(Box.createHorizontalGlue()); panel1.add(x);
        x.setPreferredSize(new Dimension(240,40));

        JPanel panel2 = new JPanel();
        JLabel label2 = new JLabel("Choose y:");
        label2.setBackground(Color.WHITE);
        label2.setFont(GUI.getMyFont(25));
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);
        y.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel2.add(label2); panel2.add(Box.createHorizontalGlue()); panel2.add(y);
        y.setPreferredSize(new Dimension(240,40));

        JPanel panel3 = new JPanel();
        JLabel label3 = new JLabel("Choose IS:");
        label3.setFont(GUI.getMyFont(25));
        label3.setAlignmentX(Component.LEFT_ALIGNMENT);
        impactSpeed.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel3.add(label3);  panel3.add(Box.createHorizontalGlue()); panel3.add(impactSpeed);;
        impactSpeed.setPreferredSize(new Dimension(220,40));

        name.setMinimumSize(new Dimension(370, 30));
        name.setPreferredSize(new Dimension(370, 60));
        name.setMaximumSize(new Dimension(370, 60));

        realHero.setMinimumSize(new Dimension(370, 30));
        realHero.setPreferredSize(new Dimension(370, 60));
        realHero.setMaximumSize(new Dimension(370, 60));

        panel1.setMinimumSize(new Dimension(370, 30));
        panel1.setPreferredSize(new Dimension(370, 60));
        panel1.setMaximumSize(new Dimension(370, 60));

        panel2.setMinimumSize(new Dimension(370, 30));
        panel2.setPreferredSize(new Dimension(370, 60));
        panel2.setMaximumSize(new Dimension(370, 60));

        panel3.setMinimumSize(new Dimension(370, 30));
        panel3.setPreferredSize(new Dimension(370, 60));
        panel3.setMaximumSize(new Dimension(370, 60));

        hasToothPick.setMinimumSize(new Dimension(370, 30));
        hasToothPick.setPreferredSize(new Dimension(370, 60));
        hasToothPick.setMaximumSize(new Dimension(370, 60));

        soundtrackName.setMinimumSize(new Dimension(370, 30));
        soundtrackName.setPreferredSize(new Dimension(370, 60));
        soundtrackName.setMaximumSize(new Dimension(370, 60));

        weaponType.setMinimumSize(new Dimension(370, 30));
        weaponType.setPreferredSize(new Dimension(370, 60));
        weaponType.setMaximumSize(new Dimension(370, 60));

        mood.setMinimumSize(new Dimension(370, 30));
        mood.setPreferredSize(new Dimension(370, 60));
        mood.setMaximumSize(new Dimension(370, 60));

        car.setMinimumSize(new Dimension(370, 30));
        car.setPreferredSize(new Dimension(370, 60));
        car.setMaximumSize(new Dimension(370, 60));


        add(panel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(label);          panel.add(Box.createVerticalStrut(10));
        panel.add(name);           panel.add(Box.createVerticalStrut(10));
        panel.add(panel1);         panel.add(Box.createVerticalStrut(10));
        panel.add(panel2);         panel.add(Box.createVerticalStrut(10));
        panel.add(realHero);       panel.add(Box.createVerticalStrut(10));
        panel.add(hasToothPick);   panel.add(Box.createVerticalStrut(10));
        panel.add(panel3);         panel.add(Box.createVerticalStrut(10));
        panel.add(soundtrackName); panel.add(Box.createVerticalStrut(10));
        panel.add(weaponType);     panel.add(Box.createVerticalStrut(10));
        panel.add(mood);           panel.add(Box.createVerticalStrut(10));
        panel.add(car);            panel.add(Box.createVerticalStrut(10));
        panel.add(button);         panel.add(Box.createVerticalStrut(10));

        button.addActionListener(e -> {

            JLabel label4 = new JLabel(name.getText().equals(" Enter name...")  ?
                "Field name must not be empty!" : soundtrackName.getText().equals(" Enter soundtrack name...") ?
                "Field soundtrack name must not be empty!"
                    : Stream.of(realHero.getSelectedItem(), hasToothPick.getSelectedItem(), weaponType.getSelectedItem(),
                        mood.getSelectedItem(), car.getSelectedItem()).anyMatch( x -> defaultValues.contains(x)) ?
                "Please, select all fields" : GUI.handler.switchCommand(command, GUI.user, null, getAllItems()).getRespond() );

            HashSet<String> set = new HashSet<>(Arrays.asList( "Field name must not be empty!",
                "Field soundtrack name must not be empty!", "Please, select all fields"));

            label4.setFont(GUI.getMyFont(15).deriveFont(Font.BOLD));

            if (!set.contains(label4.getText())) dispose();

            JOptionPane.showMessageDialog(new JFrame(), label4, "", set.contains(label4.getText()) ?
                JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);

        });
        setVisible(true);
    }

    public String[] getAllItems() {
        return new String[] { name.getText(), "" + x.getValue(), "" + y.getValue(), "" + realHero.getSelectedItem(),
            hasToothPick.getSelectedItem().equals("null") ? null : "" + hasToothPick.getSelectedItem(),
            "" + impactSpeed.getValue(), soundtrackName.getText(), "" + weaponType.getSelectedItem(),
            mood.getSelectedItem().equals("null") ? null : "" + mood.getSelectedItem(),
            car.getSelectedItem().equals("null")  ? null : "" + car.getSelectedItem() };
    }
}
