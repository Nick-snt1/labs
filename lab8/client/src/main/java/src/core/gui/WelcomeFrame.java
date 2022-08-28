package src.core.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.Arrays;

import src.util.*;

public class WelcomeFrame extends JFrame {

    private static JFrame frame;

    private static JButton upBtm, downBtm;

    private static JTextField textf;

    private static JPasswordField passf;

    private static Animation anim;

    private static Image[] image = new Image[35];

    private WelcomeFrame(String welcome, String quest) {
        frame = new JFrame();
        frame.setResizable(true);
        frame.setSize(470, 660);

        frame.setMinimumSize(new Dimension(470, 330));
        frame.setMaximumSize(new Dimension(470, 660));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton b = new JButton();
        b.setMinimumSize(new Dimension(0,0)); b.setMaximumSize(new Dimension(0,0));
        frame.add(b);//to focus on it
        frame.setIconImage(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB_PRE));
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(3,1,1,1, Color.BLACK));
        frame.setLocationRelativeTo(null);

        JLabel label = new JLabel(welcome);
        label.setFont(GUI.getMyFont(28));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label2 = new JLabel(quest);
        label2.setFont(GUI.getMyFont(19));
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);

        textf = GUI.createTextField(" Username...", 24);
        textf.setMinimumSize(new Dimension(370, 30));
        textf.setPreferredSize(new Dimension(370, 60));
        textf.setMaximumSize(new Dimension(370, 60));

        passf = GUI.createPasswordField(" Password...", 24);
        passf.setMinimumSize(new Dimension(370, 30));
        passf.setPreferredSize(new Dimension(370, 60));
        passf.setMaximumSize(new Dimension(370, 60));

        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        panel.setBounds(45,50,360,360);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JPanel panel2 = new JPanel();
        panel2.setBounds(45,510,360,100);
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

        frame.add(panel); frame.add(Box.createVerticalGlue()); frame.add(panel2);

        panel.add(Box.createVerticalStrut(50));
        panel.add(label);     panel.add(Box.createVerticalStrut(40));
        panel.add(textf);     panel.add(Box.createVerticalStrut(40));
        panel.add(passf);     panel.add(Box.createVerticalStrut(40));
        panel.add(upBtm);

        panel2.add(label2);   panel2.add(Box.createHorizontalStrut(40));//Box.createHorizontalStrut(35);
        panel2.add(downBtm);
    }

    private WelcomeFrame() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setSize(760, 940);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JButton());//to focus on it
        frame.setIconImage(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB_PRE));
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.BLACK));
        frame.setLocationRelativeTo(null);

        Toolkit t = Toolkit.getDefaultToolkit();
        for (int i = 0; i < 35; i++) image[i] = t.getImage("assets/scullMan/sprite_" + (i < 10 ? "0" + i : i) + ".png");
        anim = new Animation(image, 180);
        anim.setBackground(new Color(251, 231, 170));

        frame.getContentPane().setBackground(new Color(251, 231, 170));

        anim.add(downBtm);
        frame.getContentPane().add(anim);
        anim.setLayout(null);


    }

    public static void createAuthorizationWindow() {
        upBtm = GUI.createButton("Sign IN", new Dimension(160, 70), 27, Component.CENTER_ALIGNMENT);
        upBtm.setMinimumSize(new Dimension(100, 50));
        upBtm.setPreferredSize(new Dimension(160, 70));
        upBtm.setMaximumSize(new Dimension(160, 70));
        downBtm = GUI.createButton("Remind me", new Dimension(90, 65), 18, Component.RIGHT_ALIGNMENT);

        upBtm.addActionListener((ae) -> {
            User user = new User(textf.getText(), new String(passf.getPassword()));
            JLabel label = new JLabel("");
            label.setFont(GUI.getMyFont(15).deriveFont(Font.BOLD));
            if (textf.getText().equals(" Username...") || new String(passf.getPassword()).equals(" Password...")) {
                label.setText("Fields must not be empty!");
                JOptionPane.showMessageDialog(new JFrame(), label, "", JOptionPane.ERROR_MESSAGE);
            } else {
                Respond message = GUI.handler.switchCommand("authorization", user, "" + GUI.UPDATE_PORT, null);
                if (message.getRespond().equals("OK")) {
                    GUI.user = user; frame.dispose(); GUI.mainFrame = new MainFrame();
                } else {
                    label.setText(message.getRespond());
                    JOptionPane.showMessageDialog(new JFrame(), label, "", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        downBtm.addActionListener((ae) -> {frame.dispose(); createReminderWindow();});

        new WelcomeFrame("Welcome back to Lab8", "Forgot password?     ");

        frame.setVisible(true);
    }

    public static void createRegistrationWindow() {
        upBtm   = GUI.createButton("Sign UP", new Dimension(160, 70), 27, Component.CENTER_ALIGNMENT);
        upBtm.setMinimumSize(new Dimension(100, 50));
        upBtm.setPreferredSize(new Dimension(160, 70));
        upBtm.setMaximumSize(new Dimension(160, 70));
        downBtm = GUI.createButton("Sign IN", new Dimension(90, 65), 18,  Component.RIGHT_ALIGNMENT);

        upBtm.addActionListener((ae) -> {

            JLabel label = new JLabel("");
            label.setFont(GUI.getMyFont(15).deriveFont(Font.BOLD));

            if (textf.getText().equals(" Username...")
                    || new String(passf.getPassword()).equals(" Password...")) {

                label.setText("Fields must not be empty");
                JOptionPane.showMessageDialog(new JFrame(), label, "", JOptionPane.ERROR_MESSAGE);

            } else {

                String respond = GUI.handler.switchCommand(
                    "registration", new User(textf.getText(), new String(passf.getPassword())), null, null).getRespond();

                label.setText(respond.equals("OK")
                    ? "Registration complete successfully!" : respond);

                JOptionPane.showMessageDialog(new JFrame(), label, "",
                    label.getText().equals("Registration complete successfully!")
                    ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);

                if (respond.equals("OK")) { frame.dispose(); createAuthorizationWindow(); }

            }

        });
        downBtm.addActionListener((ae) -> { frame.setVisible(false); createAuthorizationWindow(); });

        new WelcomeFrame("Welcome to Lab8", "Already have an account?");

        frame.setVisible(true);
    }

    public static void createReminderWindow() {
        downBtm = GUI.createButton("Leave", new Dimension(160, 70), 27, Component.CENTER_ALIGNMENT);
        downBtm.setBorder(BorderFactory.createMatteBorder(0,3,3,0, Color.BLACK));
        downBtm.setBounds(640, 0, 100, 50);
        downBtm.setBackground(new Color(184, 181, 185));

        downBtm.addActionListener((ae) -> { frame.dispose(); createRegistrationWindow(); });

        new WelcomeFrame();

        frame.setVisible(true);

        anim.timer.start();
        anim.timer.setDelay(0);
        JLabel dialog = new JLabel(new ImageIcon("../assets/dialog.png"));
        anim.add(dialog);
        dialog.setBounds(365,-20 ,420,268);
        anim.revalidate();
        anim.repaint();
    }
}
