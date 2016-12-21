package viewControllers;

import viewControllers.interfaces.AppView;
import viewControllers.interfaces.ViewController;

/**
 * Created by lwdthe1 on 12/5/16.
 */
public interface AppViewController extends ViewController {
    NavigationController getNavigationController();
    AppView getView();
    void setupView();
    void transitionTo(AppViewController appViewController);

    void viewWillAppear();
}
