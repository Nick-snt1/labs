package src.core.gui;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class FilterFrame extends JFrame {

    JComboBox<String> comboBox = new JComboBox<>(
            new String[] {
                "id", "name", "creationDate", "x", "y", "realHero", "hasToothpick",
                    "impactSpeed", "soundtrackName", "weaponType", "mood", "car"});

    JTextField textf = GUI.createTextField(" Enter value to filter by...", 25);

    JButton button = GUI.createButton("Submit", new Dimension(160, 70), 27, Component.CENTER_ALIGNMENT);;

    public FilterFrame() {
        super();
        setResizable(false);
        setSize(470, 470);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new JButton());
        setIconImage(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB_PRE));
        getRootPane().setBorder(BorderFactory.createMatteBorder(3,1,1,1, Color.BLACK));
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Filter by");
        label.setFont(GUI.getMyFont(28));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        comboBox.setFont(GUI.getMyFont(24));
        comboBox.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.BLACK));
        comboBox.setBackground(Color.WHITE);

        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(45,50,360,360);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JPanel panel2 = new JPanel();
        panel2.setBounds(45,510,360,100);
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

        add(panel); add(panel2);

        panel.add(label);     panel.add(Box.createVerticalStrut(40));
        panel.add(comboBox);  panel.add(Box.createVerticalStrut(40));
        panel.add(textf);     panel.add(Box.createVerticalStrut(40));
        panel.add(button);    panel.add(Box.createVerticalStrut(40));

        setVisible(true);
    }

    public void addActionListener(JTable table) {
        button.addActionListener(e -> {
            int i = comboBox.getSelectedIndex();
            ((TableRowSorter) table.getRowSorter()).setRowFilter(
                    RowFilter.regexFilter(i == 1 || i == 2 || i == 8 ? textf.getText() : "^" + textf.getText() + "$", i));
            dispose();
        });
    }
}
