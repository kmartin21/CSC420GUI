package viewControllers;

import models.ChatMessage;
import models.CurrentUser;
import models.Publication;
import models.RequestDecisionNotification;
import org.json.JSONObject;
import utils.PublicationsService;
import utils.WebService.socketio.SocketEvent;
import utils.WebService.socketio.SocketListener;
import utils.WebService.socketio.SocketManager;
import viewControllers.interfaces.AppView;
import viewControllers.interfaces.AuthEvent;
import viewControllers.interfaces.AuthListener;
import views.LoggedOutActionListener;
import views.appViews.HomeFeedView;
import views.subviews.PublicationContributeButtonCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Semaphore;

import static java.lang.String.format;
import static java.lang.Thread.sleep;

/**
 * Created by lwdthe1 on 9/5/16.
 */
public class HomeFeedViewController implements AppViewController, SocketListener, AuthListener {
    private final HomeFeedView view;
    private final MainApplication application;
    private final NavigationController navigationController;


    private SocketManager socketManger;
    private Semaphore setupViewWhileLoadingSemaphore = new Semaphore(1);
    private PublicationsService publicationsService;
    private ArrayList<Publication> publications;

    public HomeFeedViewController(MainApplication application) {
        this.application = application;
        //acquire the lock here before starting load
        try {
            setupViewWhileLoadingSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        publicationsService = PublicationsService.sharedInstance;
        loadFeed();
        this.view = new HomeFeedView(this, application.getMainFrame().getWidth(), application.getMainFrame().getHeight());
        this.navigationController = new NavigationController(application);
        setupView();
        setupViewWhileLoadingSemaphore.release();
        startSocketIO();
        registerForAuthEvents();
    }

    @Override
    public NavigationController getNavigationController() {
        return this.navigationController;
    }

    @Override
    public AppView getView() {
        return view;
    }

    public void setupView() {
        this.view.createAndShow();
        view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    @Override
    public void transitionTo(AppViewController appViewController) {
        this.getNavigationController().moveTo(appViewController);
    }

    @Override
    public void viewWillAppear() {
        navigationController.toggleLoggedin();
        if (publications != null && !publications.isEmpty()) {
            view.refreshTable();
        }
    }

    private void loadFeed() {
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                PublicationsService.sharedInstance.loadAll();
                return true;
            }

            // Can safely update the GUI from this method.
            protected void done() {
                showPublications();
            }
        };
        worker.execute();
    }

