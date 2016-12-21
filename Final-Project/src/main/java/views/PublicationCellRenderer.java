package views;

import models.Publication;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

/**
 * Created by Andres on 10/23/16.
 */
public class PublicationCellRenderer extends JPanel implements TableCellRenderer {
    static Insets TOP_5_LEFT_PAD_15 = new Insets(5, 15, 0, 0);


    private void addContributeButton(GridBagConstraints constraints) {
        JButton contributeButton = new JButton("Contribute");
        constraints.gridx = 2;
        constraints.gridy = 0;
        this.add(contributeButton, constraints);
    }

    private void addMetaInfoLabel(GridBagConstraints constraints, Publication pub) {
        String metaInfo = pub.getContributorUsername() + " · " + "Requested " + pub.getPubIdTotalContributionRequests() + " times" + " · " + "viewed " + pub.getPubIdTotalVisits() + " times";

        JLabel metaInfoLabel = new JLabel(metaInfo);
        metaInfoLabel.setFont(metaInfoLabel.getFont().deriveFont(10.0f));
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.ipady = 5;
        constraints.insets = TOP_5_LEFT_PAD_15;
        this.add(metaInfoLabel, constraints);
    }

    private void addDescriptionLabel(GridBagConstraints constraints, Publication pub) {
        String description = "<html><body width='500px'>" +
                pub.getDescription() +
                "</body></html>";

        JLabel descriptionLabel = new JLabel(description);
        constraints.weightx = 0.7;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = TOP_5_LEFT_PAD_15;
        this.add(descriptionLabel, constraints);
    }

    private void addNameLabel(GridBagConstraints constraints, Publication pub) {
        JLabel pubNameLabel = new JLabel(pub.getName());
        pubNameLabel.putClientProperty("labelType", "pubNameLabel");
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = TOP_5_LEFT_PAD_15;
        this.add(pubNameLabel, constraints);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.white);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        Publication pub = (Publication) value;

        addImageLabel(constraints, pub);
        addNameLabel(constraints, pub);
        addDescriptionLabel(constraints, pub);
        addMetaInfoLabel(constraints, pub);
        addContributeButton(constraints);

        return this;
    }

    private void addImageLabel(GridBagConstraints constraints, Publication pub) {
        JLabel imageLabel = new JLabel();
        Dimension dimensions = new Dimension(30, 30);
        imageLabel.setSize(dimensions);
        imageLabel.setIcon(new ImageIcon(pub.getImage().getScaledInstance(
                30,
                30,
                Image.SCALE_SMOOTH)));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = TOP_5_LEFT_PAD_15;
        this.add(imageLabel, constraints);
    }
}

