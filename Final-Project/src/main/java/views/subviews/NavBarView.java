package views.subviews;

import javax.swing.*;

import models.CurrentUser;
import models.UserSettingsRestCallResult;
import net.miginfocom.swing.MigLayout;
import org.apache.http.HttpException;
import utils.ImageUtils;
import utils.WebService.RestCaller;
import viewControllers.NavigationController;
import viewControllers.interfaces.View;
import viewControllers.interfaces.ViewController;
import views.LoggedOutActionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by keithmartin on 10/23/16.
 */
public class NavBarView implements View {
    private NavigationController viewController;

    private final int MAX_PROFILE_ICON_SIZE = 35;

    // login
    JTextField username = new JTextField();
    JTextField password = new JPasswordField();

    Object[] loginObjects = {
            "Username:", username,
            "Password:", password
    };

    // settings
    JCheckBox instantMessageCB = new JCheckBox();
    JCheckBox statusRequestCB = new JCheckBox();
    JPanel instantMessagePanel = new JPanel();
    JPanel statusRequestPanel = new JPanel();

    private LoggedOutActionListener loggedOutActionListener;


    private  JPanel contentPane;
    private  JLabel mediumIconImgLabel;
    private  JLabel superMeditorLabel;
    private  JButton publicationsTabButton;
    private JButton settingsButton;

    private JButton profileButton;
    private int width;
    private final int height;

    public NavBarView(int frameWidth) {
        this.width = frameWidth;
        this.height = 300;
    }

    private  void setupContentPane() {
        contentPane = new JPanel();
        contentPane.setSize(new Dimension(this.width, height));
        contentPane.setBackground(Color.white);
    }

    private void setMediumIconImgLabel() {
        mediumIconImgLabel = new JLabel();
        mediumIconImgLabel.setSize(new Dimension(100, 100));
        mediumIconImgLabel.setIcon(new ImageIcon("Images/SuperMeditorLogo.png", null));
    }

    private void setSuperMeditorLabel() {
        superMeditorLabel = new JLabel();
        superMeditorLabel.setSize(new Dimension(100, 100));
        superMeditorLabel.setText("SuperMeditor");
        superMeditorLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        superMeditorLabel.setForeground(Color.black);
    }

    private void setPublicationsTabButton() {
        publicationsTabButton = new JButton();
        publicationsTabButton.setSize(new Dimension(100, 100));
        publicationsTabButton.setText("Publications");
        publicationsTabButton.setForeground(Color.gray);
        publicationsTabButton.setBorderPainted(false);
    }

    private void setSettingsButton() {
        settingsButton = new JButton();
        settingsButton.setSize(new Dimension(100, 100));
        settingsButton.setIcon(new ImageIcon("Images/settings_icon.png", null));
        settingsButton.setBorderPainted(false);
        settingsButton.setEnabled(false);
        settingsButton.setVisible(false);
    }

    private void setProfileButton() {
        profileButton = new JButton();
        profileButton.setMaximumSize(new Dimension(100, 100));
        profileButton.setIcon(new ImageIcon("Images/Profile.png", null));
        profileButton.setBorderPainted(false);
        profileButton.setIcon(new ImageIcon("Images/Profile.png", null));
    }

    private void setActionListeners(){
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UserSettingsRestCallResult userSettingsRestCallResult = RestCaller.sharedInstance.getCurrentUserSettings();
                    CurrentUser.sharedInstance.updateSettings(userSettingsRestCallResult.getInstantMessage(), userSettingsRestCallResult.getStatusRequest());
                    instantMessageCB.setSelected(userSettingsRestCallResult.getInstantMessage());
                    statusRequestCB.setSelected(userSettingsRestCallResult.getStatusRequest());
                    int option = JOptionPane.showConfirmDialog(null, getSettingsObjects(), "Settings", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (option == JOptionPane.OK_OPTION) {
                        Boolean instantMessage = instantMessageCB.isSelected();
                        Boolean statusRequest = statusRequestCB.isSelected();

                        if(instantMessage != userSettingsRestCallResult.getInstantMessage()){
                            RestCaller.sharedInstance.updateCurrentUserSettings(UserSettingsRestCallResult.new_message_notification_instant,instantMessage);
                        }

                        if(statusRequest != userSettingsRestCallResult.getStatusRequest()){
                            RestCaller.sharedInstance.updateCurrentUserSettings(UserSettingsRestCallResult.contribution_request_decision,statusRequest);
                        }
                    }
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (HttpException e1) {
                    e1.printStackTrace();
                }
            }

        });

    }

    private void setLayout() {
        contentPane.setLayout(new MigLayout("", // Layout Constraints
                "[][][][]440[]", // Column constraints with default align
                "")); // Row constraints
    }

    private void addComponents() {
        contentPane.add(mediumIconImgLabel, "cell 0 0");
        contentPane.add(superMeditorLabel, "cell 1 0");
        contentPane.add(publicationsTabButton, "cell 2 0");
        contentPane.add(settingsButton, "cell 3 0");
        contentPane.add(profileButton, "pushx, growx");
    }


    public JButton getPublicationsTabButton() {
        return publicationsTabButton;
    }

    public Object[] getSettingsObjects(){
        Object[] objects = new Object[2];
        objects[0] = instantMessagePanel;
        objects[1] = statusRequestPanel;
        return objects;
    }

    public void setUpSettingPanels() {
        instantMessagePanel.setLayout(new BoxLayout(instantMessagePanel,BoxLayout.LINE_AXIS));
        instantMessagePanel.add(new JLabel("Recieve instant message notifications"));
        instantMessagePanel.add(instantMessageCB);
        statusRequestPanel.setLayout(new BoxLayout(statusRequestPanel,BoxLayout.LINE_AXIS));
        statusRequestPanel.add(new JLabel("Status of Your Requests to Contribute"));
        statusRequestPanel.add(statusRequestCB);
    }

    public void toggleLoggedStatus(){
        boolean isLoggedIn = CurrentUser.sharedInstance.getIsLoggedIn();
        settingsButton.setEnabled(isLoggedIn);
        settingsButton.setVisible(isLoggedIn);
        if(isLoggedIn){
            String imageUrl = CurrentUser.sharedInstance.getUser().getImageUrl();
            Image resizedImage =
                    ImageUtils.loadImage(imageUrl).getScaledInstance(MAX_PROFILE_ICON_SIZE, MAX_PROFILE_ICON_SIZE, Image.SCALE_FAST);
            ImageIcon icon = new ImageIcon(resizedImage);
            profileButton.setIcon(icon);
        } else {
            profileButton.setIcon(new ImageIcon("Images/Profile.png", null));
        }
    }

    public JButton getProfileButton() {
        return profileButton;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void createAndShow() {
        setupContentPane();
        setMediumIconImgLabel();
        setSuperMeditorLabel();
        setPublicationsTabButton();
        setProfileButton();
        setSettingsButton();
        setLayout();
        addComponents();
        setButtonHoverListeners();
        setUpSettingPanels();
        setActionListeners();
    }

    @Override
    public ViewController getViewController() {
        return this.viewController;
    }

    @Override
    public JPanel getContentPane() {
        return this.contentPane;
    }

    private void setButtonHoverListeners() {
        publicationsTabButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                publicationsTabButton.setForeground(Color.BLACK);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                publicationsTabButton.setForeground(Color.GRAY);
            }
        });
    }
}
