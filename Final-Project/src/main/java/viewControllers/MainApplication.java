package viewControllers;

import viewControllers.interfaces.AppView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lwdthe1 on 12/4/16.
 */
public class MainApplication {
    private JFrame mainFrame;
    private JPanel currentVisibleView;
    private JScrollPane currentVisibleScrollView;
    private HomeFeedViewController homeFeedViewController;
    private UserProfileViewController userProfileController;
    private PublicationPageViewController publicationPageViewController;

    public MainApplication() {
        this.mainFrame = new JFrame("SuperSwingMeditor");
        //Create and set up the window.
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainFrame.setSize(new Dimension(1200,900));
        mainFrame.setBackground(Color.white);
        mainFrame.setVisible(true);

        moveToMainViewController();
    }

    private void moveToMainViewController() {
        homeFeedViewController = new HomeFeedViewController(this);
        setVisibleView(homeFeedViewController);
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void setVisibleView(AppViewController viewController) {
        if (currentVisibleView != null) {
            mainFrame.getContentPane().remove(currentVisibleView);
            currentVisibleView.setVisible(false);
            currentVisibleView = null;

        } else if (currentVisibleScrollView != null) {
            System.out.println("CURRENT VISIBLE SCROLL VIEW");
            mainFrame.getContentPane().remove(currentVisibleScrollView);
            currentVisibleScrollView.setVisible(false);
            currentVisibleScrollView = null;
        }
        if (viewController instanceof PublicationPageViewController) {
            JScrollPane contentScrollPane = new JScrollPane();
            contentScrollPane.setViewportView(viewController.getView().getContentPane());
            mainFrame.getContentPane().add(contentScrollPane);
            viewController.viewWillAppear();
            contentScrollPane.setVisible(true);
            contentScrollPane.getVerticalScrollBar().setUnitIncrement(100);
            mainFrame.getContentPane().revalidate();
            mainFrame.getContentPane().repaint();   // This is required in some cases
            currentVisibleScrollView = contentScrollPane;
        } else {
            System.out.println("MOVING TO NEW CONTOLELR");
            JPanel contentPane = viewController.getView().getContentPane();
            mainFrame.getContentPane().add(contentPane);
            viewController.viewWillAppear();
            contentPane.setVisible(true);

            mainFrame.getContentPane().revalidate();
            mainFrame.getContentPane().repaint();   // This is required in some cases
            currentVisibleView = contentPane;
        }

    }

    public HomeFeedViewController getHomeFeedViewController() {
        return homeFeedViewController;
    }

    public UserProfileViewController getUserProfileViewController() {
        if (userProfileController == null) {
            userProfileController = new UserProfileViewController(this);
        }
        return userProfileController;
    }

    public Boolean isCurrentView(AppView appView) {
        return appView.getContentPane() == currentVisibleView;
    }
}
