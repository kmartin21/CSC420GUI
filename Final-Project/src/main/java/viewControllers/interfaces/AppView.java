package viewControllers.interfaces;

import viewControllers.AppViewController;
import views.subviews.RealTimeNotificationView;

import javax.swing.*;

/**
 * Created by lwdthe1 on 12/5/16.
 */
public interface AppView extends View {
    AppViewController getViewController();
    JPanel getContentPane();
    RealTimeNotificationView getRealTimeNotificationView();
    Boolean isVisibleView();

}
