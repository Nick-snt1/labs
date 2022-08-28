package src.core.gui;

import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.*;

import src.util.*;

public class MainFrame extends JFrame {

    JPanel mainPanel;

    public MainFrame() {
        super();
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB_PRE));
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createMatteBorder(3,1,1,1, Color.BLACK));

        GUI.tablePanel   = new TablePanel(GUI.handler.switchCommand( "guiShow", GUI.user, null, null).getInitialData());
        GUI.picturePanel = new PicturePanel();
        GUI.visualPanel  = new VisualPanel();
        GUI.menu         = new Menu();

        new Thread(GUI.update).start();

        JPanel content = new JPanel();
        content.setOpaque(true);
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));

        mainPanel = new JPanel();
        mainPanel.setMinimumSize(new Dimension((getHeight()+100)*2/3, getHeight()*2/3));
        mainPanel.setPreferredSize(new Dimension(getHeight()+100, getHeight()));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,3, Color.BLACK));

        mainPanel.add(GUI.menu, BorderLayout.NORTH);

        JPanel pictureTablePanel = new JPanel();
        pictureTablePanel.setLayout(new BorderLayout());

        mainPanel.add(pictureTablePanel, BorderLayout.CENTER);

        pictureTablePanel.add(GUI.picturePanel, BorderLayout.NORTH);
        pictureTablePanel.add(GUI.tablePanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(GUI.visualPanel);
        scrollPane.setBorder(BorderFactory.createMatteBorder(0,2,0,0, Color.BLACK));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanel, scrollPane);
        content.add(splitPane);

        GUI.visualPanel.repaint();
        setContentPane(content);

        setVisible(true);
    }
}
