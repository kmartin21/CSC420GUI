package views.appViews;

import models.Publication;
import models.RequestToContribute;
import viewControllers.UserProfileViewController;
import viewControllers.interfaces.AppView;
import viewControllers.AppViewController;
import viewControllers.interfaces.TableView;
import views.subviews.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class UserProfileView implements AppView, TableView {
    private final UserProfileViewController viewController;
    private int width;
    private int height;
    private JPanel contentPane;
    private JTable table;
    private RealTimeNotificationView realTimeNotificationView;

    public UserProfileView(UserProfileViewController viewController, int width, int height) {
        this.viewController = viewController;
        this.width = width;
        this.height = height;
    }

    public JTable getTable() {
        return table;
    }


    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public void createAndShow() {
        this.contentPane = new JPanel(new BorderLayout());
        this.contentPane.setSize(new Dimension(this.contentPane.getWidth(), this.contentPane.getHeight()));
        realTimeNotificationView = new RealTimeNotificationView(this.viewController);
        addComponentsToPane();
    }

    @Override
    public AppViewController getViewController() {
        return viewController;
    }

    public void addComponentsToPane() {
        createAndAddScrollableTable();
        contentPane.add(viewController.getNavigationController().getView().getContentPane(), BorderLayout.NORTH);
        contentPane.add(realTimeNotificationView.getContainer(), BorderLayout.SOUTH);
    }

    private void createAndAddScrollableTable() {
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);

        panel.setSize(new Dimension(width, height));

        table = new JTable(){
            public TableCellRenderer getCellRenderer(int row, int column ) {
                switch(column) {
                    case 0:
                        return new PublicationImageCellRenderer();
                    case 1:
                        return new PublicationTextCellRenderer();
                    case 2:
                        return new RequestStatusButtonCellRenderer();
                    case 3:
                        return new EmptyCellRenderer();
                    default:
                        return null;
                }
            }

            public boolean isCellEditable(int row, int column) {
                switch(column) {
                    case 0:
                        handlePublicationImageButtonClick(row, column);
                        return false;
                    case 2:
                        handleActionButtonCellClicked(row, column);
                        return false;
                    default:
                        return false;
                }
            }
        };


        table.setRowHeight(100);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(panel.getWidth(), panel.getHeight()));
        panel.add(scrollPane);
    }

    private void handleActionButtonCellClicked(int row, int column) {
        Object value = table.getValueAt(row, column);
        if (value instanceof RequestToContribute) {
            RequestToContribute requestToContribute = (RequestToContribute) value;
            JButton contributeButton = requestToContribute.getFeedTableCell().contributeButton;
            if (contributeButton.isEnabled()) {
                viewController.publicationContributeCellClicked(requestToContribute, row, column);
            }
        }
    }

    private void handlePublicationImageButtonClick(int row, int column) {
        Object value = table.getValueAt(row, column);
        if (value instanceof Publication) {
            viewController.publicationImageButtonClicked((Publication) value);
        }

    }

    public RealTimeNotificationView getRealTimeNotificationView() {
        return realTimeNotificationView;
    }

    @Override
    public Boolean isVisibleView() {
        return this.contentPane.isVisible();
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public void onContributeRequestSuccess(int row, int column) {
        refreshTableCell(row, column);
    }

    public void refreshTableCell(int row, int column) {
        ((AbstractTableModel) table.getModel()).fireTableCellUpdated(row, column);
    }

    public void refreshTable() {
        table.repaint();// faster than ((AbstractTableModel) table.getModel()).fireTableDataChanged();
    }
}