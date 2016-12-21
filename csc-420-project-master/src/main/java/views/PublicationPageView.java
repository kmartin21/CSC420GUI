package views;

import models.Publication;
import viewControllers.interfaces.AppView;
import viewControllers.PublicationPageViewController;
import viewControllers.AppViewController;
import views.subviews.PublicationChatView;
import views.subviews.RealTimeNotificationView;

import javax.swing.*;
import java.awt.*;

import static java.lang.String.format;
import static utils.TextUtils.*;

/**
 * Created by keithmartin on 12/5/16.
 */
public class PublicationPageView implements AppView {

    private JLabel nameLabel;
    private JLabel aboutLabel;
    private JLabel descriptionLabel;
    private JLabel logoLabel;
    private JLabel statsLabel;
    private JLabel requestedStatsLabel;
    private JLabel viewedStatsLabel;
    private JLabel relationLabel;
    private JLabel relationDescriptionLabel;
    private JLabel contributorLabel;
    private JPanel contentPane;
    private JPanel northPane;
    private JPanel southPane;

    private static Insets LEFT_PAD_15 = new Insets(100,100, 0, 0);
    private static Insets LEFT_PAD_20 = new Insets(0,20, 0, 0);
    private static Insets RIGHT_PAD_15 = new Insets(0,0, 0, 15);
    private static Insets TOP_5_LEFT_PAD_20 = new Insets(0,105, 0, 0);
    private static Insets TOP_10_LEFT_PAD_20 = new Insets(10,105, 0, 0);
    private static Insets TOP_20_LEFT_PAD_20 = new Insets(20, 105, 0, 0);
    private static Insets TOP_30_LEFT_PAD_20 = new Insets(30, 105, 0, 0);
    private static final Insets TOP_PAD_5 = new Insets(5, 0, 0, 0);

    private int width;
    private int height;

    private PublicationChatView publicationChatView;

    private PublicationPageViewController publicationPageViewController;

    private RealTimeNotificationView realTimeNotificationView;
    private JPanel publicationPane;

