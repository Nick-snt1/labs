package src.core.gui;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class IntCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final JSpinner editor;

    public IntCellEditor() {
        editor = new JSpinner(); editor.setFont(GUI.getMyFont(13));
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editor.setValue(value);
        return editor;
    }

    public Object getCellEditorValue() {
        return editor.getValue();
    }
}
