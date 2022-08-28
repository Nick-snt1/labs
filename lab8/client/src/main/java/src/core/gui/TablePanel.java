package src.core.gui;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.*;
import java.util.*;

public class TablePanel extends JPanel {

    JButton toolAdd           = new JButton(new ImageIcon("assets/ToolBarButtonsNew/add.jpg")),
            toolAddIfMin      = new JButton(new ImageIcon("assets/ToolBarButtonsNew/addIfMin.jpg")),
            toolRemove        = new JButton(new ImageIcon("assets/ToolBarButtonsNew/remove.jpg")),
            toolRemoveLower   = new JButton(new ImageIcon("assets/ToolBarButtonsNew/removeLower.jpg")),
            toolRemoveGreater = new JButton(new ImageIcon("assets/ToolBarButtonsNew/removeGreater.jpg"));

    JToggleButton showYours  = new JToggleButton("Show yours"),
                  toolFilter = new JToggleButton("Filter");

    JComboBox<String> comboBox = new JComboBox<>(new String[] {"Lower first", "Bigger first"});

    DefaultTableModel tableModel;

    JTable table;

    Object[][] data;

    public TablePanel(Object[][] data) {
        super();
        this.data = data;
        setLayout(new BorderLayout());
        add(createToolBar(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);

        addActionListeners();

    }

    public JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.BLACK));
        toolBar.setFloatable(false);
        toolBar.add(toolAdd);         toolBar.add(toolAddIfMin);      toolBar.add(toolRemove);
        toolBar.add(toolRemoveLower); toolBar.add(toolRemoveGreater); toolBar.add(showYours);
        toolBar.add(toolFilter);

        GUI.configButton(toolFilter, GUI.getMyFont(23), new Dimension(120, 40));
        GUI.configButton(showYours,  GUI.getMyFont(23), new Dimension(150, 40));

        comboBox.setFont(GUI.getMyFont(24));

        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(comboBox);

        return toolBar;
    }

    public JScrollPane createTable() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int j) {
                return table.getValueAt(table.convertRowIndexToView(i), 12).equals(GUI.user.getLogin())
                        && !(j == 0 || j == 2 || j == 12);
            }

            @Override
            public Class getColumnClass(int col) {
                return (col >= 0) && (col < getColumnCount()) ? getValueAt(0, col).getClass() : Object.class;
            }
        };

        tableModel.setColumnIdentifiers(new String[] {
                "Id", "Name", "Creation date", "X", "Y", "Real hero", "Has toothpick",
                "Impact speed", "Soundtrack name", "Weapon type", "Mood", "Car" , "Owner"});

        for (Object[] o : data) tableModel.addRow(o);

        table = new JTable(tableModel);
        table.setFont(GUI.getMyFont(13));
        table.setBackground(new Color(255, 242, 204));
        table.setRowHeight(table.getRowHeight() + 10);

        table.setRowSorter(new TableRowSorter<>(tableModel));
        //default sort by id ascending

        configColumnWidth(); configCellEditors();

        table.getTableHeader().setFont(GUI.getMyFont(15));
        table.getTableHeader().setReorderingAllowed(false);

        return new JScrollPane(table);
    }

    public void removeHumans(Object[] array) {
        for (Object o : array ) {
            for (int i = 0; i < table.getRowCount(); i++) {
                if (table.getValueAt(i, 0).equals(o)) {
                    ((DefaultTableModel) table.getModel()).removeRow(table.convertRowIndexToModel(i));
                    GUI.visualPanel.removeAnimation((Long) o);
                    break;
                }
            }
        }
        revalidate();
        repaint();
    }

    public void addRow(Object[] o) {
        tableModel.addRow(o);
        revalidate();
        repaint();
    }

    public void updateRow(Object[] data) {
        for(int i = 0; i < table.getRowCount(); i++)
            if (data[0].equals(table.getValueAt(i, 0)))
                for (int j = 0; j < 12; j++) table.setValueAt(data[j], i, j);
    }

    public void addActionListeners() {
        addTableActions();
        addToolBarActions();
        addCellActions();
    }

    public void addTableActions() {
        table.getRowSorter().setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));

        ((TableRowSorter) table.getRowSorter()).setSortsOnUpdates(true);

        table.getRowSorter().addRowSorterListener(e -> { comboBox.setSelectedIndex(
                table.getRowSorter().getSortKeys().get(0).getSortOrder() == SortOrder.ASCENDING ? 0 : 1);
        } );
    }

    public void addToolBarActions() {
        comboBox.addActionListener(e -> table.getRowSorter().setSortKeys(Collections.singletonList(
                new RowSorter.SortKey( table.getRowSorter().getSortKeys().get(0).getColumn(),
                        comboBox.getSelectedIndex() == 1 ? SortOrder.DESCENDING : SortOrder.ASCENDING))));

        showYours.addItemListener(e -> {
            if (showYours.isSelected()) {
                showYours.setText("Show all");
                ((TableRowSorter) table.getRowSorter()).setRowFilter(
                    RowFilter.regexFilter("^"+ GUI.user.getLogin() + "$", 12));
            } else {
                showYours.setText("Show yours");
                ((TableRowSorter) table.getRowSorter()).setRowFilter(null);
            }
        });

        toolFilter.addItemListener( e -> {
            if (toolFilter.isSelected()) {
                toolFilter.setText("Undo");
                new FilterFrame().addActionListener(table);
            } else {
                toolFilter.setText("Filter");
                ((TableRowSorter) table.getRowSorter()).setRowFilter(null);
            }

        });

        toolAdd.addActionListener           (e -> { new AddFrame("add"); });
        toolAddIfMin.addActionListener      (e -> { new AddFrame("add_if_min"); });
        toolRemoveLower.addActionListener   (e -> { new AddFrame("remove_lower"); });
        toolRemoveGreater.addActionListener (e -> { new AddFrame("remove_greater"); });

        toolRemove.addActionListener(e -> {
            try {
                JLabel label = new JLabel(table.getSelectedRow() == -1 ? "Please, select human at the table to delete"
                    : !table.getValueAt(table.getSelectedRow(), 12).equals(GUI.user.getLogin()) ?
                        "You can't remove elements that not yours" :
                            GUI.handler.switchCommand(
                                "remove_by_id", GUI.user, "" + table.getValueAt(table.getSelectedRow(), 0), null).getRespond());

                label.setFont(GUI.getMyFont(15).deriveFont(Font.BOLD));
                JOptionPane.showMessageDialog(new JFrame(), label, "", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ignored) {}
        });
    }


    public void addCellActions() {
        CellEditorListener listener = new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                if (table.getSelectedRow() != -1) {
                    Object[] data = new Object[13];
                    for (int i = 0; i < 13; i++) data[i] = table.getValueAt(table.getSelectedRow(), i);

                    JLabel label = new JLabel(data[1].equals("") ? "Name must not be empty!"
                                            : data[8].equals("") ? "Soundtrack name must not be empty!" : "");
                    label.setFont(GUI.getMyFont(15).deriveFont(Font.BOLD));

                    if (!label.getText().equals("")) {
                        JOptionPane.showMessageDialog(new JFrame(), label, "", JOptionPane.ERROR_MESSAGE);
                        updateRow(GUI.visualPanel.getDataById(data[0]));
                    } else {
                        GUI.handler.switchCommand("update", GUI.user, "" + data[0], new String[] {
                            "" + data[1], "" + data[3], "" + data[4], "" + data[5],
                            data[ 6].equals("null") ? null : "" + data[ 6], "" + data[7], "" + data[8], "" + data[9],
                            data[10].equals("null") ? null : "" + data[10] ,
                            data[11].equals("null") ? null : "" + data[11]
                        });
                    }
                }
            }

            @Override
            public void editingCanceled(ChangeEvent e) { }
        };

        for (int i : new int[] {1, 3, 4, 5, 6, 7, 8, 9, 10, 11})
            table.getColumnModel().getColumn(i).getCellEditor().addCellEditorListener(listener);

    }

    public void configColumnWidth() {
        TableColumnModel model = table.getColumnModel();
        model.getColumn(0).setPreferredWidth(15); model.getColumn(3).setPreferredWidth(10);
        model.getColumn(4).setPreferredWidth(10); model.getColumn(5).setPreferredWidth(25);
        model.getColumn(8).setPreferredWidth(90); model.getColumn(11).setPreferredWidth(10);

        model.getColumn(12).setMinWidth(0); model.getColumn(12).setMaxWidth(0);
        model.getColumn(12).setWidth(0);
    }

    public void configCellEditors() {
        JComboBox<String> boolEditor       = new JComboBox<>(new String[] {"true", "false"}),
                          boolNullEditor   = new JComboBox<>(new String[] {"true", "false", "null"}),
                          weaponTypeEditor = new JComboBox<>(new String[] {"HAMMER", "PISTOL", "BAT", "MACHINE_GUN"}),
                          moodEditor       = new JComboBox<>(new String[] {"SADNESS", "APATHY", "LONGING", "FRENZY", "null"});

        JTextField textEditor = new JTextField();

        textEditor.setFont(GUI.getMyFont(13));
        boolEditor.setFont(GUI.getMyFont(13));       boolNullEditor.setFont(GUI.getMyFont(13));
        weaponTypeEditor.setFont(GUI.getMyFont(13)); moodEditor.setFont(GUI.getMyFont(13));


        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(textEditor));

        table.getColumnModel().getColumn(3).setCellEditor(new IntCellEditor());

        table.getColumnModel().getColumn(4).setCellEditor(new IntCellEditor());

        table.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(boolEditor));

        table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(boolEditor));

        table.getColumnModel().getColumn(7).setCellEditor(new IntCellEditor());

        table.getColumnModel().getColumn(8).setCellEditor(new DefaultCellEditor(textEditor));

        table.getColumnModel().getColumn(9).setCellEditor(new DefaultCellEditor(weaponTypeEditor));

        table.getColumnModel().getColumn(10).setCellEditor(new DefaultCellEditor(moodEditor));

        table.getColumnModel().getColumn(11).setCellEditor(new DefaultCellEditor(boolNullEditor));
    }
}
