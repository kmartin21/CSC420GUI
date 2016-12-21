package utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lwdthe1 on 12/4/16.
 */
public class TimeUtils {
    public static void setTimeout(int delay, ActionListener actionListener){
        Timer timer = new Timer(delay, actionListener);
        timer.setRepeats(false);
        timer.start();
    }

    public static void setInterval(int delay, ActionListener actionListener){
        Timer timer = new Timer(delay, actionListener);
        timer.setRepeats(true);
        timer.start();
    }
}
