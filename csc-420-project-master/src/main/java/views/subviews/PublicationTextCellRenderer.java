package views.subviews;

import models.ChatMessage;
import models.Publication;
import models.RequestToContribute;
import utils.TextUtils;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

import static java.lang.String.format;
import static utils.TextUtils.DESCRIPTIVE_TEXT_STYLE;
import static utils.TextUtils.FEED_PUBLICATION_NAME_STYLE;

/**
 * Created by Andres on 10/23/16.
 */
public class PublicationTextCellRenderer extends JPanel implements TableCellRenderer {
    private static Insets LEFT_PAD_20 = new Insets(0,20, 0, 0);
    private static Insets TOP_5_LEFT_PAD_20 = new Insets(5,20, 0, 0);


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        GridBagLayout gridbagLayout = new GridBagLayout();
        this.setLayout(gridbagLayout);
        this.setBackground(Color.white);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        Publication pub = null;
        ChatMessage chatMessage = null;

        String nameLabelText = null;
        String descriptionLabelText = null;
        String metaInfoText = null;
        if (value instanceof Publication) {
            pub = (Publication) value;
            nameLabelText = pub.getName();
            descriptionLabelText = pub.getDescription();
            metaInfoText = pub.getContributorUsername() + " · " + "Requested " + pub.getPubIdTotalContributionRequests() + " times" + " · " + "viewed " + pub.getPubIdTotalVisits() + " times";
        } else if (value instanceof RequestToContribute) {
            pub = ((RequestToContribute) value).getPublication();
            nameLabelText = pub.getName();
            descriptionLabelText = pub.getDescription();
            metaInfoText = pub.getContributorUsername() + " · " + "Requested " + pub.getPubIdTotalContributionRequests() + " times" + " · " + "viewed " + pub.getPubIdTotalVisits() + " times";
        } else if (value instanceof ChatMessage) {
            chatMessage = (ChatMessage) value;
            if (chatMessage.getUserName() != null) nameLabelText = chatMessage.getUserName();
            else nameLabelText = chatMessage.getUserId();

            descriptionLabelText = chatMessage.getText();
            metaInfoText = chatMessage.getContributorRole();
        }

        if (pub != null) {
            addNameLabel(constraints, nameLabelText);
            addDescriptionLabel(constraints, descriptionLabelText);
            addMetaInfoLabel(constraints, metaInfoText);
        } else if (chatMessage != null) {
            addNameLabel(constraints, nameLabelText);
            addDescriptionLabel(constraints, descriptionLabelText);
            addMetaInfoLabel(constraints, metaInfoText);
        }

        return this;
    }

    private void addNameLabel(GridBagConstraints constraints, String nameLabelText) {
            String nameHTML = format("<html><body><p style='%s'>%s</p></body></html>", FEED_PUBLICATION_NAME_STYLE, nameLabelText);
            JLabel pubNameLabel = new JLabel(nameHTML);
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.insets = LEFT_PAD_20;
            this.add(pubNameLabel, constraints);
    }

    private void addDescriptionLabel(GridBagConstraints constraints, String descriptionLabelText) {
            String description1stLine = descriptionLabelText, description2ndLine = "";
            if (descriptionLabelText.split(" ").length > 9) {
                description1stLine = descriptionLabelText.substring(0, descriptionLabelText.length() / 2);
                description2ndLine = descriptionLabelText.substring(descriptionLabelText.length() / 2, descriptionLabelText.length() - 1);
            }

            String descriptionHTML = format("<html><body><p style='%s'>%s<br>%s</p></body></html>", DESCRIPTIVE_TEXT_STYLE, description1stLine, description2ndLine);

            JLabel descriptionLabel = new JLabel(descriptionHTML);
            constraints.weightx = 0.7;
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.insets = LEFT_PAD_20;
            this.add(descriptionLabel, constraints);
    }

    private void addMetaInfoLabel(GridBagConstraints constraints, String metaInfoText) {
            JLabel metaInfoLabel = new JLabel(metaInfoText);
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.insets = TOP_5_LEFT_PAD_20;
            this.add(metaInfoLabel, constraints);
    }

}