    public PublicationPageView(PublicationPageViewController publicationPageViewController, int width, int height) {
        this.width = width;
        this.height = height;
        this.publicationPageViewController = publicationPageViewController;
        this.publicationChatView = new PublicationChatView(publicationPageViewController.getPublication().getImage(), width, height);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public void createAndShow() {
        this.contentPane = new JPanel();
        this.contentPane.setSize(new Dimension(width, height));
        this.contentPane.setBackground(Color.white);
        GridBagLayout gridbagLayout = new GridBagLayout();
        this.contentPane.setLayout(new BorderLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        Publication pub = publicationPageViewController.getPublication();
        realTimeNotificationView = new RealTimeNotificationView(publicationPageViewController);


        contentPane.add(publicationPageViewController.getNavigationController().getView().getContentPane(), BorderLayout.NORTH);
        contentPane.add(realTimeNotificationView.getContainer(), BorderLayout.SOUTH);

        northPane = new JPanel(gridbagLayout);
        southPane = new JPanel(gridbagLayout);
        northPane.setBackground(Color.white);
        southPane.setBackground(Color.white);
        addComponentsToPane(constraints, pub);
        contentPane.add(northPane, BorderLayout.NORTH);
    }

    private void addComponentsToPane(GridBagConstraints constraints, Publication pub) {
        addNameLabel(constraints, pub);
        addAboutLabel(constraints);
        addDescriptionLabel(constraints, pub);
        addLogoLabel(constraints, pub);
        addStatsLabel(constraints);
        addRequestedStatsLabel(constraints, pub);
        addViewedStatsLabel(constraints, pub);
        addRelationLabel(constraints);
        addRelationDescriptionLabel(constraints);
        addContributerLabel(constraints, pub);
        addChatTable(constraints);
    }

    private void addNameLabel(GridBagConstraints constraints, Publication pub) {
        String nameHTML = format("<html><body><p style='%s'>%s</p></body></html>", FEED_PUBLICATION_NAME_STYLE, pub.getName());
        nameLabel = new JLabel(nameHTML);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = LEFT_PAD_15;
        this.northPane.add(nameLabel, constraints);
    }

    private void addAboutLabel(GridBagConstraints constraints) {
        String about = format("<html><body><p style='%s'>%s</p></body></html>", SUBTITLE_TEXT_STYLE, "ABOUT");
        aboutLabel = new JLabel(about);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.insets = TOP_10_LEFT_PAD_20;
        this.northPane.add(aboutLabel, constraints);
    }

    private void addLogoLabel(GridBagConstraints constraints, Publication pub) {
        logoLabel = new JLabel();
        Dimension dimensions = new Dimension(60, 60);
        logoLabel.setSize(dimensions);
        logoLabel.setIcon(new ImageIcon(pub.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.insets = TOP_10_LEFT_PAD_20;
        this.northPane.add(logoLabel, constraints);
    }

    private void addDescriptionLabel(GridBagConstraints constraints, Publication pub) {
        String description = format("<html><body><p style='%s'>%s</p></body></html>", DESCRIPTIVE_TEXT_GREY_STYLE, pub.getDescription());
        descriptionLabel = new JLabel(description);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.insets = TOP_10_LEFT_PAD_20;
        this.northPane.add(descriptionLabel, constraints);
    }

    private void addStatsLabel(GridBagConstraints constraints) {
        String stats = format("<html><body><p style='%s'>%s</p></body></html>", SUBTITLE_TEXT_STYLE, "STATS");
        statsLabel = new JLabel(stats);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.insets = TOP_20_LEFT_PAD_20;
        this.northPane.add(statsLabel, constraints);
    }

    private void addRequestedStatsLabel(GridBagConstraints constraints, Publication pub) {
        String requestedStats = format("<html><body><p style='%s'>%s</p></body></html>", DESCRIPTIVE_TEXT_GREY_STYLE, "Requested " + pub.getPubIdTotalContributionRequests() + " times.");
        requestedStatsLabel = new JLabel(requestedStats);
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.insets = TOP_5_LEFT_PAD_20;
        this.northPane.add(requestedStatsLabel, constraints);
    }

    private void addViewedStatsLabel(GridBagConstraints constraints, Publication pub) {
        String viewedStats = format("<html><body><p style='%s'>%s</p></body></html>", DESCRIPTIVE_TEXT_GREY_STYLE, "Viewed " + pub.getPubIdTotalVisits() + " times. " + pub.getPubIdTotalVisitsByCurrentUser() + " by you.");
        viewedStatsLabel = new JLabel(viewedStats);
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.insets = TOP_5_LEFT_PAD_20;
        this.northPane.add(viewedStatsLabel, constraints);
    }

    private void addRelationLabel(GridBagConstraints constraints) {
        String relation = format("<html><body><p style='%s'>%s</p></body></html>", SUBTITLE_TEXT_STYLE, "RELATION");
        relationLabel = new JLabel(relation);
        constraints.gridx = 1;
        constraints.gridy = 7;
        constraints.insets = TOP_30_LEFT_PAD_20;
        this.northPane.add(relationLabel, constraints);
    }

    private void addRelationDescriptionLabel(GridBagConstraints constraints) {
        String relationDescription = format("<html><body><p style='%s'>%s</p></body></html>", DESCRIPTIVE_TEXT_GREY_STYLE, "Your relationship with this publication.");
        relationDescriptionLabel = new JLabel(relationDescription);
        constraints.gridx = 1;
        constraints.gridy = 8;
        constraints.insets = TOP_10_LEFT_PAD_20;
        this.northPane.add(relationDescriptionLabel, constraints);
    }

    private void addContributerLabel(GridBagConstraints constraints, Publication pub) {
        String contributorRole = format("<html><body><p style='%s'>%s</p></body></html>", DESCRIPTIVE_TEXT_GREY_STYLE, pub.getContributorRole());
        contributorLabel = new JLabel(contributorRole);
        constraints.gridx = 1;
        constraints.gridy = 9;
        constraints.insets = TOP_5_LEFT_PAD_20;
        this.northPane.add(contributorLabel, constraints);
    }

    private void addChatTable(GridBagConstraints constraints) {
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.contentPane.add(publicationChatView.getContentPane(), BorderLayout.SOUTH);
        //this.southPane.add(publicationChatView.getContentPane(), constraints);
    }

    @Override
    public AppViewController getViewController() {
        return publicationPageViewController;
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public JTable getTable() { return publicationChatView.getTable(); }

    public void refreshTable() { publicationChatView.refreshTable(); }

    public JButton getSendMessageButton() {
        return publicationChatView.getSendMessageButton();
    }

    public JTextArea getChatTextArea() {
        return publicationChatView.getChatTextArea();
    }

    public RealTimeNotificationView getRealTimeNotificationView() {
        return realTimeNotificationView;
    }

    public JScrollPane getScrollPane() { return publicationChatView.getScrollPane(); }

    @Override
    public Boolean isVisibleView() {
        return this.getContentPane().isVisible();
    }

}
