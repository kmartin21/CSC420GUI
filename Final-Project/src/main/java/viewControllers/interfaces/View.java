package viewControllers.interfaces;

import javax.swing.*;

/**
 * Created by lwdthe1 on 12/7/16.
 */
public interface View {
    int getWidth();
    int getHeight();
    void createAndShow();
    ViewController getViewController();
    JPanel getContentPane();
}
