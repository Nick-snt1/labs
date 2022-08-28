package src.core.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import src.core.handlers.*;
import src.util.*;

public class GUI implements Runnable {

   public static MainFrame mainFrame;

   public static TablePanel tablePanel;

   public static VisualPanel visualPanel;

   public static Menu menu;

   public static PicturePanel picturePanel;

   public static CommandHandler handler;

   public static User user;

   public static UpdateHandler update;

   public static int UPDATE_PORT;

   public GUI(CommandHandler handler, ConnectionHandler connection ,int UPDATE_PORT) {
       GUI.UPDATE_PORT = UPDATE_PORT;
       GUI.update = new UpdateHandler(connection);
       GUI.handler = handler;
   }

   public static void addHuman(Object[] data) {
       GUI.tablePanel.addRow(data);
       GUI.visualPanel.addToPanel(data);
   }

   public static void updateHuman(Object[] data) {
       GUI.tablePanel.updateRow(data);
       GUI.visualPanel.updateAnimation(data);
   }

   public static Font getMyFont(int size) {
      return new Font("Comic Sans MS", Font.PLAIN, size);
   }

   public static VisualPanel getVisualPanel() {
      return visualPanel;
   }

   public static JButton createButton(String text, Dimension preferedSize, int fontSize, float alligmentX) {
      JButton button = new JButton(text);  button.setPreferredSize(preferedSize);
      button.setFont(getMyFont(fontSize)); button.setBackground(Color.WHITE);
      button.setFocusPainted(false);       button.setAlignmentX(alligmentX);
      return button;
   }

   public static void configButton(AbstractButton button, Font font, Dimension size) {
      button.setFont(font); button.setPreferredSize(size); button.setFocusPainted(false);
   }

   public static JComboBox<String> createComboBox(String[] values, int fontSize) {
      String defaultVal = values[0];
      JComboBox<String> comboBox = new JComboBox<>(values);
      comboBox.setFont(getMyFont(fontSize));
      comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
      comboBox.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.BLACK));
      comboBox.setBackground(Color.WHITE);
      comboBox.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            if (comboBox.getItemAt(0).equals(defaultVal)) comboBox.removeItemAt(0);
            comboBox.revalidate();
            comboBox.repaint();

         }
         @Override
         public void focusLost(FocusEvent e) { }
      });

      return comboBox;
   }

   public static JTextField createTextField(String defaultText, int fontSize) {
      JTextField textf = new JTextField(defaultText);
      textf.setFont(getMyFont(fontSize));
      textf.setAlignmentX(Component.CENTER_ALIGNMENT);
      textf.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));
      textf.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) { if (textf.getText().equals(defaultText)) textf.setText(null); }

         @Override
         public void focusLost(FocusEvent e) { if (textf.getText().equals("")) textf.setText(defaultText); }
      });
      return textf;
   }

   public static JPasswordField createPasswordField(String defaultText, int fontSize) {
      JPasswordField passwordf = new JPasswordField(defaultText);
      passwordf.setEchoChar((char) 0);
      passwordf.setFont(getMyFont(fontSize));
      passwordf.setAlignmentX(Component.CENTER_ALIGNMENT);
      passwordf.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLACK));
      passwordf.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            if (new String(passwordf.getPassword()).equals(defaultText)) {
               passwordf.setText(null); passwordf.setEchoChar('*');
            }
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (passwordf.getPassword().length == 0) {
               passwordf.setText(defaultText); passwordf.setEchoChar((char) 0);
            }
         }
      });
      return passwordf;
   }

   @Override
   public void run() {
      WelcomeFrame.createRegistrationWindow();
   }
}
