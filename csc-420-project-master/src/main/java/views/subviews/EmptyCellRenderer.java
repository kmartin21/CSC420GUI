package views.subviews;

import models.Publication;
import utils.PublicationsService;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Andres on 10/23/16.
 */
public class EmptyCellRenderer extends JPanel implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);
        return this;
    }
}
