package views.subviews;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Andres on 12/8/16.
 */
public class LoggedOutPanel extends JPanel {

    public LoggedOutPanel(){
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(ImageIO.read(new File("Images/logged-out-pane.jpg")), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