    private void showPublications() {
        try {
            //acquire the lock to assure the view is ready
            setupViewWhileLoadingSemaphore.acquire();
            publications = publicationsService.getAll();
            publications.sort(new Comparator<Publication>() {
                @Override
                public int compare(Publication o1, Publication o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
            setupPublicationsTable(publications);
        } catch (InterruptedException e) {
            System.out.printf("\nCouldn't show publications because: %s", e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupPublicationsTable(ArrayList<Publication> publications) {
        DefaultTableModel model = new DefaultTableModel();
        view.getTable().setModel(model);

        model.addColumn("", publications.toArray());
        model.addColumn(format("%d Publications Looking for Writers", publications.size()), publications.toArray());
        model.addColumn("", publications.toArray());

        view.getTable().getColumnModel().getColumn(1).setPreferredWidth(400);
        view.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    private void startSocketIO() {
        this.socketManger = SocketManager.sharedInstance;
        socketManger.listen(SocketEvent.CONNECTED, this);
        socketManger.setupAndConnect();
        registerForEvents();
    }

    @Override
    public void onEvent(SocketEvent event, JSONObject payload) {
        switch(event) {
            case CHAT_MESSAGE:
                if(CurrentUser.sharedInstance.getInstantNotificationsSetting()){
                    updateRealtimeNotificationWithNewChatMessage(payload);
                }
                break;
            case NOTIFICATION_REQUEST_TO_CONTRIBUTE_DECISION:
                    updateRealtimeNotificationWithNewRequestDecision(payload);

                break;
        }
    }

    private void updateRealtimeNotificationWithNewChatMessage(JSONObject payload) {
        ChatMessage chatMessage = new ChatMessage(payload);
        final Publication chatPub = publicationsService.getById(chatMessage.getPublicationId());
        if (chatPub == null) return;

        view.getRealTimeNotificationView().updateNotification("New Contributor Message",
                format("A contributor said: %s", chatMessage.getText()),
                chatPub.getImage(), new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PublicationPageViewController publicationPageViewController = new PublicationPageViewController(application, getSelf(), chatPub);
                        navigationController.moveTo(publicationPageViewController);
                    }
                }
        );
    }

    private void updateRealtimeNotificationWithNewRequestDecision(JSONObject payload) {
        RequestDecisionNotification requestDecisionNotification = new RequestDecisionNotification(payload);
        System.out.printf("\nReceived requestDecisionNotification: %s\n", requestDecisionNotification);
        final Publication requestPub = publicationsService.getById(requestDecisionNotification.getPublicationId());
        if (requestPub == null) return;


        boolean requestApproved = requestDecisionNotification.getAccepted();
        CurrentUser.sharedInstance.getRequestToContributeByPubId(requestPub.getId()).updateAccepted(requestApproved);
        requestPub.setCurrentUserIsContributor(requestApproved);
        requestPub.setCurrentUserRequestWasRejected(!requestApproved);
        view.refreshTable();

        String title = requestApproved? "Request Approved" : "Request Denied";
        if(CurrentUser.sharedInstance.getRequestDecisionSetting()) {
            view.getRealTimeNotificationView().updateNotification(title,
                    format("Your request to contribute to %s was %s",
                            requestPub.getName(),
                            requestApproved ? "approved." : "denied."),
                    requestPub.getImage(), new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            PublicationPageViewController publicationPageViewController = new PublicationPageViewController(application, getSelf(), requestPub);
                            navigationController.moveTo(publicationPageViewController);
                        }

                    }
            );
        }
    }

    @Override
    public void onEvent(AuthEvent event) {
        System.out.println("received event in HomeFeedViewController");
        switch(event) {
            case LOGGED_IN:
                view.removeLoggedOutPanel();
                break;
            case LOGGED_OUT:
                //navigationController.setProfileButtonActionListenerToLogin();
                break;
        }
    }

    @Override
    public void registerForAuthEvents() {
        CurrentUser.sharedInstance.listen(AuthEvent.LOGGED_IN, this);
        CurrentUser.sharedInstance.listen(AuthEvent.LOGGED_OUT, this);
    }

    @Override
    public void registerForEvents() {
        socketManger.listen(SocketEvent.CHAT_MESSAGE, this);
        socketManger.listen(SocketEvent.NOTIFICATION_REQUEST_TO_CONTRIBUTE_DECISION, this);
    }

    public void publicationImageButtonClicked(Publication publication) {
        PublicationPageViewController publicationPageViewController = new PublicationPageViewController(application, getSelf(), publication);
        navigationController.moveTo(publicationPageViewController);

    }

    public void publicationContributeCellClicked(Publication publication, int row, int column) {
        PublicationContributeButtonCellRenderer homeFeedTableCell = publication.getHomeFeedTableCell();
        JButton contributeButton = homeFeedTableCell.contributeButton;
        if(CurrentUser.sharedInstance.getIsLoggedIn()){
            if (contributeButton.getText() == "Contribute") {
                PublicationsService.sharedInstance.requestToContributeById(publication.getId());
            } else {
                PublicationsService.sharedInstance.retractRequestToContributeById(publication.getId());
            }
        } else {
            new LoggedOutActionListener().actionPerformed(null);
        }

        view.onContributeRequestSuccess(row, column);
    }

    public PublicationsService getPublicationsService() {
        return publicationsService;
    }

    public SocketManager getSocketManger() {
        return socketManger;
    }

    public HomeFeedViewController getSelf() { return this; }
}
