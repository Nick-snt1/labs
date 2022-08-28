package src.core.gui;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

public class PicturePanel extends JPanel {

    JButton profileInfo, profileSettings;

    public PicturePanel() {
        super();
        setPreferredSize(new Dimension(1160, 260));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel left = new JPanel(), center = new JPanel(), right = new JPanel();

        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        add(new JPanel()); add(Box.createHorizontalGlue());
        add(left); add(Box.createHorizontalGlue()); add(center); add(Box.createHorizontalGlue()); add(right);
        add(Box.createHorizontalGlue()); add(new JPanel());

        createProfileInfoB(); createProfileSettingsB();

        JLabel avatar = new JLabel(new ImageIcon("assets/Avatar.jpg"));
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        avatar.setAlignmentY(Component.CENTER_ALIGNMENT);
        center.add(avatar);

        left.add(Box.createHorizontalStrut(80));
        left.add(profileInfo);
        left.add(Box.createHorizontalStrut(80));

        right.add(Box.createHorizontalStrut(80));
        right.add(profileSettings);
        right.add(Box.createHorizontalStrut(80));

    }

    public void createProfileInfoB() {
        profileInfo = GUI.createButton("Profile info", new Dimension(280, 80), 30, Component.CENTER_ALIGNMENT);
        profileInfo.setBackground(new Color(255, 242, 204));
        profileInfo.setForeground(new Color(214, 182, 86));
        profileInfo.setBorder(BorderFactory.createMatteBorder(4,4,4,4, new Color(214, 182, 86)));

        profileInfo.setPreferredSize(new Dimension(240, 80));
        profileInfo.setMaximumSize(new Dimension(280, 80));
    }

    public void createProfileSettingsB() {
        profileSettings = GUI.createButton("Profile settings", new Dimension(280, 80), 30, Component.CENTER_ALIGNMENT);
        profileSettings.setBorder(BorderFactory.createMatteBorder(4,4,4,4, new Color(214, 182, 86)));
        profileSettings.setBackground(new Color(255, 242, 204));
        profileSettings.setForeground(new Color(214, 182, 86));

        profileSettings.setPreferredSize(new Dimension(240, 80));
        profileSettings.setMaximumSize(new Dimension(280, 80));
    }
}
