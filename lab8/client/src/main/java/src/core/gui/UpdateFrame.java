package src.core.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UpdateFrame extends JFrame {

    JTextField name, soundtrackName;

    JSpinner x = new JSpinner(), y = new JSpinner(), impactSpeed = new JSpinner();

    JComboBox<String> hasToothPick = new JComboBox<>( new String[] {"true", "false"}),
            realHero     = new JComboBox<>(new String[] {"true", "false"}),
            weaponType   = new JComboBox<>(new String[] {"HAMMER", "PISTOL", "BAT", "MACHINE_GUN"}),
            mood         = new JComboBox<>(new String[] {"SADNESS", "APATHY", "LONGING", "FRENZY", "null"}),
            car          = new JComboBox<>(new String[] {"true", "false", "null"});

    JButton button  = GUI.createButton("Change", new Dimension(160, 70), 27, Component.LEFT_ALIGNMENT);
    JButton button1 = GUI.createButton("Cancel", new Dimension(160, 70), 27, Component.RIGHT_ALIGNMENT);

    Object[] info;

    Animation animation;

    public UpdateFrame(Animation animation) {
        this(animation.getData());
        this.animation = animation;
    }

    public UpdateFrame(Object[] info) {
        super(); this.info = info;
        setSize(470, 950);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new JButton());//to focus on it
        setIconImage(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB_PRE));
        getRootPane().setBorder(BorderFactory.createMatteBorder(3,1,1,1, Color.BLACK));
        setLocationRelativeTo(null);

        JLabel label = new JLabel(info[12].equals(GUI.user.getLogin()) ?
            "Here you can update human" :
                "Only viewing, element not yours");

        label.setFont(GUI.getMyFont(23));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        hasToothPick.setFont(GUI.getMyFont(25)); hasToothPick.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));
        hasToothPick.setBackground(Color.WHITE);
        realHero.setFont(GUI.getMyFont(25)); realHero.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));
        realHero.setBackground(Color.WHITE);

        weaponType.setFont(GUI.getMyFont(25)); weaponType.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));
        weaponType.setBackground(Color.WHITE);
        mood.setFont(GUI.getMyFont(25)); mood.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));
        mood.setBackground(Color.WHITE);
        car.setFont(GUI.getMyFont(25)); car.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));
        car.setBackground(Color.WHITE);

        name = new JTextField(); name.setFont(GUI.getMyFont(25));
        name.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));
        soundtrackName = new JTextField(); soundtrackName.setFont(GUI.getMyFont(25));
        soundtrackName.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));

        JPanel panel = new JPanel();
        panel.setBounds(45,50,360,840);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        x.setFont(GUI.getMyFont(25)); y.setFont(GUI.getMyFont(25)); impactSpeed.setFont(GUI.getMyFont(25));
        x.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));
        y.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));
        impactSpeed.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));

        JPanel panel1 = new JPanel();
        JLabel label1 = new JLabel("Current x:");
        label1.setFont(GUI.getMyFont(25));
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);
        x.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel1.add(label1);  panel1.add(Box.createHorizontalGlue()); panel1.add(x);
        x.setPreferredSize(new Dimension(220,40));

        JPanel panel2 = new JPanel();
        JLabel label2 = new JLabel("Current y:");
        label2.setBackground(Color.WHITE);
        label2.setFont(GUI.getMyFont(25));
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);
        y.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel2.add(label2); panel2.add(Box.createHorizontalGlue()); panel2.add(y);
        y.setPreferredSize(new Dimension(220,40));

        JPanel panel3 = new JPanel();
        JLabel label3 = new JLabel("Current IS:");
        label3.setFont(GUI.getMyFont(25));
        label3.setAlignmentX(Component.LEFT_ALIGNMENT);
        impactSpeed.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel3.add(label3); panel3.add(Box.createHorizontalGlue()); panel3.add(impactSpeed);
        impactSpeed.setPreferredSize(new Dimension(210,40));

        JPanel panel4 = new JPanel();
        panel4.add(button); panel4.add(Box.createHorizontalGlue()); panel4.add(button1);

        add(panel);

        name.setText((String) info[1]); x.setValue(info[3]); y.setValue(info[4]);
        realHero.setSelectedItem(info[5]); hasToothPick.setSelectedItem(info[6]);
        impactSpeed.setValue(info[7]); soundtrackName.setText((String) info[8]);
        weaponType.setSelectedItem(info[9]); mood.setSelectedItem(info[10]);
        car.setSelectedItem(info[11]);

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

        button.setMinimumSize(new Dimension(140, 40));
        button.setPreferredSize(new Dimension(160, 50));
        button.setMaximumSize(new Dimension(160, 60));

        button1.setMinimumSize(new Dimension(140, 40));
        button1.setPreferredSize(new Dimension(160, 50));
        button1.setMaximumSize(new Dimension(160, 60));

        panel4.setMinimumSize(new Dimension(370, 50));
        panel4.setPreferredSize(new Dimension(370, 80));
        panel4.setMaximumSize(new Dimension(370, 80));


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
        panel.add(panel4);         panel.add(Box.createVerticalStrut(10));

        if (!info[12].equals(GUI.user.getLogin())) panel4.remove(button);

        button.addActionListener(e -> {
            JLabel label4 = new JLabel(name.getText().equals("") ? "Field name must not be empty!"
                : soundtrackName.getText().equals("") ? "Field soundtrackName must not be empty!"
                    : GUI.handler.switchCommand("update", GUI.user, "" + info[0], getAllItems()).getRespond());

            label4.setFont(GUI.getMyFont(15).deriveFont(Font.BOLD));

            JOptionPane.showMessageDialog(new JFrame(), label4, "",
                label4.getText().equals("Field name must not be empty!")
                    || label4.getText().equals("Field soundtrackName must not be empty!") ?
                        JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);

            if (!label4.getText().equals("Field name must not be empty!")
                && !label4.getText().equals("Field soundtrackName must not be empty!")) dispose();
        });

        button1.addActionListener(e -> dispose());

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
