package views.appViews;

import models.Publication;
import viewControllers.interfaces.AppView;
import viewControllers.HomeFeedViewController;
import viewControllers.AppViewController;
import viewControllers.interfaces.TableView;
import views.LoggedOutActionListener;
import views.subviews.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class HomeFeedView implements AppView, TableView {
    private final HomeFeedViewController appViewController;
    private int width;
    private int height;
    private JPanel contentPane;
    private JTable table;
    private RealTimeNotificationView realTimeNotificationView;
    private JPanel loggedOutPanel;
    private JPanel contentPanel;

    private JButton loginButton;

    public HomeFeedView(HomeFeedViewController appViewController, int width, int height) {
        this.appViewController = appViewController;
        this.width = width;
        this.height = height;
        loginButton = new JButton("Get Started");
        loginButton.addActionListener(new LoggedOutActionListener());
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

    public JButton getLoginButton() {
        return loginButton;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public void createAndShow() {
        this.contentPane = new JPanel(new BorderLayout());
        this.contentPane.setSize(new Dimension(this.contentPane.getWidth(), this.contentPane.getHeight()));

        setUpLoggedOutPanel();
        realTimeNotificationView = new RealTimeNotificationView(this.appViewController);

        addComponentsToPane();
    }

    @Override
    public AppViewController getViewController() {
        return appViewController;
    }

    public void addComponentsToPane() {
        createAndAddScrollableTable();
        contentPane.add(appViewController.getNavigationController().getView().getContentPane(), BorderLayout.NORTH);
        contentPane.add(realTimeNotificationView.getContainer(), BorderLayout.SOUTH);
    }

    public void setUpLoggedOutPanel(){
        loggedOutPanel = new LoggedOutPanel();
        loggedOutPanel.setLayout(new BoxLayout(loggedOutPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("<html>"+ "<p style=\"text-align:center;\">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<font color='white'> A community of Medium editors.</font></p>" +"</html>");
        title.setFont(new Font(title.getName(), Font.PLAIN, 40));
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel subtitle = new JLabel("<html>"+ "<p style=\"text-align:center;\"><font color='white'>A community of Medium editors — Meditors — who need writers who need editors.</font></p>" +"</html>");
        subtitle.setFont(new Font(title.getName(), Font.PLAIN, 30));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JLabel subscript = new JLabel("<html>"+"<p style=\"text-align:center;\"><font color='white'>Signup to advertise and request to contribute to publications or join the chat with fellow contributors.</font></p>" + "</html>");
        subscript.setFont(new Font(title.getName(), Font.PLAIN, 20));
        subscript.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        subscript.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loggedOutPanel.add(title);
        loggedOutPanel.add(subtitle);
        loggedOutPanel.add(subscript);
        loggedOutPanel.add(loginButton);
        loggedOutPanel.setPreferredSize(new Dimension(width,280));
    }

    private void createAndAddScrollableTable() {
        contentPanel = new JPanel();
        contentPanel.setSize(new Dimension(width, height));
        contentPane.add(contentPanel);

        table = new JTable(){
            public TableCellRenderer getCellRenderer(int row, int column ) {
                switch(column) {
                    case 0:
                        return new PublicationImageCellRenderer();
                    case 1:
                        return new PublicationTextCellRenderer();
                    case 2:
                        return new PublicationContributeButtonCellRenderer();
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
        contentPanel.add(loggedOutPanel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(contentPanel.getWidth(), contentPanel.getHeight()));
        contentPanel.add(scrollPane);
    }

    private void handleActionButtonCellClicked(int row, int column) {
        Object value = table.getValueAt(row, column);
        if (value instanceof Publication) {
            Publication publication = (Publication) value;
            JButton contributeButton = publication.getHomeFeedTableCell().contributeButton;
            if (contributeButton.isEnabled()) {
                appViewController.publicationContributeCellClicked(publication, row, column);
            }
        }
    }

    private void handlePublicationImageButtonClick(int row, int column) {
        try {
            Object value = table.getValueAt(row, column);
            if (value instanceof Publication) {
                appViewController.publicationImageButtonClicked((Publication) value);
            }
        } catch (Exception ex) {

        }

    }

    public RealTimeNotificationView getRealTimeNotificationView() {
        return realTimeNotificationView;
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
        table.repaint();// faster than ((AbstractTableModel) table.getModel()).fireTableDataChanged()
    }

    public void removeLoggedOutPanel(){
        contentPanel.remove(loggedOutPanel);
    }

    @Override
    public Boolean isVisibleView() {
        return this.contentPane.isVisible();
    }
}