package views.subviews;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import utils.ImageUtils;
import utils.TimeUtils;
import viewControllers.AppViewController;
import viewControllers.interfaces.View;
import viewControllers.interfaces.ViewController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

/**
 * Created by lwdthe1 on 10/23/16.
 */
public class RealTimeNotificationView implements View {
    private final AppViewController viewController;
    private  JPanel contentPane;
    private  JLabel imageLabel;
    private  JLabel descriptionLabel, titleLabel;
    private  JButton actionButton;
    private Semaphore updateSemaphore = new Semaphore(1);
    private int width;
    private int height;

    public RealTimeNotificationView(AppViewController viewController) {
        this.viewController = viewController;
        this.width = viewController.getView().getWidth();
        this.height = 100;
        createAndShow();
    }

    @Override
    public void createAndShow() {
        setContainerPanel();
        setMediumIconImgLabel();
        setupTitleLabel();
        setupDescriptionLabel();
        setupActionButton();

        setLayout();
        addComponents();
    }

    private void setContainerPanel() {
        contentPane = new JPanel();
        contentPane.setSize(new Dimension(width, height));
        contentPane.setBackground(Color.white);
        contentPane.setVisible(false);
    }

    private void setMediumIconImgLabel() {
        imageLabel = new JLabel();
        imageLabel.setSize(new Dimension(30, 30));
    }

    private void setupTitleLabel() {
        titleLabel = new JLabel();
        titleLabel.setText("Notification");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 12));
        titleLabel.setForeground(Color.black);
    }

    private void setupDescriptionLabel() {
        descriptionLabel = new JLabel();
        descriptionLabel.setText("This is a real-time notification");
        descriptionLabel.setFont(new Font("Helvetica", Font.PLAIN, 12));
        descriptionLabel.setForeground(Color.black);
    }

    private void setupActionButton() {
        actionButton = new JButton();
        actionButton.setSize(new Dimension(10, 10));
        actionButton.setText("View");
        actionButton.setForeground(Color.gray);
        actionButton.setBorderPainted(false);
    }


    private void setLayout() {
        contentPane.setLayout(new MigLayout("", // Layout Constraints
                "[][][][]460[]", // Column constraints with default align
                "")); // Row constraints
    }

    private void addComponents() {
        contentPane.add(imageLabel, "cell 0 0");
        contentPane.add(titleLabel, "cell 1 0");
        contentPane.add(descriptionLabel, "cell 1 1");
        contentPane.add(actionButton, "cell 1 2");
    }

    public JPanel getContainer() {
        return contentPane;
    }

    public void updateNotification(final String notificationTitle, final String notificationDescription, final BufferedImage image, final ActionListener onClickAction) {
        if (viewController.getView().isVisibleView()) {
            SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    updateSemaphore.acquire();
                    //wait 2 seconds to avoid rapid succession of flashing notifications
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {}
                    return true;
                }

                // Can safely update the GUI from this method.
                protected void done() {
                    update(notificationTitle, notificationDescription, image, onClickAction);
                }
            };
            worker.execute();
        }
    }

    private void update(String notificationTitle, final String notificationDescription, final BufferedImage image, ActionListener onClickAction) {
        contentPane.setVisible(true);
        titleLabel.setText(notificationTitle);
        descriptionLabel.setText(notificationDescription);
        actionButton.addActionListener(onClickAction);
        if(image != null) {
            imageLabel.setIcon(new ImageIcon(ImageUtils.scaleImage(image, 32, 32)));
            imageLabel.setVisible(true);
            hide();
        }
    }

    private void hide() {
        TimeUtils.setTimeout(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                contentPane.setVisible(false);
                imageLabel.setVisible(false);
                updateSemaphore.release();
            }
        });
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
    public ViewController getViewController() {
        return null;
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
}
