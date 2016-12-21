import viewControllers.MainApplication;

/**
 * Created by lwdthe1 on 12/4/16.
 */
public class Main {
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainApplication();
            }
        });
    }
}
