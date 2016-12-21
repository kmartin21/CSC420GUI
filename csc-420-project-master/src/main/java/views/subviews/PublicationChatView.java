package views.subviews;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by keithmartin on 12/6/16.
 */
public class PublicationChatView  {
    private int width;
    private int height;
    private JPanel contentPane;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton sendMessageButton;
    private JTextArea chatTextArea;
    private JLabel currentUserImageLabel;
    private Image userImage;

    public PublicationChatView(Image userImage, int width, int height) {
        this.width = width;
        this.height = height;
        this.userImage = userImage;
        createAndShow();
    }

    public JTable getTable() {
        return table;
    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public void createAndShow() {
        this.contentPane = new JPanel();
        this.contentPane.setLayout(new MigLayout("", "[][]", "[][]"));
        this.contentPane.setBackground(Color.white);
        addComponentsToPane();
    }

    public void addComponentsToPane() {
        createAndAddScrollableTable();
        //addCurrentUserImage();
        addChatTextArea();
        addSendMessageButton();
    }

    private void createAndAddScrollableTable() {

        table = new JTable(){

            public TableCellRenderer getCellRenderer(int row, int column ) {
                switch(column) {
                    case 0:
                        return new PublicationImageCellRenderer();
                    case 1:
                        return new PublicationTextCellRenderer();
                    default:
                        return null;
                }
            }
        };

        table.setRowHeight(100);

        scrollPane = new JScrollPane(table);
        scrollPane.getVerticalScrollBar().setUnitIncrement(100);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        table.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int lastIndex = table.getRowCount() - 1;
                table.changeSelection(lastIndex, 0, false, false);
            }
        });
        contentPane.add(scrollPane, "cell 0 0,span 3, pushx, growx");
    }

    private void addCurrentUserImage() {
        currentUserImageLabel = new JLabel();
        Dimension dimensions = new Dimension(30, 30);
        currentUserImageLabel.setSize(dimensions);
        currentUserImageLabel.setIcon(new ImageIcon(userImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        currentUserImageLabel.setBorder(BorderFactory.createEmptyBorder());

        contentPane.add(currentUserImageLabel, "cell 0 1");

    }

    private void addChatTextArea() {
        chatTextArea = new JTextArea();
        chatTextArea.setLineWrap(true);
        JScrollPane chatTextScrollingArea = new JScrollPane(chatTextArea);
        contentPane.add(chatTextScrollingArea, "cell 0 1, pushx, growx");
    }

    private void addSendMessageButton() {
        sendMessageButton = new JButton("Send");
        contentPane.add(sendMessageButton, "cell 1 1");
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public JScrollPane getScrollPane() { return scrollPane; }

    public void refreshTable() {
        table.repaint();
    }

    public JButton getSendMessageButton() {
        return sendMessageButton;
    }

    public JTextArea getChatTextArea() {
        return chatTextArea;
    }
}
