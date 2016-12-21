package viewControllers;

import models.CurrentUser;
import utils.WebService.socketio.SocketManager;
import viewControllers.interfaces.*;
import views.LoggedOutActionListener;
import views.subviews.NavBarView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import static java.lang.String.format;
import static java.lang.Thread.sleep;

/**
 * Created by lwdthe1 on 9/5/16.
 */
public class NavigationController implements ViewController, AuthListener {
    private final NavBarView view;
    private final MainApplication application;
    private HashMap<String, AppViewController> viewControllersMap = new HashMap<>();

    public NavigationController(MainApplication application) {
        this.application = application;
        this.view = new NavBarView(application.getMainFrame().getWidth());
        setupView();
        setActionListeners();
        registerForAuthEvents();
    }

    @Override
    public View getView() {
        return view;
    }

    public void setupView() {
        this.view.createAndShow();
    }

    private void setActionListeners() {
        view.getPublicationsTabButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeFeedViewController viewController = (HomeFeedViewController) getViewController(ViewControllerKey.PUBLICATIONS);
                moveTo(viewController);
            }
        });
        addProfileButtonActionListener();
    }

    public void addProfileButtonActionListener(){
        if(!CurrentUser.sharedInstance.getIsLoggedIn()){
            view.getProfileButton().addActionListener(new LoggedOutActionListener());
        }
    }

    public void setProfileButtonActionListenerToMove(){
        view.getProfileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserProfileViewController viewController = (UserProfileViewController) getViewController(ViewControllerKey.PROFILE);
                moveTo(viewController);
            }
        });
    }

    public void setProfileButtonActionListenerToLogout(){
        view.getProfileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Logout?", "logout", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    CurrentUser.sharedInstance.logout();
                    moveTo(application.getHomeFeedViewController());
                }
            }
        });
    }

    public void toggleLoggedin(){
        view.toggleLoggedStatus();
    }

    public void moveTo(AppViewController viewController) {
        this.application.setVisibleView(viewController);
    }

    @Override
    public void onEvent(AuthEvent event) {
        switch(event) {
            case LOGGED_IN:
                resetProfileButtonActionListeners();
                setProfileButtonActionListenerToMove();
                toggleLoggedin();
                break;
            case LOGGED_OUT:
                toggleLoggedin();
                break;
        }
    }

    @Override
    public void registerForAuthEvents() {
        CurrentUser.sharedInstance.listen(AuthEvent.LOGGED_IN, this);
        CurrentUser.sharedInstance.listen(AuthEvent.LOGGED_OUT, this);
    }

    private enum ViewControllerKey {
        PUBLICATIONS("Publications"),
        PROFILE("Profile");

        private String value;
        ViewControllerKey(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    private AppViewController getViewController(ViewControllerKey key) {
        if (!viewControllersMap.containsKey(key.getValue())) {
            switch (key) {
                case PUBLICATIONS:
                    return application.getHomeFeedViewController();
                case PROFILE:
                    return application.getUserProfileViewController();
            }
        }
        return null;
    }

    public void resetProfileButtonActionListeners(){
        for( ActionListener al : view.getProfileButton().getActionListeners() ) {
            view.getProfileButton().removeActionListener( al );
        }
    }
}
