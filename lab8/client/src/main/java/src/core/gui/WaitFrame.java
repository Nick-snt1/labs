package src.core.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;


public class WaitFrame extends JFrame {

    public static JFrame frame;

    public static int time;

    public static JLabel label;

    JButton button = GUI.createButton("Stop waiting and leave", new Dimension(260, 70), 27, Component.CENTER_ALIGNMENT);

    public WaitFrame(int time) {
        frame = new JFrame();
        WaitFrame.time = time;
        frame.setResizable(false);
        frame.setSize(470, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB_PRE));
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(3,1,1,1, Color.BLACK));
        frame.setLocationRelativeTo(null);

        label = new JLabel("Server isn't responding. Wait for " + time + " seconds");//"Welcome back to Lab8"
        label.setFont(GUI.getMyFont(20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        frame.getContentPane().add(label);

        frame.getContentPane().add(Box.createVerticalGlue());

        frame.getContentPane().add(button);

        button.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    public static void update(int time) {
        label.setText("Server isn't responding. Wait for " + time + " seconds");
        frame.revalidate();
        frame.repaint();
    }
}
